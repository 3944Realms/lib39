package top.r3944realms.lib39.example.core.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.r3944realms.lib39.api.event.SyncManagerRegisterEvent;
import top.r3944realms.lib39.core.sync.CachedSyncManager;
import top.r3944realms.lib39.example.content.capability.AbstractedTestSyncData;
import top.r3944realms.lib39.example.content.capability.ExCapabilityHandler;
import top.r3944realms.lib39.example.content.capability.TestSyncData;
import top.r3944realms.lib39.example.datagen.EXLib39DataGenEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Common handler.
 */
public class ExCommonEventHandler {
    /**
     * The type Game.
     */
    @SuppressWarnings("unused")

    public static class Game extends ExCommonEventHandler {
        /**
         * Attach capability.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void attachCapability(AttachCapabilitiesEvent<?> event) {
            ExCapabilityHandler.attachCapability(event);
        }

        /**
         * On register sync.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onRegisterSync (SyncManagerRegisterEvent event) {
            event.registerSyncManager(
                    TestSyncData.ID,
                    new CachedSyncManager<Entity, AbstractedTestSyncData>() {
                        private final Map<Entity, AbstractedTestSyncData> syncDataMap = new ConcurrentHashMap<>();
                        @Override
                        public Map<Entity, AbstractedTestSyncData> getSyncMap() {
                            return syncDataMap;
                        }
                    },
                    ExCapabilityHandler.TEST_CAP

            );
        }

    }


    /**
     * The type Mod.
     */
    public static class Mod extends ExCommonEventHandler {

        /**
         * On fml common setup.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {

            });
        }

        /**
         * Register capability.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void registerCapability(RegisterCapabilitiesEvent event) {
            ExCapabilityHandler.registerCapability(event);
        }

        /**
         * Gather data.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            EXLib39DataGenEvent.gatherData(event);
        }
    }
}
