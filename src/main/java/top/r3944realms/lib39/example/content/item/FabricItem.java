package top.r3944realms.lib39.example.content.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.content.capability.AbstractedTestSyncData;
import top.r3944realms.lib39.example.content.capability.ExCapabilityHandler;
import top.r3944realms.lib39.example.content.capability.TestSyncData;
import top.r3944realms.lib39.example.core.network.ClientDataPacket;
import top.r3944realms.lib39.example.core.network.ExNetworkHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 用于执行数据查询并检查同步状态的物品
 * Shift + 右键：客户端与服务器双端同时查询检查同步
 * 普通右键：单端查询目标生物数据
 */
public class FabricItem extends Item {

    /**
     * Instantiates a new Fabric item.
     *
     * @param properties the properties
     */
    public FabricItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            // 客户端逻辑
            if (player.isShiftKeyDown()) {
                // Shift + 右键：双端检查 - 先获取客户端数据，然后发送到服务器
                handleClientDualCheck(player);
            } else {
                // 普通右键：客户端单端查询
                handleClientSideQuery(player);
            }
        } else {
            // 服务器逻辑
            ServerPlayer serverPlayer = (ServerPlayer) player;

            if (player.isShiftKeyDown()) {
                // 服务器端已经通过数据包处理双端检查，这里只发送开始消息
                player.sendSystemMessage(Component.literal("§b开始双端同步检查，请等待客户端数据..."));
            } else {
                // 服务器单端查询
                handleServerSingleEndQuery(serverPlayer);
            }

            // 添加冷却时间
            player.getCooldowns().addCooldown(this, 20); // 1秒冷却
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    /**
     * 客户端处理双端检查
     */
    private void handleClientDualCheck(Player player) {
        Entity targetEntity = getClientTargetedEntity(player);

        if (targetEntity instanceof LivingEntity livingTarget) {
            // 在客户端获取本地数据
            TestSyncData clientData = getLocalClientData(livingTarget);

            if (clientData != null) {
                // 发送客户端数据到服务器
                sendClientDataToServer(clientData, livingTarget.getId());

                // 客户端提示
                player.sendSystemMessage(Component.literal("§b已发送客户端数据到服务器，等待对比结果..."));
            } else {
                player.sendSystemMessage(Component.literal("§c无法获取客户端本地数据"));
            }
        } else {
           if (targetEntity == null && player.isShiftKeyDown()) {
               handlePlayerSelfData(player);
           } else {
               player.sendSystemMessage(Component.literal("§c请对准一个生物进行同步检查！"));
           }
        }
    }

    /**
     * 处理玩家自身数据的双端检查
     */
    private void handlePlayerSelfData(Player player) {
        // 获取玩家自身的客户端数据
        TestSyncData clientData = getLocalClientData(player);

        if (clientData != null) {
            // 发送玩家自身数据到服务器
            sendClientDataToServer(clientData, player.getId());

            // 客户端提示
            player.sendSystemMessage(Component.literal("§b已发送玩家自身客户端数据到服务器，等待对比结果..."));
        } else {
            player.sendSystemMessage(Component.literal("§c无法获取玩家自身客户端数据"));
        }
    }
    /**
     * 客户端单端查询
     */
    private void handleClientSideQuery(Player player) {
        Entity targetEntity = getClientTargetedEntity(player);

        if (targetEntity instanceof LivingEntity livingTarget) {
            TestSyncData clientData = getLocalClientData(livingTarget);

            if (clientData != null) {
                displayClientSideResults(player, livingTarget, clientData);
            } else {
                player.sendSystemMessage(Component.literal("§c无法查询客户端本地数据"));
            }
        } else {
            player.sendSystemMessage(Component.literal("§c请对准一个生物使用！"));
        }
    }

    /**
     * 服务器端处理单端查询
     */
    private void handleServerSingleEndQuery(ServerPlayer player) {
        Entity targetEntity = getServerTargetedEntity(player);

        if (targetEntity instanceof LivingEntity livingTarget) {
            player.sendSystemMessage(Component.literal(
                    String.format("§b开始查询 §e%s§b 的数据，3秒后显示结果...", livingTarget.getName().getString())
            ));

            // 启动异步数据查询
            startServerSingleEndQuery(player, livingTarget);
        } else {
            player.sendSystemMessage(Component.literal("§c请对准一个生物使用！"));
        }
    }

    /**
     * 在客户端获取本地数据
     */
    private TestSyncData getLocalClientData(LivingEntity target) {
        try {
            AbstractedTestSyncData abstractData = target.getCapability(ExCapabilityHandler.TEST_CAP).resolve().orElse(null);
            if (abstractData instanceof TestSyncData) {
                return (TestSyncData) abstractData;
            }
        } catch (Exception e) {
            Lib39.LOGGER.error("[FabricItem] 获取客户端数据失败", e);
        }
        return null;
    }

    /**
     * 发送客户端数据到服务器
     */
    private void sendClientDataToServer(TestSyncData clientData, int targetEntityId) {
        // 使用网络系统发送数据包
        ExNetworkHandler.INSTANCE.sendToServer(new ClientDataPacket(clientData, targetEntityId));
    }

    /**
     * 服务器端处理客户端发送的数据（从数据包调用）
     *
     * @param player         the player
     * @param clientData     the client data
     * @param targetEntityId the target entity id
     */
    public static void handleClientDataFromPacket(@NotNull ServerPlayer player, TestSyncData clientData, int targetEntityId) {
        Entity target = player.level().getEntity(targetEntityId);

        if (target instanceof LivingEntity livingTarget) {
            // 获取服务器端数据
            TestSyncData serverData = getServerSideData(livingTarget);

            if (serverData != null) {
                // 显示双端对比结果
                displayDualEndComparison(player, livingTarget, serverData, clientData);
            } else {
                player.sendSystemMessage(Component.literal("§c无法获取服务器端数据"));
            }
        } else {
            player.sendSystemMessage(Component.literal("§c目标生物不存在或已消失"));
        }
    }

    /**
     * 获取服务器端数据
     */
    private static @Nullable TestSyncData getServerSideData(LivingEntity target) {
        try {
            AbstractedTestSyncData abstractData = target.getCapability(ExCapabilityHandler.TEST_CAP).resolve().orElse(null);
            if (abstractData instanceof TestSyncData) {
                return (TestSyncData) abstractData;
            }
        } catch (Exception e) {
            Lib39.LOGGER.error("[FabricItem] 获取服务器端数据失败", e);
        }
        return null;
    }

    /**
     * 启动服务器单端查询
     */
    private void startServerSingleEndQuery(ServerPlayer player, LivingEntity target) {
        CompletableFuture.runAsync(() -> {
            try {
                // 等待 3 秒
                Thread.sleep(3000);

                // 在服务器线程中执行结果处理
                player.server.execute(() -> {
                    displayServerSingleEndResults(player, target);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Lib39.LOGGER.error("[FabricItem] 数据查询被中断", e);
                player.sendSystemMessage(Component.literal("§c数据查询被中断"));
            } catch (Exception e) {
                Lib39.LOGGER.error("[FabricItem] 数据查询出错", e);
                player.server.execute(() ->
                        player.sendSystemMessage(Component.literal("§c数据查询出错: " + e.getMessage()))
                );
            }
        });
    }

    /**
     * 显示服务器单端查询结果
     */
    private void displayServerSingleEndResults(ServerPlayer player, @NotNull LivingEntity target) {
        Lib39.LOGGER.info("[FabricItem] 查询生物 {} 的数据", target.getName().getString());

        // 获取目标生物的数据
        AbstractedTestSyncData abstractData = getTestSyncData(target);

        if (abstractData instanceof TestSyncData testData) {
            // 显示详细数据
            displayServerDetailedData(player, target, testData);
        } else {
            player.sendSystemMessage(Component.literal(
                    String.format("§c生物 §e%s§c 没有测试数据或数据无效", target.getName().getString())
            ));
        }
    }

    /**
     * 显示客户端查询结果
     */
    private void displayClientSideResults(Player player, LivingEntity target, TestSyncData clientData) {
        player.sendSystemMessage(Component.literal("§6=== 客户端数据查询结果 ==="));
        player.sendSystemMessage(Component.literal("§7目标生物: §e" + target.getName().getString()));
        player.sendSystemMessage(Component.literal("§7数据来源: §9客户端本地"));
        player.sendSystemMessage(Component.literal(""));

        player.sendSystemMessage(Component.literal("§a基础数据:"));
        player.sendSystemMessage(Component.literal("§7字符串: §f" + clientData.getTestString()));
        player.sendSystemMessage(Component.literal("§7整数值: §f" + clientData.getTestInt()));
        player.sendSystemMessage(Component.literal("§7布尔值: §f" + clientData.isTestBoolean()));
        player.sendSystemMessage(Component.literal("§7双精度值: §f" + String.format("%.2f", clientData.getTestDouble())));
        player.sendSystemMessage(Component.literal("§7计数器: §f" + clientData.getCounter()));

        // 显示客户端特定信息
        player.sendSystemMessage(Component.literal(""));
        player.sendSystemMessage(Component.literal("§e客户端状态:"));
        player.sendSystemMessage(Component.literal("§7数据验证: " + (clientData.validateData() ? "§a通过" : "§c失败")));
        player.sendSystemMessage(Component.literal("§7同步状态: " + (clientData.isDirty() ? "§6待同步" : "§a已同步")));
    }

    /**
     * 显示服务器详细数据（单端查询）
     */
    private void displayServerDetailedData(ServerPlayer player, LivingEntity target, TestSyncData testData) {
        player.sendSystemMessage(Component.literal("§6=== 数据查询结果 ==="));
        player.sendSystemMessage(Component.literal(
                String.format("§7目标生物: §e%s", target.getName().getString())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7实体ID: §e%d", target.getId())
        ));
        player.sendSystemMessage(Component.literal(""));

        // 显示基础数据
        player.sendSystemMessage(Component.literal("§a基础数据:"));
        player.sendSystemMessage(Component.literal(
                String.format("§7字符串: §f%s", testData.getTestString())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7整数值: §f%d", testData.getTestInt())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7布尔值: §f%s", testData.isTestBoolean())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7双精度值: §f%.2f", testData.getTestDouble())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7计数器: §f%d", testData.getCounter())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7最后同步: §f%dms前", System.currentTimeMillis() - testData.getLastSyncTime())
        ));
        player.sendSystemMessage(Component.literal(""));

        // 显示自定义数据
        TestSyncData.TestData customData = testData.getCustomData();
        player.sendSystemMessage(Component.literal("§a自定义数据:"));
        player.sendSystemMessage(Component.literal(
                String.format("§7名称: §f%s", customData.getName())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7数值: §f%d", customData.getValue())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7标志: §f%s", customData.isFlag())
        ));
        player.sendSystemMessage(Component.literal(""));

        // 显示验证状态
        boolean isValid = testData.validateData();
        player.sendSystemMessage(Component.literal(
                String.format("§7数据验证: %s", isValid ? "§a通过" : "§c失败")
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7数据状态: %s", testData.isDirty() ? "§6未同步" : "§a已同步")
        ));
    }

    /**
     * 显示双端比较结果
     */
    private static void displayDualEndComparison(ServerPlayer player, LivingEntity target, TestSyncData serverData, TestSyncData clientData) {
        player.sendSystemMessage(Component.literal("§6=== 客户端-服务器双端同步检查结果 ==="));
        player.sendSystemMessage(Component.literal(
                String.format("§7目标生物: §e%s", target.getName().getString())
        ));
        player.sendSystemMessage(Component.literal(""));

        // 显示双端数据来源
        player.sendSystemMessage(Component.literal("§a数据来源:"));
        player.sendSystemMessage(Component.literal("§7- §c服务器端§7: 实体ID " + serverData.entityId()));
        player.sendSystemMessage(Component.literal("§7- §9客户端§7: 实体ID " + clientData.entityId()));
        player.sendSystemMessage(Component.literal(""));

        // 比较各个字段
        boolean stringSynced = serverData.getTestString().equals(clientData.getTestString());
        boolean intSynced = serverData.getTestInt() == clientData.getTestInt();
        boolean booleanSynced = serverData.isTestBoolean() == clientData.isTestBoolean();
        boolean doubleSynced = Math.abs(serverData.getTestDouble() - clientData.getTestDouble()) < 0.001;
        boolean counterSynced = serverData.getCounter() == clientData.getCounter();
        boolean customDataSynced = compareCustomData(serverData.getCustomData(), clientData.getCustomData());

        // 显示字段同步状态
        player.sendSystemMessage(Component.literal("§a字段同步状态:"));
        displayDualEndSyncStatus(player, "字符串", stringSynced,
                serverData.getTestString(), clientData.getTestString());
        displayDualEndSyncStatus(player, "整数值", intSynced,
                serverData.getTestInt(), clientData.getTestInt());
        displayDualEndSyncStatus(player, "布尔值", booleanSynced,
                serverData.isTestBoolean(), clientData.isTestBoolean());
        displayDualEndSyncStatus(player, "双精度值", doubleSynced,
                serverData.getTestDouble(), clientData.getTestDouble());
        displayDualEndSyncStatus(player, "计数器", counterSynced,
                serverData.getCounter(), clientData.getCounter());
        displayDualEndSyncStatus(player, "自定义数据", customDataSynced,
                serverData.getCustomData().toString(), clientData.getCustomData().toString());

        player.sendSystemMessage(Component.literal(""));

        // 计算总体同步率
        int totalFields = 6;
        int syncedFields = (stringSynced ? 1 : 0) + (intSynced ? 1 : 0) +
                (booleanSynced ? 1 : 0) + (doubleSynced ? 1 : 0) +
                (counterSynced ? 1 : 0) + (customDataSynced ? 1 : 0);
        double syncRate = (double) syncedFields / totalFields * 100;

        // 显示总体同步状态
        player.sendSystemMessage(Component.literal("§a总体同步状态:"));
        player.sendSystemMessage(Component.literal(
                String.format("§7同步字段: §e%d§7/§e%d", syncedFields, totalFields)
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7同步率: %s", getSyncRateColor(syncRate) + String.format("%.1f%%", syncRate))
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7同步状态: %s", getOverallSyncStatus(syncRate))
        ));

        // 显示数据状态差异
        player.sendSystemMessage(Component.literal(""));
        player.sendSystemMessage(Component.literal("§a数据状态差异:"));
        player.sendSystemMessage(Component.literal(
                String.format("§7服务器脏数据状态: %s", serverData.isDirty() ? "§6脏" : "§a干净")
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7客户端脏数据状态: %s", clientData.isDirty() ? "§6脏" : "§a干净")
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7服务器验证状态: %s", serverData.validateData() ? "§a通过" : "§c失败")
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7客户端验证状态: %s", clientData.validateData() ? "§a通过" : "§c失败")
        ));

        // 显示同步建议
        player.sendSystemMessage(Component.literal(""));
        player.sendSystemMessage(Component.literal("§e同步建议:"));
        if (syncRate == 100) {
            player.sendSystemMessage(Component.literal("§a✓ 数据完全同步，无需操作"));
        } else if (syncRate >= 80) {
            player.sendSystemMessage(Component.literal("§e⚠ 数据基本同步，建议观察"));
        } else if (syncRate >= 50) {
            player.sendSystemMessage(Component.literal("§6⚠ 数据部分不同步，建议检查网络"));
        } else {
            player.sendSystemMessage(Component.literal("§c✗ 数据严重不同步，建议重新同步"));
        }
    }

    /**
     * 显示双端同步状态
     */
    private static void displayDualEndSyncStatus(ServerPlayer player, String fieldName, boolean synced, Object serverValue, Object clientValue) {
        String status = synced ? "§a✓ 同步" : "§c✗ 不同步";
        if (synced) {
            player.sendSystemMessage(Component.literal(
                    String.format("§7%s: %s §8(值: §7%s§8)", fieldName, status, serverValue)
            ));
        } else {
            player.sendSystemMessage(Component.literal(
                    String.format("§7%s: %s", fieldName, status)
            ));
            player.sendSystemMessage(Component.literal(
                    String.format("§8  §c服务器: §7%s", serverValue)
            ));
            player.sendSystemMessage(Component.literal(
                    String.format("§8  §9客户端: §7%s", clientValue)
            ));
        }
    }

    /**
     * 比较自定义数据
     */
    private static boolean compareCustomData(TestSyncData.TestData first, TestSyncData.TestData second) {
        return first.getName().equals(second.getName()) &&
                first.getValue() == second.getValue() &&
                first.isFlag() == second.isFlag();
    }

    /**
     * 获取同步率颜色
     */
    private static String getSyncRateColor(double syncRate) {
        if (syncRate >= 90) return "§a";
        if (syncRate >= 70) return "§e";
        if (syncRate >= 50) return "§6";
        return "§c";
    }

    /**
     * 获取总体同步状态
     */
    private static String getOverallSyncStatus(double syncRate) {
        if (syncRate == 100) return "§a完全同步";
        if (syncRate >= 90) return "§a优秀同步";
        if (syncRate >= 70) return "§e良好同步";
        if (syncRate >= 50) return "§6部分同步";
        return "§c同步较差";
    }

    /**
     * 客户端获取准星目标实体
     */
    private Entity getClientTargetedEntity(Player player) {
        double reachDistance = 20.0;
        float partialTicks = 1.0f; // 服务器端通常用1.0

        // 获取玩家的视线向量和位置
        Vec3 eyePosition = player.getEyePosition(partialTicks);
        Vec3 lookVector = player.getViewVector(partialTicks);
        Vec3 endPosition = eyePosition.add(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance);

        // 先检测实体
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                eyePosition,
                endPosition,
                player.getBoundingBox().expandTowards(lookVector.scale(reachDistance)).inflate(1.0),
                entity -> !entity.isSpectator() && entity.isPickable(),
                reachDistance * reachDistance // 平方距离
        );

        if (entityHit != null) {
            return entityHit.getEntity();
        }

        return null;
    }

    /**
     * 服务器获取准星目标实体
     */
    private Entity getServerTargetedEntity(ServerPlayer player) {
        double reachDistance = 20.0;
        float partialTicks = 1.0f; // 服务器端通常用1.0

        // 获取玩家的视线向量和位置
        Vec3 eyePosition = player.getEyePosition(partialTicks);
        Vec3 lookVector = player.getViewVector(partialTicks);
        Vec3 endPosition = eyePosition.add(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance);

        // 先检测实体
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                eyePosition,
                endPosition,
                player.getBoundingBox().expandTowards(lookVector.scale(reachDistance)).inflate(1.0),
                entity -> !entity.isSpectator() && entity.isPickable(),
                reachDistance * reachDistance // 平方距离
        );

        if (entityHit != null) {
            return entityHit.getEntity();
        }

        return null;
    }

    /**
     * 获取测试同步数据
     */
    private AbstractedTestSyncData getTestSyncData(Entity entity) {
        try {
            return entity.getCapability(ExCapabilityHandler.TEST_CAP).resolve().orElse(null);
        } catch (Exception e) {
            Lib39.LOGGER.debug("[FabricItem] 获取生物 {} 的 TestSyncData 失败: {}",
                    entity.getName().getString(), e.getMessage());
            return null;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.literal("§7右键点击在 3 秒后执行"));
        tooltip.add(Component.literal("§7§e准星瞄准生物§7的数据查询"));
        tooltip.add(Component.literal("§7§oShift + 右键§7进行§e客户端-服务器双端同步检查§7"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§6查询延迟: §e3秒"));
        tooltip.add(Component.literal("§6瞄准距离: §e20格"));
        tooltip.add(Component.literal("§6冷却时间: §e1秒"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§a单端查询内容:"));
        tooltip.add(Component.literal("§7- 基础数据字段"));
        tooltip.add(Component.literal("§7- 自定义数据结构"));
        tooltip.add(Component.literal("§7- 数据验证状态"));
        tooltip.add(Component.literal("§7- 同步状态信息"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e双端同步检查:"));
        tooltip.add(Component.literal("§7- 客户端和服务器同时查询"));
        tooltip.add(Component.literal("§7- 字段级同步状态对比"));
        tooltip.add(Component.literal("§7- 总体同步率计算"));
        tooltip.add(Component.literal("§7- 双端数据状态差异"));
        tooltip.add(Component.literal("§7- 同步建议"));
    }
}