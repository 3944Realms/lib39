package top.r3944realms.lib39.core.sync;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface ISyncManager<K,T extends ISyncData<?>> {
    Map<K, T> getSyncMap();
    default Set<T> getSyncSet() {
        return new HashSet<>(getSyncMap().values());
    }
    default void track(K key, T instance) {
        Set<T> syncSet = checkAndGetSet();
        syncSet.add(instance);
    }
    default void untrack(K key, T instance) {
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
