package top.r3944realms.lib39.example.datagen.provider;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.example.datagen.generator.ExBlockModelGenerator;
import top.r3944realms.lib39.example.datagen.generator.ExItemModelGenerators;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ExModelDataProvider extends ModelProvider {
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;
    public ExModelDataProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
        this.blockStatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {

        ItemInfoCollector itemModelOutput = new ItemInfoCollector(this::getKnownItems);
        BlockStateGeneratorCollector blockModelOutput = new BlockStateGeneratorCollector(this::getKnownBlocks);
        SimpleModelCollector modelOutput = new SimpleModelCollector();
        itemModelOutput.finalizeAndValidate();
        this.registerModels(new ExBlockModelGenerator(blockModelOutput, itemModelOutput, modelOutput), new ExItemModelGenerators(itemModelOutput, modelOutput));
        return CompletableFuture.allOf(blockModelOutput.save(cachedOutput, this.blockStatePathProvider), modelOutput.save(cachedOutput, this.modelPathProvider), itemModelOutput.save(cachedOutput, this.itemInfoPathProvider));
    }

    static class ItemInfoCollector implements ItemModelOutput {
        private final Map<Item, ClientItem> itemInfos;
        private final Map<Item, Item> copies;
        private final Supplier<Stream<? extends Holder<Item>>> knownItems;

        public ItemInfoCollector(Supplier<Stream<? extends Holder<Item>>> knownItems) {
            this.itemInfos = new HashMap<>();
            this.copies = new HashMap<>();
            this.knownItems = knownItems;
        }


        public void accept(@NotNull Item item, ItemModel.@NotNull Unbaked model) {
            this.register(item, new ClientItem(model, ClientItem.Properties.DEFAULT));
        }

        public void register(@NotNull Item item, @NotNull ClientItem clientItem) {
            ClientItem clientitem = this.itemInfos.put(item, clientItem);
            if (clientitem != null) {
                throw new IllegalStateException("Duplicate item model definition for " + item);
            }
        }

        public void copy(@NotNull Item item, @NotNull Item copyItem) {
            this.copies.put(copyItem, item);
        }

        public void finalizeAndValidate() {
            (this.knownItems.get()).map(Holder::value).forEach((item) -> {
                if (!this.copies.containsKey(item) && item instanceof BlockItem blockitem) {
                    if (!this.itemInfos.containsKey(blockitem)) {
                        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(blockitem.getBlock());
                        this.accept(blockitem, ItemModelUtils.plainModel(resourcelocation));
                    }
                }

            });
            this.copies.forEach((item, item1) -> {
                ClientItem clientitem = this.itemInfos.get(item1);
                if (clientitem == null) {
                    String item1Name = String.valueOf(item1);
                    throw new IllegalStateException("Missing donor: " + item1Name + " -> " + item);
                } else {
                    this.register(item, clientitem);
                }
            });
            List<ResourceLocation> list = (this.knownItems.get()).filter((holder) -> !this.itemInfos.containsKey(holder.value())).map((holder) -> holder.unwrapKey().orElseThrow().location()).toList();

            if (!list.isEmpty()) {
                LOGGER.warn("Missing item model definitions for: {}", list);
            }
        }

        public CompletableFuture<?> save(CachedOutput cachedOutput, PackOutput.PathProvider pathProvider) {
            return DataProvider.saveAll(cachedOutput, ClientItem.CODEC, (item) -> pathProvider.json(item.builtInRegistryHolder().key().location()), this.itemInfos);
        }
    }

    static class SimpleModelCollector implements BiConsumer<ResourceLocation, ModelInstance> {
        private final Map<ResourceLocation, ModelInstance> models = new HashMap<>();

        SimpleModelCollector() {
        }

        public void accept(ResourceLocation resourceLocation, ModelInstance modelInstance) {
            Supplier<JsonElement> supplier = this.models.put(resourceLocation, modelInstance);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + resourceLocation);
            }
        }

        public CompletableFuture<?> save(CachedOutput cachedOutput, PackOutput.PathProvider pathProvider) {
            Objects.requireNonNull(pathProvider);
            return saveAll(cachedOutput, pathProvider::json, this.models);
        }

        static <T> CompletableFuture<?> saveAll(CachedOutput cachedOutput, Function<T, Path> tPathFunction, Map<T, ? extends Supplier<JsonElement>> tMap) {
            return DataProvider.saveAll(cachedOutput, Supplier::get, tPathFunction, tMap);
        }
    }

    static class BlockStateGeneratorCollector implements Consumer<BlockModelDefinitionGenerator> {
        private final Map<Block, BlockModelDefinitionGenerator> generators = new HashMap<>();
        private final Supplier<Stream<? extends Holder<Block>>> knownBlocks;

        public BlockStateGeneratorCollector(Supplier<Stream<? extends Holder<Block>>> knownBlocks) {
            this.knownBlocks = knownBlocks;
        }

        @Deprecated // Neo: Provided for vanilla/multi-loader compatibility. Use constructor with Supplier parameter.
        public BlockStateGeneratorCollector() {
            this(BuiltInRegistries.BLOCK::listElements);
        }

        public void accept(BlockModelDefinitionGenerator p_405192_) {
            Block block = p_405192_.block();
            BlockModelDefinitionGenerator blockmodeldefinitiongenerator = this.generators.put(block, p_405192_);
            if (blockmodeldefinitiongenerator != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        }

        public void validate() {
            Stream<? extends Holder<Block>> stream = knownBlocks.get();
            List<ResourceLocation> list = stream.filter(p_386843_ -> !this.generators.containsKey(p_386843_.value()))
                    .map(p_386823_ -> p_386823_.unwrapKey().orElseThrow().location())
                    .toList();
            if (!list.isEmpty()) {
                throw new IllegalStateException("Missing blockstate definitions for: " + list);
            }
        }

        public CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider pathProvider) {
            Map<Block, BlockModelDefinition> map = Maps.transformValues(this.generators, BlockModelDefinitionGenerator::create);
            Function<Block, Path> function = p_387598_ -> pathProvider.json(p_387598_.builtInRegistryHolder().key().location());
            return DataProvider.saveAll(output, BlockModelDefinition.CODEC, function, map);
        }
    }
}
