package top.r3944realms.lib39.core.network.toClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.core.event.CommonEventHandler;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.NBTEntitySyncData;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The type Sync nbt data s 2 c pack.
 */
@SuppressWarnings("unused")
public record SyncNBTCapDataEntityS2CPack(int entityId, ResourceLocation id, CompoundTag data) {

    /**
     * Instantiates a new Sync nbt data s 2 c pack.
     *
     * @param entityId the entity id
     * @param id       the id
     * @param data     the data
     */
    public SyncNBTCapDataEntityS2CPack(int entityId, ResourceLocation id, @NotNull NBTEntitySyncData data) {
        this(entityId, data.id(), data.serializeNBT());
    }

    /**
     * Encode.
     *
     * @param msg    the msg
     * @param buffer the buffer
     */
    public static void encode(@NotNull SyncNBTCapDataEntityS2CPack msg, @NotNull FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeResourceLocation(msg.id);
        buffer.writeNbt(msg.data);
    }

    /**
     * Decode sync nbt data s 2 c pack.
     *
     * @param buffer the buffer
     * @return the sync nbt data s 2 c pack
     */
    @Contract("_ -> new")
    public static @NotNull SyncNBTCapDataEntityS2CPack decode(@NotNull FriendlyByteBuf buffer) {
        return new SyncNBTCapDataEntityS2CPack(buffer.readInt(), buffer.readResourceLocation(), buffer.readNbt());
    }

    /**
     * Handle.
     *
     * @param msg the msg
     * @param ctx the ctx
     */
    public static void handle(SyncNBTCapDataEntityS2CPack msg, @NotNull Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(msg.entityId);
                if (entity != null) {
                    Optional<SyncData2Manager.DataProvider<Entity, ISyncData<?>>> capability =
                            CommonEventHandler.Game
                                    .getSyncData2Manager()
                                    .getDataProvider(msg.id);
                    capability.flatMap(dataProvider -> dataProvider.getData(entity))
                            .ifPresent(cap -> {
                                if (cap instanceof NBTEntitySyncData nbtCap) {
                                    CompoundTag current = nbtCap.serializeNBT();
                                    if (!current.equals(msg.data)) {
                                        nbtCap.deserializeNBT(msg.data);
                                    }
                                } else Lib39.LOGGER.debug("Unhandled sync data: {}", msg.data);
                            }
                    );
                }
            }
        });
        context.setPacketHandled(true);
    }

}
