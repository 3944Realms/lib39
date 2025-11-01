package top.r3944realms.lib39.core.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.concurrent.Callable;

/**
 * The interface Compat.
 */
public interface ICompat {

    /**
     * Id resource location.
     *
     * @return the resource location
     */
    ResourceLocation id();

    /**
     * Initialize.
     */
    void initialize();

    /**
     * On load complete.
     */
    default void onLoadComplete() {}

    /**
     * Is mod loaded boolean.
     *
     * @return the boolean
     */
    default boolean isModLoaded() {
        return false;
    }

    /**
     * Call if present t.
     *
     * @param <T>      the type parameter
     * @param callable the callable
     * @return the t
     * @throws Exception the exception
     */
    default <T> T callIfPresent(Callable<T> callable) throws Exception {
        if (isModLoaded()) return callable.call();
        else return null;
    }

    /**
     * Call if pesent t.
     *
     * @param <T>      the type parameter
     * @param callable the callable
     * @param elseCall the else call
     * @return the t
     * @throws Exception the exception
     */
    default <T> T callIfPresent(Callable<T> callable, Callable<T> elseCall) throws Exception {
        if (isModLoaded()) return callable.call();
        else return elseCall.call();
    }

    /**
     * Run if present boolean.
     *
     * @param runnable the runnable
     * @return the boolean
     * @throws Exception the exception
     */
    default boolean runIfPresent(Runnable runnable) throws Exception {
        if (isModLoaded()) runnable.run(); else return false;
        return true;
    }

    /**
     * Add common game listener.
     *
     * @param gameBus the game bus
     */
    default void addCommonGameListener(IEventBus gameBus) {
        // 实现通用游戏事件监听器添加逻辑
    }

    /**
     * Add common mod listener.
     *
     * @param modBus the mod bus
     */
    default void addCommonModListener(IEventBus modBus) {
        // 实现通用模组事件监听器添加逻辑
    }

    /**
     * Add client game listener.
     *
     * @param gameBus the game bus
     */
    default void addClientGameListener(IEventBus gameBus) {
        // 实现客户端游戏事件监听器添加逻辑
    }

    /**
     * Add client mod listener.
     *
     * @param modBus the mod bus
     */
    default void addClientModListener(IEventBus modBus) {
        // 实现客户端模组事件监听器添加逻辑
    }

    /**
     * Add server game listener.
     *
     * @param gameBus the game bus
     */
    default void addServerGameListener(IEventBus gameBus) {
        // 实现服务端游戏事件监听器添加逻辑
    }

    /**
     * Add server mod listener.
     *
     * @param modBus the mod bus
     */
    default void addServerModListener(IEventBus modBus) {
        // 实现服务端模组事件监听器添加逻辑
    }
}
