package top.r3944realms.lib39.example.core.register;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.content.item.FabricItem;
import top.r3944realms.lib39.example.content.item.NeoForgeItem;

/**
 * The type Ex lib 39 items.
 */
public class ExLib39Items {
    /**
     * The constant ITEMS.
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lib39.MOD_ID);
    /**
     * The constant SUPER_LEAD_ROPE.
     */
    public static final RegistryObject<Item> FABRIC = ITEMS.register(
            "fabric",
            () -> new FabricItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
            )
    );
    /**
     * The constant ETERNAL_POTATO.
     */
    public static final RegistryObject<Item> NEOFORGE =
            ITEMS.register("neoforge",
                    () -> new NeoForgeItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .fireResistant()
                    ));

    /**
     * Register.
     *
     * @param bus the bus
     */
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

}
