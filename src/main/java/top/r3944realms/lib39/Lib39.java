package top.r3944realms.lib39;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.r3944realms.lib39.core.network.NetworkHandler;

@Mod(Lib39.MOD_ID)
public class Lib39 {
    public static final String MOD_ID = "lib39";
    public static final Logger LOGGER = LoggerFactory.getLogger(Lib39.class);
    public Lib39() {
        initialize();
    }
    public static void initialize() {
        LOGGER.info("[Lib39] Initializing Lib39");
        NetworkHandler.register();
        LOGGER.info("[Lib39] Initialized Lib39");

    }
    public static class ModInfo {
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
