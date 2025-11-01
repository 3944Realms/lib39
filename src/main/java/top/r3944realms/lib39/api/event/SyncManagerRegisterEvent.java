package top.r3944realms.lib39.api.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.ISyncManager;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

import java.util.Optional;
import java.util.function.Function;

/**
 * The type Sync manager register event.
 */
@SuppressWarnings("unused")
public class SyncManagerRegisterEvent extends Event {
    /**
     * The Syncs 2 manager.
     */
    protected final SyncData2Manager syncs2Manager;

    /**
     * Instantiates a new Sync manager register event.
     *
     * @param syncsManager the syncs manager
     */
    public SyncManagerRegisterEvent(SyncData2Manager syncsManager) {
        this.syncs2Manager = syncsManager;
    }

    /**
     * Gets syncs manager.
     *
     * @return the syncs manager
     */
    public SyncData2Manager getSyncsManager() {
        return syncs2Manager;
    }


    /**
     * 类型安全的同步管理器注册
     *
     * @param <K>          the type parameter
     * @param <T>          the type parameter
     * @param id           the id
     * @param syncManager  the sync manager
     * @param dataProvider the dataProvider
     */
    public <K, T extends ISyncData<?>> void registerSyncManager(
            ResourceLocation id,
            ISyncManager<K, T> syncManager,
            Function<Entity, Optional<T>> dataProvider
    ) {
        syncs2Manager.registerManager(id, syncManager, dataProvider);
    }


    /**
     * Unregister sync manager.
     *
     * @param id the id
     */
    public void unregisterSyncManager(ResourceLocation id) {
        syncs2Manager.removeManager(id);
    }

    /**
     * 允许实体类
     *
     * @param id            the id
     * @param entityClasses the entity classes
     */
    public final void addAllowEntityClass(ResourceLocation id, Class<?>... entityClasses) {
        syncs2Manager.allowEntityClass(id, entityClasses);
    }

    /**
     * 移除允许的实体类
     *
     * @param id            the id
     * @param entityClasses the entity classes
     */
    public final void removeAllowEntityClass(ResourceLocation id, Class<?>... entityClasses) {
        syncs2Manager.disallowEntityClass(id, entityClasses);
    }


    /**
     * 解绑数据提供者
     *
     * @param id the id
     */
    public void unbindDataProvider(ResourceLocation id) {
        syncs2Manager.unbindDataProvider(id);
    }


    /**
     * 完整的类型安全注册
     *
     * @param <K>                  the type parameter
     * @param <T>                  the type parameter
     * @param id                   the id
     * @param syncManager          the sync manager
     * @param dataProvider         the capability
     * @param allowedEntityClasses the allowed entity classes
     */
    public <K, T extends ISyncData<?>> void registerComplete(
            ResourceLocation id,
            ISyncManager<K, T> syncManager,
            Function<Entity, Optional<T>> dataProvider,
            Class<?>... allowedEntityClasses
    ) {
        registerSyncManager(id, syncManager, dataProvider);
        if (allowedEntityClasses.length > 0) {
            addAllowEntityClass(id, allowedEntityClasses);
        }
    }
}