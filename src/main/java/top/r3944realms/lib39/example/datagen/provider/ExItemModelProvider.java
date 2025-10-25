/*
 *  Super Lead rope mod
 *  Copyright (C)  2025  R3944Realms
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.r3944realms.lib39.example.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.datagen.value.LangKeyValue;
import top.r3944realms.lib39.datagen.value.ModPartEnum;
import top.r3944realms.lib39.example.datagen.data.ExLib39LangKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Slp item model provider.
 */
public class ExItemModelProvider extends ItemModelProvider {
    private static List<Item> objectList;
    /**
     * The constant GENERATED.
     */
    public static final String GENERATED = "item/generated";
    /**
     * The constant HANDHELD.
     */
    public static final String HANDHELD = "item/handheld";

    /**
     * Instantiates a new Slp item model provider.
     *
     * @param output             the output
     * @param existingFileHelper the existing file helper
     */
    public ExItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lib39.MOD_ID, existingFileHelper);
        objectList = new ArrayList<>();
        init();
    }

    @Override
    protected void registerModels() {
        DefaultModItemModelRegister();
    }
    private void init() {
        for(LangKeyValue obj : ExLib39LangKeys.INSTANCE.getValues()) {
            if(!(obj.isDefault() && obj.getMPE().equals(ModPartEnum.ITEM))) continue;
            objectList.add(obj.getItem());
        }
    }
    /**
     * @implNote <br/>&nbsp;先有纹理才会成功构建
     */
    private void DefaultModItemModelRegister() {
        objectList.forEach(this::basicItem);
    }

    /**
     * Item generate model.
     *
     * @param item     the item
     * @param location the location
     */
    public void itemGenerateModel(Item item, ResourceLocation location){
        withExistingParent(itemName(item), GENERATED).texture("layer0", location);
    }

    /**
     * Item hand held model.
     *
     * @param item     the item
     * @param location the location
     */
    public void itemHandHeldModel(Item item, ResourceLocation location){
        withExistingParent(itemName(item), HANDHELD).texture("layer0", location);
    }

    /**
     * Item name string.
     *
     * @param item the item
     * @return the string
     */
    public String itemName(Item item){
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    /**
     * Resource item resource location.
     *
     * @param path the path
     * @return the resource location
     */
    public ResourceLocation resourceItem(String path){
        return modLoc("item/" + path);
    }
}
