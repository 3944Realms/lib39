package top.r3944realms.lib39.core.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

public abstract class NBTSyncData implements ISyncData<NBTSyncData>, INBTSerializable<CompoundTag> {
    protected boolean dirty;
    protected final ResourceLocation id;

    protected NBTSyncData(ResourceLocation id) {
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
    public void copyFrom(@NotNull NBTSyncData src) {
        this.dirty = src.isDirty();
    }

}
