package top.r3944realms.lib39.example.core.network;


import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;

/**
 * The type Ex network handler.
 */
public class ExNetworkHandler {
    public static void registerPackets(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Lib39.MOD_ID + "-ex-" + Lib39.ModInfo.VERSION);
        registrar.playToServer(
                ClientDataPayload.TYPE,
                ClientDataPayload.STREAM_CODEC,
                ClientDataPayload::handle
        );
    }
}