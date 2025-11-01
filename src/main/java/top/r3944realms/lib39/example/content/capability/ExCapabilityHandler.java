package top.r3944realms.lib39.example.content.capability;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The type Ex capability handler.
 */
public class ExCapabilityHandler {
    /**
     * The constant TEST_CAP.
     */
    public static final Capability<AbstractedTestSyncData> TEST_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    /**
     * Register capability.
     *
     * @param event the event
     */
    public static void registerCapability(@NotNull RegisterCapabilitiesEvent event) {
        event.register(AbstractedTestSyncData.class);
    }

    /**
     * Attach capability.
     *
     * @param event the event
     */
    public static void attachCapability(@NotNull AttachCapabilitiesEvent<?> event) {
        Object object = event.getObject();
        if(object instanceof Entity entity ) {
            event.addCapability(TestSyncCapProvider.TEST_SYNC_REL, new TestSyncCapProvider(entity));
        }
    }
}
