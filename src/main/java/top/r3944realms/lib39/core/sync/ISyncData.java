package top.r3944realms.lib39.core.sync;

import net.minecraft.resources.ResourceLocation;

public interface ISyncData<T> {
    ResourceLocation id();
    boolean isDirty();
    void setDirty(boolean dirty);
    default void makeDirty() {
        setDirty(true);
    }
    void copyFrom(T src);
    void checkIfDirtyThenUpdate();
}
