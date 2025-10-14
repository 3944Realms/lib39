package top.r3944realms.lib39.api.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.Event;
import top.r3944realms.lib39.core.sync.ISyncData;
import top.r3944realms.lib39.core.sync.ISyncManager;
import top.r3944realms.lib39.core.sync.SyncData2Manager;

@SuppressWarnings("unused")
public class SyncManagerRegisterEvent extends Event {
    protected final SyncData2Manager syncs2Manager;

    public SyncManagerRegisterEvent(SyncData2Manager syncsManager) {
        this.syncs2Manager = syncsManager;
    }

    public SyncData2Manager getSyncsManager() {
        return syncs2Manager;
    }

    /**
     * 类型安全的同步管理器注册
     */
    public <T extends ISyncData<?>> void registerSyncManager(
            ResourceLocation id,
            ISyncManager<T> syncManager,
            Capability<T> capability
    ) {
        syncs2Manager.registerManager(id, syncManager, capability);
    }



    public void unregisterSyncManager(ResourceLocation id) {
        syncs2Manager.removeManager(id);
    }

    /**
     * 允许实体类
     */
    public final void addAllowEntityClass(ResourceLocation id, Class<?>... entityClasses) {
        syncs2Manager.allowEntityClass(id, entityClasses);
    }

    /**
     * 移除允许的实体类
     */
    public final void removeAllowEntityClass(ResourceLocation id, Class<?>... entityClasses) {
        syncs2Manager.disallowEntityClass(id, entityClasses);
    }

    /**
     * 绑定能力（用于分离注册的情况）
     * @param id 必须先注册安全同步管理器，再绑定Cap，否则会抛出{@link IllegalStateException 未找到对应安全同步管理器}
     */
    public <T extends ISyncData<?>> void bindCapability(ResourceLocation id, Capability<T> capability) {
        syncs2Manager.bindCapability(id, capability);
    }

    /**
     * 解绑能力
     */
    public void unbindCapability(ResourceLocation id) {
        syncs2Manager.unbindCapability(id);
    }

    /**
     * 完整的类型安全注册
     */
    public <T extends ISyncData<?>> void registerComplete(
            ResourceLocation id,
            ISyncManager<T> syncManager,
            Capability<T> capability,
            Class<?>... allowedEntityClasses
    ) {
        registerSyncManager(id, syncManager, capability);
        if (allowedEntityClasses.length > 0) {
            addAllowEntityClass(id, allowedEntityClasses);
        }
    }
}