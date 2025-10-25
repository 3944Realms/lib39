package top.r3944realms.lib39;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.r3944realms.lib39.core.network.NetworkHandler;

/**
 * The type Lib 39.
 */
@Mod(Lib39.MOD_ID)
public class Lib39 {
    /**
     * The constant MOD_ID.
     */
    public static final String MOD_ID = "lib39";
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(Lib39.class);

    /**
     * Instantiates a new Lib 39.
     */
    public Lib39() {
        initialize();
    }

    /**
     * Initialize.
     */
    public static void initialize() {
        LOGGER.info("[Lib39] Initializing Lib39");
        NetworkHandler.register();
        LOGGER.info("[Lib39] Initialized Lib39");

    }

    /**
     * The type Mod info.
     */
    public static class ModInfo {
        /**
         * The constant VERSION.
         */
        public static final String VERSION;
        static {
            // 从 ModList 获取当前 ModContainer 的元数据
            VERSION = ModList.get()
                    .getModContainerById(MOD_ID)
                    .map(c -> c.getModInfo().getVersion().toString())
                    .orElse("UNKNOWN");
        }
    }
}
