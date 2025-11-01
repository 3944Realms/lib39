package top.r3944realms.lib39.example.core.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import top.r3944realms.lib39.example.content.capability.ExCapabilityHandler;
import top.r3944realms.lib39.example.content.capability.TestSyncData;
import top.r3944realms.lib39.example.core.network.ExNetworkHandler;
import top.r3944realms.lib39.example.core.register.ExLib39Attachments;
import top.r3944realms.lib39.example.datagen.ExLib39DataGenEvent;

/**
 * The type Common handler.
 */
public class ExCommonEventHandler {
    /**
     * The type Game.
     */
    @SuppressWarnings("unused")

    public static class Game extends ExCommonEventHandler {
        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof LivingEntity || entity instanceof Boat || entity instanceof Minecart) {
                TestSyncData data = entity.getData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
                if (data.entityId() == -1) {
                    data.setSelf(entity);
                }
            }
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
            ExCapabilityHandler.registerCapabilities(event);
        }

        /**
         * Gather data.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void gatherData(GatherDataEvent.Client event) {
            ExLib39DataGenEvent.gatherData(event);
        }
        @SubscribeEvent
        public static void registerPayloadPacket (RegisterPayloadHandlersEvent event) {
            ExNetworkHandler.registerPackets(event);
        }
    }
}
