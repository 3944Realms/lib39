package top.r3944realms.lib39.example.datagen.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import top.r3944realms.lib39.datagen.value.ILangKeyValueCollection;
import top.r3944realms.lib39.datagen.value.LangKeyValue;
import top.r3944realms.lib39.datagen.value.ModPartEnum;
import top.r3944realms.lib39.example.core.register.ExLib39Items;

import java.util.ArrayList;
import java.util.List;

/**
 * The enum Ex lib 39 lang keys.
 */
public enum ExLib39LangKeys implements ILangKeyValueCollection {
    /**
     * Instance ex lib 39 lang keys.
     */
    INSTANCE;
    ExLib39LangKeys() {
        initLangKeyValues();
    }

    /**
     * The Lang key values.
     */
    final List<LangKeyValue> langKeyValues = new ArrayList<>();


    /**
     * Init lang key values.
     */
    public void initLangKeyValues() {
        addLang(LangKeyValue.ofSupplier(
                ExLib39Items.FABRIC, ModPartEnum.ITEM,
                "Fabric", "织布" , "織布" ,"織" , true
        ));
        addLang(LangKeyValue.ofSupplier(
                ExLib39Items.NEOFORGE, ModPartEnum.ITEM,
                "NeoForge", "小狐狸", "狐狸", "狸", true
        ));
   }


    /**
     * Add lang.
     *
     * @param keyValue the key value
     */
    public void addLang(LangKeyValue keyValue) {
        langKeyValues.add(keyValue);
   }

    /**
     * Clear.
     */
    public void clear() {
       langKeyValues.clear();
   }


    @Contract(pure = true)
    @Override
    public @Unmodifiable List<LangKeyValue> getValues() {
        return List.copyOf(langKeyValues);
    }

}
