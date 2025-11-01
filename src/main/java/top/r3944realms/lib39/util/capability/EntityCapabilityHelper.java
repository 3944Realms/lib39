package top.r3944realms.lib39.util.capability;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * 实体 Capability 注册工具类
 * 简化批量注册实体能力的过程
 */
@SuppressWarnings("unused")
public class EntityCapabilityHelper {

    /**
     * 为多个实体类型注册相同的 Capability Provider
     */
    public static <T, C extends @Nullable Object> void registerForEntityTypes(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            EntityType<?>... entityTypes) {

        if (entityTypes.length == 0) {
            throw new IllegalArgumentException("必须提供至少一个实体类型");
        }

        for (EntityType<?> entityType : entityTypes) {
            event.registerEntity(capability, entityType, provider);
        }
    }

    /**
     * 为继承自某个基类的所有实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForEntityClass(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            Class<? extends Entity> baseClass) {

        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> entityType.getBaseClass().isAssignableFrom(baseClass))
                .forEach(entityType -> event.registerEntity(capability, entityType, provider));
    }

    /**
     * 为特定命名空间的所有实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForModEntities(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            String namespace) {

        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> {
                    ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
                    return namespace.equals(key.getNamespace());
                })
                .forEach(entityType -> event.registerEntity(capability, entityType, provider));
    }

    /**
     * 为特定生物类别的所有实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForMobCategory(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            MobCategory category) {

        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> entityType.getCategory() == category)
                .forEach(entityType -> event.registerEntity(capability, entityType, provider));
    }

    /**
     * 基于条件筛选注册实体 Capability
     */
    public static <T, C extends @Nullable Object> void registerEntitiesByCondition(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            Predicate<EntityType<?>> condition) {

        BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(condition)
                .forEach(entityType -> event.registerEntity(capability, entityType, provider));
    }

    /**
     * 为所有生物实体注册 Capability（包括玩家）
     */
    public static <T, C extends @Nullable Object> void registerForAllLivingEntities(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider) {

        registerForEntityClass(event, capability, provider, LivingEntity.class);
    }

    /**
     * 为所有怪物实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForAllMonsters(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider) {

        registerForEntityClass(event, capability, provider, Monster.class);
    }

    /**
     * 为所有动物实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForAllAnimals(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider) {

        registerForEntityClass(event, capability, provider, Animal.class);
    }

    /**
     * 为所有玩家注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForPlayers(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider) {

        registerForEntityTypes(event, capability, provider, EntityType.PLAYER);
    }

    /**
     * 为所有BOSS实体注册 Capability
     */
    public static <T, C extends @Nullable Object> void registerForBossEntities(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider) {

        registerEntitiesByCondition(event, capability, provider, entityType ->
                entityType == EntityType.ENDER_DRAGON ||
                        entityType == EntityType.WITHER ||
                        entityType == EntityType.WARDEN
        );
    }

    /**
     * 排除特定实体类型进行注册
     */
    public static <T, C extends @Nullable Object> void registerWithExclusions(
            RegisterCapabilitiesEvent event,
            EntityCapability<T, C> capability,
            ICapabilityProvider<Entity, C, T> provider,
            Collection<EntityType<?>> includedEntities,
            EntityType<?>... excludedTypes) {

        Collection<EntityType<?>> excludedSet = Arrays.asList(excludedTypes);

        includedEntities.stream()
                .filter(entityType -> !excludedSet.contains(entityType))
                .forEach(entityType -> event.registerEntity(capability, entityType, provider));
    }

    /**
     * 检查某个实体类型是否已经注册了指定的 Capability
     */
    public static boolean isEntityCapabilityRegistered(
            RegisterCapabilitiesEvent event,
            EntityCapability<?, ?> capability,
            EntityType<?> entityType) {

        return event.isEntityRegistered(capability, entityType);
    }

    /**
     * 获取已注册指定 Capability 的实体类型数量
     */
    public static long getRegisteredEntityCount(
            RegisterCapabilitiesEvent event,
            EntityCapability<?, ?> capability) {

        return BuiltInRegistries.ENTITY_TYPE.stream()
                .filter(entityType -> event.isEntityRegistered(capability, entityType))
                .count();
    }
}