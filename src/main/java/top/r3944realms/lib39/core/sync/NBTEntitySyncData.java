package top.r3944realms.lib39.core.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.core.network.NetworkHandler;
import top.r3944realms.lib39.core.network.toClient.SyncNBTCapDataEntityS2CPack;

/**
 * The type Nbt sync data.
 */
public abstract class NBTEntitySyncData implements IEntity, ISyncData<NBTEntitySyncData>, INBTSerializable<CompoundTag> {
    /**
     * The Dirty.
     */
    protected boolean dirty;
    /**
     * The Id.
     */
    protected final ResourceLocation id;

    /**
     * Instantiates a new Nbt sync data.
     *
     * @param id the id
     */
    protected NBTEntitySyncData(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void copyFrom(@NotNull NBTEntitySyncData src) {
        this.dirty = src.isDirty();
    }

    @Override
    public void checkIfDirtyThenUpdate() {
        if (isDirty()) {
            NetworkHandler.sendToAllPlayer(new SyncNBTCapDataEntityS2CPack(entityId(), id(), serializeNBT()));
        }
    }
}
