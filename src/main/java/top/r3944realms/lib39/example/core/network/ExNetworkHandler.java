package top.r3944realms.lib39.example.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import top.r3944realms.lib39.Lib39;

/**
 * The type Ex network handler.
 */
public class ExNetworkHandler {
    /**
     * The constant INSTANCE.
     */
    public static final SimpleChannel INSTANCE;
    private static int ID = 0;

    static {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Lib39.MOD_ID, "test"),
                () -> "1.0",
                s -> true,
                s -> true
        );
    }

    /**
     * Register.
     */
    public static void register() {
        // 注册数据包
        INSTANCE.registerMessage(ID++, ClientDataPacket.class,
                ClientDataPacket::toBytes,
                ClientDataPacket::new,
                ClientDataPacket::handle);
    }
}