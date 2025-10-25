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

import java.util.List;
import java.util.Random;

/**
 * 用于对准星生物触发 TestSyncData 随机变换的物品
 * Shift + 右键：操作自己的数据
 * 普通右键：操作瞄准生物的数据
 */
public class NeoForgeItem extends Item {
    private static final Random RANDOM = new Random();

    /**
     * Instantiates a new Neo forge item.
     *
     * @param properties the properties
     */
    public NeoForgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            if (player.isShiftKeyDown()) {
                // Shift + 右键：操作自己的数据
                handleSelfDataOperation(serverPlayer);
            } else {
                // 普通右键：操作瞄准生物的数据
                handleTargetDataOperation(serverPlayer);
            }

            // 添加冷却时间
            player.getCooldowns().addCooldown(this, 20); // 1秒冷却
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    /**
     * 处理玩家自身数据操作
     */
    private void handleSelfDataOperation(ServerPlayer player) {
        boolean success = triggerRandomTransformation(player);

        if (success) {
            player.sendSystemMessage(Component.literal("§a已触发§e自身§a测试数据的随机变换！"));
            Lib39.LOGGER.info("[NeoForgeItem] 玩家 {} 触发了自身数据变换", player.getName().getString());
        } else {
            player.sendSystemMessage(Component.literal("§c无法触发自身数据变换"));
        }
    }

    /**
     * 处理目标生物数据操作
     */
    private void handleTargetDataOperation(ServerPlayer player) {
        // 获取玩家准星瞄准的生物
        Entity targetEntity = getTargetedEntity(player);

        if (targetEntity instanceof LivingEntity livingTarget) {
            // 触发对准星生物的数据变换
            boolean success = triggerRandomTransformation(livingTarget);

            if (success) {
                player.sendSystemMessage(Component.literal(
                        String.format("§a已触发 §e%s§a 的测试数据随机变换！", livingTarget.getName().getString())
                ));
                Lib39.LOGGER.info("[NeoForgeItem] 玩家 {} 触发生物 {} 的数据变换",
                        player.getName().getString(), livingTarget.getName().getString());
            } else {
                player.sendSystemMessage(Component.literal(
                        String.format("§c无法触发 §e%s§c 的数据变换", livingTarget.getName().getString())
                ));
            }
        } else {
            // 没有瞄准生物
            player.sendSystemMessage(Component.literal("§c请对准一个生物使用！"));
        }
    }

    /**
     * 获取玩家准星瞄准的实体
     */
    private Entity getTargetedEntity(ServerPlayer player) {
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
     * 为实体触发随机数据变换
     */
    private boolean triggerRandomTransformation(LivingEntity entity) {
        try {
            AbstractedTestSyncData abstractData = getOrCreateTestSyncData(entity);
            if (!(abstractData instanceof TestSyncData testData)) {
                return false;
            }

            // 随机选择一种变换方式
            int transformationType = RANDOM.nextInt(6); // 增加更多变换类型

            switch (transformationType) {
                case 0 -> {
                    // 完全随机数据
                    testData.generateRandomData();
                    Lib39.LOGGER.debug("[NeoForgeItem] 为 {} 生成完全随机数据", getEntityName(entity));
                }
                case 1 -> {
                    // 只修改字符串和计数器
                    testData.setTestString("transformed_" + System.currentTimeMillis());
                    testData.incrementCounter();
                    testData.updateSyncTime();
                    Lib39.LOGGER.debug("[NeoForgeItem] 为 {} 修改字符串和计数器", getEntityName(entity));
                }
                case 2 -> {
                    // 修改数值数据
                    testData.setTestInt(RANDOM.nextInt(1000));
                    testData.setTestDouble(RANDOM.nextDouble() * 100.0);
                    testData.setTestBoolean(RANDOM.nextBoolean());
                    testData.updateSyncTime();
                    Lib39.LOGGER.debug("[NeoForgeItem] 为 {} 修改数值数据", getEntityName(entity));
                }
                case 3 -> {
                    // 修改自定义数据
                    TestSyncData.TestData newCustomData = new TestSyncData.TestData(
                            "custom_" + RANDOM.nextInt(100),
                            RANDOM.nextInt(500),
                            RANDOM.nextBoolean()
                    );
                    testData.setCustomData(newCustomData);
                    testData.incrementCounter();
                    Lib39.LOGGER.debug("[NeoForgeItem] 为 {} 修改自定义数据", getEntityName(entity));
                }
                case 4 -> {
                    // 重置为默认值
                    testData.resetToDefaults();
                    Lib39.LOGGER.debug("[NeoForgeItem] 为 {} 重置数据", getEntityName(entity));
                }
                case 5 -> {
                    // 特殊变换：玩家专属数据
                    if (entity instanceof Player) {
                        testData.setTestString("player_special_" + entity.getUUID().toString().substring(0, 8));
                        testData.setTestInt(entity.getId() * 10);
                        testData.setTestDouble(entity.getHealth());
                        testData.incrementCounter();
                        testData.updateSyncTime();
                        Lib39.LOGGER.debug("[NeoForgeItem] 为玩家 {} 设置专属数据", getEntityName(entity));
                    } else {
                        // 非玩家生物使用普通变换
                        testData.generateRandomData();
                    }
                }
            }

            // 验证数据有效性
            if (!testData.validateData()) {
                Lib39.LOGGER.warn("[NeoForgeItem] {} 的数据验证失败，重置为默认值", getEntityName(entity));
                testData.resetToDefaults();
            }

            // 显示数据预览（仅对玩家自己操作时显示）
            if (entity instanceof Player) {
                displayDataPreview((Player) entity, testData);
            }

            return true;

        } catch (Exception e) {
            Lib39.LOGGER.error("[NeoForgeItem] 为 {} 触发数据变换时出错: {}",
                    getEntityName(entity), e.getMessage());
            return false;
        }
    }

    /**
     * 显示数据预览给玩家
     */
    private void displayDataPreview(Player player, TestSyncData testData) {
        player.sendSystemMessage(Component.literal("§6数据预览:"));
        player.sendSystemMessage(Component.literal(
                String.format("§7字符串: §f%s", testData.getTestString())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7计数器: §f%d", testData.getCounter())
        ));
        player.sendSystemMessage(Component.literal(
                String.format("§7验证状态: %s", testData.validateData() ? "§a通过" : "§c失败")
        ));
    }

    /**
     * 获取实体名称（用于日志）
     */
    private String getEntityName(LivingEntity entity) {
        if (entity instanceof Player) {
            return "玩家 " + entity.getName().getString();
        } else {
            return "生物 " + entity.getName().getString();
        }
    }

    private AbstractedTestSyncData getOrCreateTestSyncData(Entity entity) {
        try {
            return entity.getCapability(ExCapabilityHandler.TEST_CAP).resolve().orElseThrow();
        } catch (Exception e) {
            Lib39.LOGGER.error("[NeoForgeItem] 获取 {} 的 TestSyncData 失败: {}",
                    getEntityName((LivingEntity) entity), e.getMessage());
            return null;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.literal("§7右键点击触发§e准星瞄准生物§7的"));
        tooltip.add(Component.literal("§7测试数据随机变换"));
        tooltip.add(Component.literal("§7§oShift + 右键§7操作§e自身§7数据"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§6冷却时间: §e1秒"));
        tooltip.add(Component.literal("§6瞄准距离: §e20格"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§a变换类型:"));
        tooltip.add(Component.literal("§7- 完全随机数据"));
        tooltip.add(Component.literal("§7- 字符串+计数器"));
        tooltip.add(Component.literal("§7- 数值数据"));
        tooltip.add(Component.literal("§7- 自定义数据"));
        tooltip.add(Component.literal("§7- 重置默认值"));
        tooltip.add(Component.literal("§7- 玩家专属数据"));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e自身操作特性:"));
        tooltip.add(Component.literal("§7- 显示数据预览"));
        tooltip.add(Component.literal("§7- 玩家专属数据变换"));
    }
}