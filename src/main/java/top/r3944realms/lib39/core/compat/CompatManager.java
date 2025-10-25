package top.r3944realms.lib39.core.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.r3944realms.lib39.Lib39;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The type Compat manager.
 */
@SuppressWarnings("unused")
public class CompatManager {
    private final Map<ResourceLocation, ICompat> compats = new HashMap<>();
    private final IEventBus modEventBus, gameEventBus;

    // 存储事件监听器配置
    private final List<ListenerConfig> listenerConfigs = new ArrayList<>();


    /**
     * Instantiates a new Compat manager.
     *
     * @param modEventBus  the mod event bus
     * @param gameEventBus the game event bus
     */
    public CompatManager(IEventBus modEventBus, IEventBus gameEventBus) {
        this.modEventBus = modEventBus;
        this.gameEventBus = gameEventBus;
    }

    /**
     * Register compat.
     *
     * @param id     the id
     * @param compat the compat
     */
    public void registerCompat(ResourceLocation id, ICompat compat) {
        if (compats.containsKey(id)) {
            Lib39.LOGGER.warn("Compat with id {} is already registered!", id);
            return;
        }
        compats.put(id, compat);
        Lib39.LOGGER.debug("Registered compat: {}", id);
    }

    /**
     * Register compat.
     *
     * @param namespace the namespace
     * @param path      the path
     * @param compat    the compat
     */
    public void registerCompat(String namespace, String path, ICompat compat) {
        registerCompat(new ResourceLocation(namespace, path), compat);
    }

    /**
     * 为所有兼容模块配置事件监听器
     *
     * @param dists the dists
     * @param bus   the bus
     */
    public void addListenerForAll(@Nullable Dist dists, Mod.EventBusSubscriber.Bus bus) {
        listenerConfigs.add(new ListenerConfig(null, dists, bus));
    }

    /**
     * 为特定兼容模块配置事件监听器
     *
     * @param compatId the compat id
     * @param dists    the dists
     * @param bus      the bus
     */
    public void addListenerForCompat(ResourceLocation compatId, @Nullable Dist dists, Mod.EventBusSubscriber.Bus bus) {
        listenerConfigs.add(new ListenerConfig(compatId, dists, bus));
    }

    /**
     * 为已加载的兼容模块配置事件监听器
     *
     * @param dists    the dists
     * @param bus      the bus
     * @param consumer the consumer
     */
    public void addListenerForLoaded(@Nullable Dist dists, Mod.EventBusSubscriber.Bus bus, Consumer<IEventBus> consumer) {
        listenerConfigs.add(new ListenerConfig(null, dists, bus) {
            @Override
            boolean shouldApply(@NotNull ICompat compat) {
                return super.shouldApply(compat);
            }
        });
    }

    // ===================== 初始化和管理 =====================

    /**
     * 初始化所有兼容模块并应用事件监听器
     */
    public void initializeAll() {
        Lib39.LOGGER.info("Initializing {} compatibility modules", compats.size());

        // 1. 先初始化所有兼容模块
        for (Map.Entry<ResourceLocation, ICompat> entry : compats.entrySet()) {
            try {
                entry.getValue().initialize();
                Lib39.LOGGER.info("Initialized compat: {}", entry.getKey());
            } catch (Exception e) {
                Lib39.LOGGER.error("Failed to initialize compat: {}", entry.getKey(), e);
            }
        }

        // 2. 然后应用所有事件监听器
        applyAllEventListeners();
    }

    /**
     * 应用所有配置的事件监听器到对应的 ICompat 实例
     */
    private void applyAllEventListeners() {
        Lib39.LOGGER.info("Applying {} event listener configurations", listenerConfigs.size());

        for (ListenerConfig config : listenerConfigs) {
            if (config.compatId == null) {
                // 应用到所有兼容模块
                applyListenerToAllCompats(config);
            } else {
                // 应用到特定兼容模块
                applyListenerToCompat(config.compatId, config);
            }
        }
    }

    /**
     * 将监听器应用到所有兼容模块
     */
    private void applyListenerToAllCompats(ListenerConfig config) {
        for (ICompat compat : compats.values()) {
            if (config.shouldApply(compat)) {
                applyListenerToCompat(compat, config);
            }
        }
    }

    /**
     * 将监听器应用到特定兼容模块
     */
    private void applyListenerToCompat(ResourceLocation compatId, ListenerConfig config) {
        ICompat compat = compats.get(compatId);
        if (compat != null && config.shouldApply(compat)) {
            applyListenerToCompat(compat, config);
        }
    }

    /**
     * 将监听器应用到具体的 ICompat 实例
     */
    private void applyListenerToCompat(ICompat compat, ListenerConfig config) {
        try {
            // 根据配置调用对应的 ICompat 方法
            if (config.dists != null) {
                switch (config.dists) {
                    case CLIENT -> {
                            if (config.bus == Mod.EventBusSubscriber.Bus.FORGE) {
                                compat.addClientGameListener(gameEventBus);
                            } else {
                                compat.addClientModListener(modEventBus);
                            }
                    }
                    case DEDICATED_SERVER -> {
                            if (config.bus == Mod.EventBusSubscriber.Bus.FORGE) {
                                compat.addServerGameListener(gameEventBus);
                            } else {
                                compat.addServerModListener(modEventBus);
                            }
                    }
                }
            } else {
                // 通用监听器
                if (config.bus == Mod.EventBusSubscriber.Bus.FORGE) {
                    compat.addCommonGameListener(gameEventBus);
                } else {
                    compat.addCommonModListener(modEventBus);
                }
            }

            Lib39.LOGGER.debug("Applied {} listener to compat: {}",
                    getListenerTypeName(config), compat.id());

        } catch (Exception e) {
            Lib39.LOGGER.error("Failed to apply listener to compat: {}", compat.id(), e);
        }
    }

    /**
     * 获取监听器类型名称（用于日志）
     */
    private @NotNull String getListenerTypeName(@NotNull ListenerConfig config) {
        if (config.dists != null) {
            return config.dists.name().toLowerCase() + " " +
                    (config.bus == Mod.EventBusSubscriber.Bus.FORGE ? "game" : "mod");
        } else {
            return "common " + (config.bus == Mod.EventBusSubscriber.Bus.FORGE ? "game" : "mod");
        }
    }


    /**
     * Add listener for compat.
     *
     * @param compatId the compat id
     * @param bus      the bus
     */
    public void addListenerForCompat(ResourceLocation compatId, Mod.EventBusSubscriber.Bus bus) {
        addListenerForCompat(compatId, null, bus);
    }

    private static class ListenerConfig {
        /**
         * The Compat id.
         */
        final ResourceLocation compatId;
        /**
         * The Dists.
         */
        final Dist dists;
        /**
         * The Bus.
         */
        final Mod.EventBusSubscriber.Bus bus;

        /**
         * Instantiates a new Listener config.
         *
         * @param compatId the compat id
         * @param dists    the dists
         * @param bus      the bus
         */
        ListenerConfig(ResourceLocation compatId, Dist dists, Mod.EventBusSubscriber.Bus bus) {
            this.compatId = compatId;
            this.dists = dists;
            this.bus = bus;
        }

        /**
         * Should apply boolean.
         *
         * @param compat the compat
         * @return the boolean
         */
        boolean shouldApply(@NotNull ICompat compat) {
            return compat.isModLoaded();
        }
    }

    // ===================== 其他方法 =====================

    /**
     * On load complete.
     */
    public void onLoadComplete() {
        Lib39.LOGGER.info("Calling onLoadComplete for {} compatibility modules", compats.size());
        for (Map.Entry<ResourceLocation, ICompat> entry : compats.entrySet()) {
            try {
                entry.getValue().onLoadComplete();
            } catch (Exception e) {
                Lib39.LOGGER.error("Error in onLoadComplete for compat: {}", entry.getKey(), e);
            }
        }
    }

    /**
     * Gets compat.
     *
     * @param id the id
     * @return the compat
     */
    public Optional<ICompat> getCompat(ResourceLocation id) {
        return Optional.ofNullable(compats.get(id));
    }

    /**
     * Has compat boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean hasCompat(ResourceLocation id) {
        return compats.containsKey(id);
    }

    /**
     * Unregister compat.
     *
     * @param id the id
     */
    public void unregisterCompat(ResourceLocation id) {
        ICompat removed = compats.remove(id);
        if (removed != null) {
            Lib39.LOGGER.debug("Unregistered compat: {}", id);
        }
    }

    /**
     * Gets loaded compats.
     *
     * @return the loaded compats
     */
    public List<ICompat> getLoadedCompats() {
        return compats.values().stream()
                .filter(ICompat::isModLoaded)
                .collect(Collectors.toList());
    }


}