package top.r3944realms.lib39.api.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import top.r3944realms.lib39.core.compat.CompatManager;
import top.r3944realms.lib39.core.compat.ICompat;

/**
 * The type Register compat event.
 */
public class RegisterCompatEvent extends Event implements IModBusEvent {
    /**
     * The Compat manager.
     */
    protected final CompatManager compatManager;

    /**
     * Instantiates a new Register compat event.
     *
     * @param compatManager the compat manager
     */
    public RegisterCompatEvent(CompatManager compatManager) {
        this.compatManager = compatManager;
    }

    /**
     * Gets compat manager.
     *
     * @return the compat manager
     */
    public CompatManager getCompatManager() {
        return compatManager;
    }

    /**
     * Register compat.
     *
     * @param id     the id
     * @param compat the compat
     */
// 注册兼容模块
    public void registerCompat(ResourceLocation id, ICompat compat) {
        compatManager.registerCompat(id, compat);
    }

    /**
     * Register compat.
     *
     * @param namespace the namespace
     * @param path      the path
     * @param compat    the compat
     */
// 注册兼容模块（简化版本）
    public void registerCompat(String namespace, String path, ICompat compat) {
        compatManager.registerCompat(namespace, path, compat);
    }

    /**
     * Unregister compat.
     *
     * @param id the id
     */
// 取消注册兼容模块
    public void unregisterCompat(ResourceLocation id) {
        compatManager.unregisterCompat(id);
    }

}
