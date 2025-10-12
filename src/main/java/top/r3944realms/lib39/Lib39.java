package top.r3944realms.lib39;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Lib39.MOD_ID)
public class Lib39 {
    public static final String MOD_ID = "lib39";
    public static final Logger LOGGER = LoggerFactory.getLogger(Lib39.class);
    public Lib39() {
        LOGGER.info("[Lib39] Initializing Lib39");
    }
}
