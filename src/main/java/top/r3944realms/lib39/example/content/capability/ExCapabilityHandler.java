package top.r3944realms.lib39.example.content.capability;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.core.register.ExLib39Attachments;
import top.r3944realms.lib39.util.capability.EntityCapabilityHelper;

/**
 * The type Ex capability handler.
 */
public class ExCapabilityHandler {
    /**
     * The constant TEST_CAP.
     */
    public static final EntityCapability<AbstractedTestSyncData, Void> TEST_CAP = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(Lib39.MOD_ID, "test_data"),
            AbstractedTestSyncData.class
    );

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        EntityCapabilityHelper.registerForEntityClass(
                event,
                TEST_CAP,
                (entity, context) -> entity.getData(ExLib39Attachments.TEST_DATA_ATTACHMENT),
                LivingEntity.class
        );
        EntityCapabilityHelper.registerForEntityClass(
                event,
                TEST_CAP,
                (entity, context) -> entity.getData(ExLib39Attachments.TEST_DATA_ATTACHMENT),
                Boat.class
        );
        EntityCapabilityHelper.registerForEntityClass(
                event,
                TEST_CAP,
                (entity, context) -> entity.getData(ExLib39Attachments.TEST_DATA_ATTACHMENT),
                Minecart.class
        );
    }
}
