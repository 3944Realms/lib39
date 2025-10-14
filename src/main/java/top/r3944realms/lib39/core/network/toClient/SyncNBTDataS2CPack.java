package top.r3944realms.lib39.core.network.toClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.core.event.CommonHandler;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.NBTSyncData;

import java.util.Optional;
import java.util.function.Supplier;

public record SyncNBTDataS2CPack(int entityId, ResourceLocation id, CompoundTag data) {

    public SyncNBTDataS2CPack(int entityId, ResourceLocation id, @NotNull NBTSyncData data) {
        this(entityId, data.id(), data.serializeNBT());
    }

    public static void encode(@NotNull SyncNBTDataS2CPack msg, @NotNull FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeResourceLocation(msg.id);
        buffer.writeNbt(msg.data);
    }

    @Contract("_ -> new")
    public static @NotNull SyncNBTDataS2CPack decode(@NotNull FriendlyByteBuf buffer) {
        return new SyncNBTDataS2CPack(buffer.readInt(), buffer.readResourceLocation(), buffer.readNbt());
    }

    public static void handle(SyncNBTDataS2CPack msg, @NotNull Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(msg.entityId);
                if (entity != null) {
                    Optional<Capability<ISyncData<?>>> capability = CommonHandler.Game.getSyncData2Manager().getCapability(msg.id);
                    capability.ifPresent(dataCapability -> entity.getCapability(dataCapability).ifPresent(cap -> {
                        if (cap instanceof NBTSyncData nbtCap){
                            CompoundTag current = nbtCap.serializeNBT();
                            if (!current.equals(msg.data)) {
                                nbtCap.deserializeNBT(msg.data);
                            }
                        } else Lib39.LOGGER.debug("Unhandled sync data: {}", msg.data);
                    }));
                }
            }
        });
        context.setPacketHandled(true);
    }

}
