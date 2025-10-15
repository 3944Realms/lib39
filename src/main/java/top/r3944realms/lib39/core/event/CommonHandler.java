package top.r3944realms.lib39.core.event;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.api.event.SyncManagerRegisterEvent;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonHandler {
    @SuppressWarnings("unused")
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Lib39.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
    public static class Game extends CommonHandler {
        private static ServerLevel sl;
        public ServerLevel getServerLevel() {
            return sl;
        }
        static volatile SyncData2Manager syncData2Manager;
        private static boolean isInitialized = false;
        public static SyncData2Manager getSyncData2Manager() {
            return syncData2Manager;
        }

        @SubscribeEvent
        public static void onWorldLoad(LevelEvent.Load event) {
            if (event.getLevel().isClientSide() || !(event.getLevel() instanceof ServerLevel serverLevel)) return;
            // 只处理主世界（避免多次初始化）
            if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;
            synchronized (Game.class) {
                if (!isInitialized) {
                    syncData2Manager = new SyncData2Manager();
                    MinecraftForge.EVENT_BUS.post(new SyncManagerRegisterEvent(syncData2Manager));
                    isInitialized = true;
                    sl = serverLevel;
                    Lib39.LOGGER.info("SyncData2Manager initialized on world load");
                }
            }
        }
        @SubscribeEvent
        public static void onWorldUnload(LevelEvent.Unload event) {
            if (event.getLevel().isClientSide() || !(event.getLevel() instanceof ServerLevel serverLevel)) return;
            if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;

            sl = null;
            isInitialized = false;
        }

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (syncData2Manager == null) return;
                if (event.getServer().getTickCount() % 10 == 0)
                    syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::makeDirty)));
                syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::checkIfDirtyThenUpdate)));
            }
        }
        @SubscribeEvent
        public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity.level().isClientSide) return;

            for (ResourceLocation id : syncData2Manager.getRegisteredKeys()) {
                if (syncData2Manager.isEntityClassAllowed(id, entity.getClass())) {
                    syncData2Manager.trackEntityForManager(entity, id);
                }
            }
        }
        @SubscribeEvent
        public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity.level().isClientSide) return;

            for (ResourceLocation id : syncData2Manager.getRegisteredKeys()) {
                if (syncData2Manager.isEntityClassAllowed(id, entity.getClass())) {
                    syncData2Manager.untrackEntityForManager(entity, id);
                }
            }
        }
    }
    @SuppressWarnings("unused")
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Lib39.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class Mod extends CommonHandler {
        private static final Map<RegistryObject<Block>, ResourceKey<CreativeModeTab>[]> itemAddMap = new ConcurrentHashMap<>();
        private static final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<Block>>> tabToItemsMap = new ConcurrentHashMap<>();

        @SafeVarargs
        public static void addItemToTabs(RegistryObject<Block> item, ResourceKey<CreativeModeTab>... tabs) {
            itemAddMap.put(item, tabs);

            // 更新反向映射
            for (ResourceKey<CreativeModeTab> tab : tabs) {
                tabToItemsMap.computeIfAbsent(tab, k -> new ArrayList<>()).add(item);
            }
        }

        @SubscribeEvent
        public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            List<RegistryObject<Block>> itemsForTab = tabToItemsMap.get(event.getTabKey());
            if (itemsForTab != null) {
                itemsForTab.forEach(event::accept);
            }
        }

        public static Map<RegistryObject<Block>, ResourceKey<CreativeModeTab>[]> getItemAddMap() {
            return itemAddMap;
        }
    }
}
