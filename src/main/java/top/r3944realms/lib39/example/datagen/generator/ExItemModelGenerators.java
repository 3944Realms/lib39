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

package top.r3944realms.lib39.example.datagen.generator;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import top.r3944realms.lib39.datagen.value.LangKeyValue;
import top.r3944realms.lib39.datagen.value.ModPartEnum;
import top.r3944realms.lib39.example.datagen.data.ExLib39LangKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * The type Slp item model provider.
 */
public class ExItemModelGenerators extends ItemModelGenerators {
    private static List<Item> objectList;

    /**
     * Instantiates a new Slp item model provider.
     *
     * @param output             the output
     * @param existingFileHelper the existing file helper
     */
    public ExItemModelGenerators(ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(itemModelOutput, modelOutput);
        objectList = new ArrayList<>();
        init();
    }

    private void init() {
        for(LangKeyValue obj : ExLib39LangKeys.INSTANCE.getValues()) {
            if(!(obj.isDefault() && obj.getMPE().equals(ModPartEnum.ITEM))) continue;
            objectList.add(obj.getItem());
        }
    }
    @Override
    public void run() {
        DefaultModItemModelRegister();
    }

    /**
     * @implNote <br/>&nbsp;先有纹理才会成功构建
     */
    private void DefaultModItemModelRegister() {
        objectList.forEach(i -> generateFlatItem(i, ModelTemplates.FLAT_ITEM));
    }

}
