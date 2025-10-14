package top.r3944realms.lib39.core.sync;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Consumer;

public interface ISyncManager<T extends ISyncData<?>> {
    Set<T> getSyncSet();
    default void track(T instance) {
        Set<T> syncSet = checkAndGetSet();
        syncSet.add(instance);
    }
    default void untrack(T instance) {
        Set<T> syncSet = checkAndGetSet();
        syncSet.remove(instance);
    }
    default void foreach(Consumer<T> consumer) {
        Set<T> syncSet = checkAndGetSet();
        syncSet.forEach(consumer);
    }

    private @NotNull Set<T> checkAndGetSet() throws IllegalArgumentException {
        Set<T> syncSet = getSyncSet();
        if (syncSet == null) {
            throw new IllegalStateException("SyncSet is not initialized");
        }
        return syncSet;
    }
}
