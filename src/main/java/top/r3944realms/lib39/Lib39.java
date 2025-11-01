package top.r3944realms.lib39;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.r3944realms.lib39.example.Lib39Example;

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
    public Lib39(IEventBus event) {
        initialize(event);
    }

    /**
     * Initialize.
     */
    public static void initialize(IEventBus event) {
        LOGGER.info("[Lib39] Initializing Lib39");
        if (shouldRegisterExamples()) {
            LOGGER.info("[Lib39] Registering Examples");
            registerExamples(event);
        }
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

    /**
     * Should register examples boolean.
     *
     * @return the boolean
     */
    static boolean shouldRegisterExamples() {
        return !FMLEnvironment.isProduction();
    }

    /**
     * Register examples.
     */
    static void registerExamples(IEventBus event) {
        LOGGER.info("[Lib39] Starting example demonstrations");
        try {
            // 创建示例实例并演示功能
            Lib39Example example = new Lib39Example(event);
            example.demonstrateFeature();

            LOGGER.info("[Lib39] Example demonstrations completed successfully");
        } catch (Exception e) {
            LOGGER.error("[Lib39] Failed to demonstrate examples", e);
        }
    }
}
