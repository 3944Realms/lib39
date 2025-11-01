package top.r3944realms.lib39.core.network;


import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;

/**
 * The type Network handler.
 */
@SuppressWarnings("unused")
public class NetworkHandler {
    public static void registerPackets(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Lib39.MOD_ID + "-" + Lib39.ModInfo.VERSION);
    }
}
