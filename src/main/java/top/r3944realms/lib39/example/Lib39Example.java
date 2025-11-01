package top.r3944realms.lib39.example;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.r3944realms.lib39.example.core.event.ExCommonEventHandler;
import top.r3944realms.lib39.example.core.network.ExNetworkHandler;
import top.r3944realms.lib39.example.core.register.ExLib39Items;

/**
 * The type Lib 39 example.
 */
public class Lib39Example {
    private static boolean registered = false;

    /**
     * Instantiates a new Lib 39 example.
     */
    public Lib39Example() {
        if (!registered) {
            init();
            registerToEventBus();
            registered = true;
        }
    }
    private void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ExLib39Items.register(modEventBus);
        ExNetworkHandler.register();
    }

    private void registerToEventBus() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus gameBus = MinecraftForge.EVENT_BUS;
//        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () ->  {
//            modBus.register(ExClientEventHandler.Mod.class);
//            gameBus.register(ExClientEventHandler.Game.class);
//            return null;
//        });
//        DistExecutor.unsafeCallWhenOn(Dist.DEDICATED_SERVER, () ->  {
//            modBus.register(ExServerEventHandler.Mod.class);
//            gameBus.register(ExServerEventHandler.Game.class);
//            return null;
//        });
        modBus.register(ExCommonEventHandler.Mod.class);
        gameBus.register(ExCommonEventHandler.Game.class);

    }

    /**
     * Demonstrate feature.
     */
    public void demonstrateFeature() {

    }
}
