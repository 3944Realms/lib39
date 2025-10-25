package top.r3944realms.lib39.core.sync;

import net.minecraft.resources.ResourceLocation;

/**
 * The interface Sync data.
 *
 * @param <T> the type parameter
 */
public interface ISyncData<T> {
    /**
     * Id resource location.
     *
     * @return the resource location
     */
    ResourceLocation id();

    /**
     * Is dirty boolean.
     *
     * @return the boolean
     */
    boolean isDirty();

    /**
     * Sets dirty.
     *
     * @param dirty the dirty
     */
    void setDirty(boolean dirty);

    /**
     * Mark dirty.
     */
    default void markDirty() {
        setDirty(true);
    }

    /**
     * Copy from.
     *
     * @param src the src
     */
    void copyFrom(T src);

    /**
     * Check if dirty then update.
     */
    void checkIfDirtyThenUpdate();
}
