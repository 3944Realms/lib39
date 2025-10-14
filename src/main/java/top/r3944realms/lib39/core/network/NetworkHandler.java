package top.r3944realms.lib39.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.core.network.toClient.SyncNBTDataS2CPack;

public class NetworkHandler {
    private static int cid = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Lib39.MOD_ID, "main"),
            () -> Lib39.ModInfo.VERSION,
            Lib39.ModInfo.VERSION::equals,
            Lib39.ModInfo.VERSION::equals
    );
    public static void register() {
        INSTANCE.messageBuilder(SyncNBTDataS2CPack.class, cid++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncNBTDataS2CPack::encode)
                .decoder(SyncNBTDataS2CPack::decode)
                .consumerNetworkThread(SyncNBTDataS2CPack::handle)
                .add();
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG, T> void sendToPlayer(MSG message, T entity, @NotNull PacketDistributor<T> packetDistributor){
        INSTANCE.send(packetDistributor.with(() -> entity), message);
    }
}
