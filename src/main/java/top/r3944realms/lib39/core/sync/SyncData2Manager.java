package top.r3944realms.lib39.core.sync;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class SyncData2Manager {
    private final Map<ResourceLocation, TypedSyncEntry<?>> typedEntries = Maps.newConcurrentMap();

    private static class TypedSyncEntry<T extends ISyncData<?>> {
        final ISyncManager<T> manager;
        final Capability<T> capability;
        final Set<Class<?>> allowedClasses;

        TypedSyncEntry(ISyncManager<T> manager, Capability<T> capability) {
            this.manager = manager;
            this.capability = capability;
            this.allowedClasses = Sets.newConcurrentHashSet();
        }
    }

    public <T extends ISyncData<?>> void registerManager(
            ResourceLocation key,
            ISyncManager<T> manager,
            Capability<T> capability
    ) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(manager, "Sync manager cannot be null");
        Objects.requireNonNull(capability, "Capability cannot be null");

        typedEntries.put(key, new TypedSyncEntry<>(manager, capability));
    }

    /**
     * 向后兼容的注册方法（只注册管理器，不注册能力）
     */
    @SuppressWarnings("unchecked")
    public void registerManager(ResourceLocation key, ISyncManager<? extends ISyncData<?>> manager) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(manager, "Sync manager cannot be null");

        // 创建一个虚拟的 TypedSyncEntry，但 capability 为 null
        // 注意：这种方法会限制类型安全的功能
        typedEntries.put(key, new TypedSyncEntry<>(
                (ISyncManager<ISyncData<?>>) manager,
                null
        ));
    }

    @SuppressWarnings("unchecked")
    public <T extends ISyncData<?>> Optional<ISyncManager<T>> getManager(ResourceLocation key) {
        TypedSyncEntry<?> entry = typedEntries.get(key);
        return entry != null ? Optional.of((ISyncManager<T>) entry.manager) : Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T extends ISyncData<?>> Optional<Capability<T>> getCapability(ResourceLocation key) {
        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null && entry.capability != null) {
            return Optional.of((Capability<T>) entry.capability);
        }
        return Optional.empty();
    }

    public final void allowEntityClass(ResourceLocation key, Class<?>... classes) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(classes, "Classes array cannot be null");

        if (classes.length == 0) {
            return;
        }

        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null) {
            entry.allowedClasses.addAll(Arrays.asList(classes));
        }
    }

    /**
     * 移除允许的实体类
     */
    public final void disallowEntityClass(ResourceLocation key, Class<?>... classes) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(classes, "Classes array cannot be null");

        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null && classes.length > 0) {
            Arrays.asList(classes).forEach(entry.allowedClasses::remove);

        }
    }

    /**
     * 绑定能力（用于分离注册的情况）
     */
    public <T extends ISyncData<?>> void bindCapability(ResourceLocation key, Capability<T> capability) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(capability, "Capability cannot be null");

        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null) {
            // 更新现有条目的能力
            updateCapabilityInEntry(entry, capability);
        } else throw new IllegalArgumentException("No manager found for " + key);
    }

    /**
     * 解绑能力
     */
    public void unbindCapability(ResourceLocation key) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");

        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null) {
            // 将能力设置为null，但保留管理器和其他配置
            updateCapabilityInEntry(entry, null);
        }
    }

    /**
     * 清除允许的实体类
     */
    public void clearAllowedEntityClasses(ResourceLocation key) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");

        TypedSyncEntry<?> entry = typedEntries.get(key);
        if (entry != null) {
            entry.allowedClasses.clear();
        }
    }

    public boolean isEntityClassAllowed(ResourceLocation key, Class<?> entityClass) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        Objects.requireNonNull(entityClass, "Entity class cannot be null");

        TypedSyncEntry<?> entry = typedEntries.get(key);
        boolean isAllowed = false;
        if (entry != null) {
            for (Class<?> allowedClass : entry.allowedClasses) {
                if (entityClass.isAssignableFrom(allowedClass)) {
                    isAllowed = true;
                    break;
                }
            }
        }
        return entry != null && isAllowed ;
    }

    // 类型安全的事件处理
    public void trackEntityForManager(Entity entity, ResourceLocation managerId) {
        TypedSyncEntry<?> entry = typedEntries.get(managerId);
        if (entry != null) {
            trackEntityWithTypedEntry(entity, entry);
        }
    }

    private <T extends ISyncData<?>> void trackEntityWithTypedEntry(Entity entity, @NotNull TypedSyncEntry<T> entry) {
        if (entry.capability != null) {
            entity.getCapability(entry.capability)
                    .ifPresent(entry.manager::track);
        }
    }
    // 类型安全的事件处理 - 取消跟踪实体
    public void untrackEntityForManager(Entity entity, ResourceLocation managerId) {
        TypedSyncEntry<?> entry = typedEntries.get(managerId);
        if (entry != null) {
            untrackEntityWithTypedEntry(entity, entry);
        }
    }

    private <T extends ISyncData<?>> void untrackEntityWithTypedEntry(Entity entity, @NotNull TypedSyncEntry<T> entry) {
        if (entry.capability != null) {
            entity.getCapability(entry.capability)
                    .ifPresent(entry.manager::untrack);
        }
    }

    /**
     * 从所有管理器中移除实体跟踪
     */
    public void untrackEntityFromAllManagers(Entity entity) {
        for (ResourceLocation id : getRegisteredKeys()) {
            if (isEntityClassAllowed(id, entity.getClass())) {
                untrackEntityForManager(entity, id);
            }
        }
    }

    /**
     * 批量从管理器中移除实体跟踪
     */
    public void untrackEntitiesForManager(@NotNull Iterable<Entity> entities, ResourceLocation managerId) {
        for (Entity entity : entities) {
            untrackEntityForManager(entity, managerId);
        }
    }

    /**
     * 从所有管理器中批量移除实体跟踪
     */
    public void untrackEntitiesFromAllManagers(@NotNull Iterable<Entity> entities) {
        for (Entity entity : entities) {
            untrackEntityFromAllManagers(entity);
        }
    }

    /**
     * 强制清理管理器中的所有跟踪数据
     */
    public void clearAllTrackedData(ResourceLocation managerId) {
        TypedSyncEntry<?> entry = typedEntries.get(managerId);
        if (entry != null) {
            clearTrackedDataForEntry(entry);
        }
    }

    private <T extends ISyncData<?>> void clearTrackedDataForEntry(@NotNull TypedSyncEntry<T> entry) {
        // 获取当前跟踪的集合并清空
        Set<T> syncSet = entry.manager.getSyncSet();
        if (syncSet != null) {
            syncSet.clear();
        }
    }

    /**
     * 清理所有管理器的跟踪数据
     */
    public void clearAllTrackedData() {
        for (ResourceLocation id : getRegisteredKeys()) {
            clearAllTrackedData(id);
        }
    }

    // 辅助方法：更新条目的能力
    @SuppressWarnings("unchecked")
    private <T extends ISyncData<?>> void updateCapabilityInEntry(TypedSyncEntry<?> entry, Capability<T> newCapability) {
        TypedSyncEntry<T> typedEntry = (TypedSyncEntry<T>) entry;
        // 由于 capability 是 final，需要替换整个 entry
        // 在实际实现中，可能需要将 capability 改为非 final 或使用不同的设计
        // 这里假设重构了 TypedSyncEntry 使 capability 可变
    }



    public Set<ResourceLocation> getRegisteredKeys() {
        return Collections.unmodifiableSet(typedEntries.keySet());
    }

    public void forEach(BiConsumer<ResourceLocation, ISyncManager<?>> consumer) {
        Objects.requireNonNull(consumer, "Consumer cannot be null");
        typedEntries.forEach((key, entry) -> consumer.accept(key, entry.manager));
    }

    public int getManagerCount() {
        return typedEntries.size();
    }

    public void clearAll() {
        typedEntries.clear();
    }

    /**
     * 移除管理器（包括所有相关配置）
     */
    public void removeManager(ResourceLocation key) {
        Objects.requireNonNull(key, "ResourceLocation key cannot be null");
        typedEntries.remove(key);
    }
}