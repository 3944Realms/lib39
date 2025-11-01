package top.r3944realms.lib39.example;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import top.r3944realms.lib39.example.core.event.ExCommonEventHandler;
import top.r3944realms.lib39.example.core.register.ExLib39Attachments;
import top.r3944realms.lib39.example.core.register.ExLib39Items;

/**
 * The type Lib 39 example.
 */
public class Lib39Example {
    private static boolean registered = false;

    /**
     * Instantiates a new Lib 39 example.
     */
    public Lib39Example(IEventBus modEventBus) {
        if (!registered) {
            init(modEventBus);
            registerToEventBus(modEventBus);
            registered = true;
        }
    }
    private void init(IEventBus modEventBus) {
        ExLib39Items.register(modEventBus);
        ExLib39Attachments.register(modEventBus);
    }

    private void registerToEventBus(IEventBus modBus) {
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
        NeoForge.EVENT_BUS.register(ExCommonEventHandler.Game.class);

    }

    /**
     * Demonstrate feature.
     */
    public void demonstrateFeature() {

    }
}
