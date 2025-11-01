package top.r3944realms.lib39.example.core.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import top.r3944realms.lib39.example.content.capability.TestSyncData;
import top.r3944realms.lib39.example.content.item.FabricItem;

import java.util.function.Supplier;

/**
 * The type Client data packet.
 */
public class ClientDataPacket {
    private final TestSyncData clientData;
    private final int targetEntityId;

    /**
     * Instantiates a new Client data packet.
     *
     * @param clientData     the client data
     * @param targetEntityId the target entity id
     */
    public ClientDataPacket(TestSyncData clientData, int targetEntityId) {
        this.clientData = clientData;
        this.targetEntityId = targetEntityId;
    }

    /**
     * Instantiates a new Client data packet.
     *
     * @param buf the buf
     */
    public ClientDataPacket(FriendlyByteBuf buf) {
        this.clientData = TestSyncData.staticFromBytes(buf);
        this.targetEntityId = buf.readInt();
    }

    /**
     * To bytes.
     *
     * @param buf the buf
     */
    public void toBytes(FriendlyByteBuf buf) {
        clientData.toBytes(buf);
        buf.writeInt(targetEntityId);
    }

    /**
     * Handle.
     *
     * @param supplier the supplier
     */
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                // 处理客户端发送的数据
                handleClientData(player, clientData, targetEntityId);
            }
        });
        context.setPacketHandled(true);
    }

    private void handleClientData(ServerPlayer player, TestSyncData clientData, int targetEntityId) {
        FabricItem.handleClientDataFromPacket(player, clientData, targetEntityId);
    }
}