package top.r3944realms.lib39.core.sync;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class CachedSyncManager<K, T extends ISyncData<?>> implements ISyncManager<K, T> {

    private volatile Set<T> cachedSet;
    private volatile int mapSize = -1;

    @Override
    public Set<T> getSyncSet() {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }

        // 检查是否需要更新缓存
        if (cachedSet == null || mapSize != syncMap.size()) {
            synchronized (this) {
                if (cachedSet == null || mapSize != syncMap.size()) {
                    cachedSet = Set.copyOf(syncMap.values());
                    mapSize = syncMap.size();
                }
            }
        }
        return cachedSet;
    }

    /**
     * 当Map发生变化时调用此方法清除缓存
     */
    protected void invalidateCache() {
        cachedSet = null;
        mapSize = -1;
    }

    @Override
    public void track(K key, T instance) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        syncMap.put(key, instance);
        invalidateCache();
    }

    @Override
    public void untrack(K key, T instance) {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap == null) {
            throw new IllegalStateException("SyncMap is not initialized");
        }
        // 只有当key对应的value确实是instance时才移除，避免误删
        syncMap.remove(key, instance);
        invalidateCache();
    }

    @Override
    public void clear() {
        Map<K, T> syncMap = getSyncMap();
        if (syncMap != null) {
            syncMap.clear();
        }
        invalidateCache();
    }
}