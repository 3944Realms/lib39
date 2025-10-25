package top.r3944realms.lib39.core.sync;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The interface Sync manager.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 */
@SuppressWarnings("unused")
public interface ISyncManager<K, T extends ISyncData<?>> {

    /**
     * 获取同步映射
     *
     * @return the sync map
     */
    Map<K, T> getSyncMap();

    /**
     * 获取同步集合
     *
     * @return the sync set
     */
    default Set<T> getSyncSet() {
        Map<K, T> syncMap = getSyncMap();
        return Set.copyOf(syncMap.values());
    }

    /**
     * 跟踪实例
     *
     * @param key      the key
     * @param instance the instance
     */
    default void track(K key, T instance) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        syncMap.put(key, instance);
    }

    /**
     * 取消跟踪
     *
     * @param key      the key
     * @param instance the instance
     */
    default void untrack(K key, T instance) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        // 只有当key对应的value确实是instance时才移除，避免误删
        syncMap.remove(key, instance);
    }

    /**
     * 遍历操作
     *
     * @param consumer the consumer
     */
    default void foreach(Consumer<T> consumer) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        syncMap.values().forEach(consumer);
    }

    /**
     * 批量操作
     *
     * @param instances the instances
     */
    default void trackAll(Map<K, T> instances) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        syncMap.putAll(instances);
    }

    /**
     * 获取大小
     *
     * @return the int
     */
    default int size() {
        Map<K, T> syncMap = getSyncMap();
        return syncMap != null ? syncMap.size() : 0;
    }

    /**
     * 检查是否包含key
     *
     * @param key the key
     * @return the boolean
     */
    default boolean containsKey(K key) {
        Map<K, T> syncMap = getSyncMap();
        return syncMap != null && syncMap.containsKey(key);
    }

    /**
     * 检查是否包含value
     *
     * @param value the value
     * @return the boolean
     */
    default boolean containsValue(T value) {
        Map<K, T> syncMap = getSyncMap();
        return syncMap != null && syncMap.containsValue(value);
    }

    /**
     * 清空所有数据
     */
    default void clear() {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap != null) {
            syncMap.clear();
        }
    }
}
