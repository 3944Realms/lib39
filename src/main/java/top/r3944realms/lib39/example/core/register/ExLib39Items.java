package top.r3944realms.lib39.example.core.register;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.content.item.FabricItem;
import top.r3944realms.lib39.example.content.item.NeoForgeItem;

import java.util.Objects;

/**
 * The type Ex lib 39 items.
 */
public class ExLib39Items {
    /**
     * The constant ITEMS.
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Lib39.MOD_ID);
    /**
     * The constant SUPER_LEAD_ROPE.
     */
    public static final DeferredHolder<Item, Item> FABRIC = ITEMS.register(
            "fabric",
            () -> new FabricItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
                            .setId(Objects.requireNonNull(ExLib39ItemResourceKeys.FABRIC.getResourceKey()))
            )
    );
    /**
     * The constant ETERNAL_POTATO.
     */
    public static final DeferredHolder<Item, Item> NEOFORGE =
            ITEMS.register("neoforge",
                    () -> new NeoForgeItem(
                            new Item.Properties()
                                    .stacksTo(1)
                                    .fireResistant()
                                    .setId(Objects.requireNonNull(ExLib39ItemResourceKeys.NEOFORGE.getResourceKey()))
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
