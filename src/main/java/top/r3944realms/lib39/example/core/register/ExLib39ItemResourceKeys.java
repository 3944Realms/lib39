package top.r3944realms.lib39.example.core.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.datagen.value.IResourceKeyValue;

public enum ExLib39ItemResourceKeys implements IResourceKeyValue {
    FABRIC("fabric"),
    NEOFORGE("neoforge"),
    ;
    private final ResourceKey<Item> resourceKey;
    ExLib39ItemResourceKeys(String name) {
        resourceKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Lib39.MOD_ID, name));
    }
    ExLib39ItemResourceKeys(@NotNull DeferredHolder<Item, ? extends Item> item) {
        resourceKey = ResourceKey.create(Registries.ITEM, item.getId());
    }
    @Contract(pure = true)
    @Override
    public @Nullable ResourceKey<Item> getResourceKey() {
        return resourceKey;
    }
}
