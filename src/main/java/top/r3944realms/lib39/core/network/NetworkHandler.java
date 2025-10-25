package top.r3944realms.lib39.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.core.network.toClient.SyncNBTCapDataEntityS2CPack;

/**
 * The type Network handler.
 */
@SuppressWarnings("unused")
public class NetworkHandler {
    private static int cid = 0;
    /**
     * The constant INSTANCE.
     */
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Lib39.MOD_ID, "main"),
            () -> Lib39.ModInfo.VERSION,
            Lib39.ModInfo.VERSION::equals,
            Lib39.ModInfo.VERSION::equals
    );

    /**
     * Register.
     */
    public static void register() {
        INSTANCE.messageBuilder(SyncNBTCapDataEntityS2CPack.class, cid++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncNBTCapDataEntityS2CPack::encode)
                .decoder(SyncNBTCapDataEntityS2CPack::decode)
                .consumerNetworkThread(SyncNBTCapDataEntityS2CPack::handle)
                .add();
    }

    /**
     * Send to player.
     *
     * @param <MSG>   the type parameter
     * @param message the message
     * @param player  the player
     */
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    /**
     * Send to player.
     *
     * @param <MSG>             the type parameter
     * @param <T>               the type parameter
     * @param message           the message
     * @param entity            the entity
     * @param packetDistributor the packet distributor
     */
    public static <MSG, T> void sendToPlayer(MSG message, T entity, @NotNull PacketDistributor<T> packetDistributor){
        INSTANCE.send(packetDistributor.with(() -> entity), message);
    }

    /**
     * Send to player.
     *
     * @param <MSG>   the type parameter
     * @param message the message
     */
    public static <MSG> void sendToAllPlayer(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
