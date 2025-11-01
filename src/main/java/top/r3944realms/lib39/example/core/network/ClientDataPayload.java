package top.r3944realms.lib39.example.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.content.capability.TestSyncData;
import top.r3944realms.lib39.example.content.item.FabricItem;

/**
 * The type Client data packet.
 */
public record ClientDataPayload(TestSyncData clientData, int targetEntityId) implements CustomPacketPayload {
    public static final Type<ClientDataPayload> TYPE =  new Type<>(ResourceLocation.fromNamespaceAndPath(Lib39.MOD_ID, "client_data"));
    public static final StreamCodec<FriendlyByteBuf, ClientDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                TestSyncData.CODEC, ClientDataPayload::clientData,
                ByteBufCodecs.INT, ClientDataPayload::targetEntityId,
                ClientDataPayload::new
            );
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            // 处理客户端发送的数据
            handleClientData(player, clientData, targetEntityId);
        });
    }

    private void handleClientData(ServerPlayer player, TestSyncData clientData, int targetEntityId) {
        FabricItem.handleClientDataFromPacket(player, clientData, targetEntityId);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}