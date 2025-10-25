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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.api.event.RegisterCompatEvent;
import top.r3944realms.lib39.api.event.SyncManagerRegisterEvent;
import top.r3944realms.lib39.core.compat.CompatManager;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Common handler.
 */
public class CommonEventHandler {
    /**
     * The type Game.
     */
    @SuppressWarnings("unused")
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Lib39.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.FORGE)
    public static class Game extends CommonEventHandler {
        private static ServerLevel sl;

        /**
         * Gets server level.
         *
         * @return the server level
         */
        public static ServerLevel getServerLevel() {
            return sl;
        }

        /**
         * The Sync data 2 manager.
         */
        static volatile SyncData2Manager syncData2Manager;
        private static boolean isSync2MInitialized = false;

        /**
         * Gets sync data 2 manager.
         *
         * @return the sync data 2 manager
         */
        public static SyncData2Manager getSyncData2Manager() {
            return syncData2Manager;
        }

        /**
         * On world load.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onWorldLoad(LevelEvent.Load event) {
            if (event.getLevel().isClientSide() || !(event.getLevel() instanceof ServerLevel serverLevel)) return;
            // 只处理主世界（避免多次初始化）
            if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;
            synchronized (Game.class) {
                if (!isSync2MInitialized) {
                    syncData2Manager = new SyncData2Manager();
                    MinecraftForge.EVENT_BUS.post(new SyncManagerRegisterEvent(syncData2Manager));
                    isSync2MInitialized = true;
                    sl = serverLevel;
                    Lib39.LOGGER.info("SyncData2Manager initialized on world load");
                }
            }
        }

        /**
         * On world unload.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onWorldUnload(LevelEvent.Unload event) {
            if (event.getLevel().isClientSide() || !(event.getLevel() instanceof ServerLevel serverLevel)) return;
            if (!serverLevel.dimension().equals(Level.OVERWORLD)) return;

            sl = null;
            isSync2MInitialized = false;
        }

        /**
         * On server tick.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                if (syncData2Manager == null) return;
                if (event.getServer().getTickCount() % 10 == 0)
                    syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::markDirty)));
                syncData2Manager.forEach(((resourceLocation, iSyncManager) -> iSyncManager.foreach(ISyncData::checkIfDirtyThenUpdate)));
            }
        }

        /**
         * On entity join world.
         *
         * @param event the event
         */
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

        /**
         * On entity leave world.
         *
         * @param event the event
         */
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

    /**
     * The type Mod.
     */
    @SuppressWarnings("unused")
    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = Lib39.MOD_ID, bus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD)
    public static class Mod extends CommonEventHandler {
        private static final Map<RegistryObject<Block>, ResourceKey<CreativeModeTab>[]> itemAddMap = new ConcurrentHashMap<>();
        private static final Map<ResourceKey<CreativeModeTab>, List<RegistryObject<Block>>> tabToItemsMap = new ConcurrentHashMap<>();

        /**
         * Gets compat manager.
         *
         * @return the compat manager
         */
        public static CompatManager getCompatManager() {
            return compatManager;
        }

        /**
         * The Compat manager.
         */
        static volatile CompatManager compatManager;

        /**
         * On fml common setup.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onFMLCommonSetup (FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
                IEventBus gameBus = MinecraftForge.EVENT_BUS;
                compatManager = new CompatManager(modBus, gameBus);
                MinecraftForge.EVENT_BUS.post(new RegisterCompatEvent(compatManager));
            });
        }

        /**
         * Add item to tabs.
         *
         * @param item the item
         * @param tabs the tabs
         */
        @SafeVarargs
        public static void addItemToTabs(RegistryObject<Block> item, ResourceKey<CreativeModeTab>... tabs) {
            itemAddMap.put(item, tabs);

            // 更新反向映射
            for (ResourceKey<CreativeModeTab> tab : tabs) {
                tabToItemsMap.computeIfAbsent(tab, k -> new ArrayList<>()).add(item);
            }
        }

        /**
         * On build creative tab contents.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            List<RegistryObject<Block>> itemsForTab = tabToItemsMap.get(event.getTabKey());
            if (itemsForTab != null) {
                itemsForTab.forEach(event::accept);
            }
        }

        /**
         * Gets item add map.
         *
         * @return the item add map
         */
        public static Map<RegistryObject<Block>, ResourceKey<CreativeModeTab>[]> getItemAddMap() {
            return itemAddMap;
        }
    }
}
