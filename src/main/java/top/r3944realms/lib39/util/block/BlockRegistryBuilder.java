package top.r3944realms.lib39.util.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.core.event.CommonEventHandler;

import java.util.function.Supplier;

/**
 * The type Block registry builder.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class BlockRegistryBuilder {
    private String registryName;
    private RegistryObject<Block> blockObject;

    /**
     * 创建新的构建器实例
     *
     * @return the block registry builder
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull BlockRegistryBuilder create() {
        return new BlockRegistryBuilder();
    }

    /**
     * 设置注册名称
     *
     * @param name the name
     * @return the block registry builder
     */
    public BlockRegistryBuilder withName(String name) {
        this.registryName = name;
        return this;
    }

    /**
     * 注册方块（不自动注册物品）
     *
     * @param blockRegister the block register
     * @param blockSupplier the block supplier
     * @return the block registry builder
     */
    public BlockRegistryBuilder registerBlock(@NotNull DeferredRegister<Block> blockRegister, Supplier<? extends Block> blockSupplier) {
        this.blockObject = blockRegister.register(this.registryName, blockSupplier);
        return this;
    }

    /**
     * 注册对应的方块物品
     *
     * @param blockObject           the block object
     * @param blockDeferredRegister the block deferred register
     */
    public void registerBlockItem(RegistryObject<Block> blockObject, @NotNull DeferredRegister<Block> blockDeferredRegister) {
        blockDeferredRegister.register(this.registryName, blockObject);
    }

    /**
     * 内部方法：注册对应的方块物品
     */
    @SafeVarargs
    private void registerBlockItem(RegistryObject<Block> blockObject, ResourceKey<CreativeModeTab>... creativeTabs) {
        CommonEventHandler.Mod.addItemToTabs(blockObject, creativeTabs);
    }

    /**
     * 注册方块和物品到建筑标签页
     *
     * @param blockRegister the block register
     * @param blockSupplier the block supplier
     * @return the block registry builder
     */
    public BlockRegistryBuilder registerWithBuildingTab(DeferredRegister<Block> blockRegister, Supplier<? extends Block> blockSupplier) {
        registerBlock(blockRegister, blockSupplier);
        registerBlockItem(this.blockObject, CreativeModeTabs.BUILDING_BLOCKS);
        return this;
    }

    /**
     * 注册方块和物品到功能标签页
     *
     * @param blockRegister the block register
     * @param blockSupplier the block supplier
     * @return the block registry builder
     */
    public BlockRegistryBuilder registerWithFunctionalTab(DeferredRegister<Block> blockRegister, Supplier<? extends Block> blockSupplier) {
        registerBlock(blockRegister, blockSupplier);
        registerBlockItem(this.blockObject, CreativeModeTabs.FUNCTIONAL_BLOCKS);
        return this;
    }

    /**
     * 获取注册的方块对象
     *
     * @return the registry object
     */
    public RegistryObject<Block> build() {
        return this.blockObject;
    }

}
