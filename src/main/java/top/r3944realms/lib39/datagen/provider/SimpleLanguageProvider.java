package top.r3944realms.lib39.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.datagen.value.ILangKeyValue;
import top.r3944realms.lib39.datagen.value.McLocale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class SimpleLanguageProvider extends LanguageProvider {
    private final McLocale language;
    private final ILangKeyValue langKeyValue;
    private final Map<String, String> lanKeyMap;
    private static final List<String> objects = new ArrayList<>();
    public SimpleLanguageProvider(PackOutput output, String modId, @NotNull McLocale Lan, ILangKeyValue langKeyValue) {
        super(output, modId, Lan.mcCode());
        this.language = Lan;
        this.langKeyValue = langKeyValue;
        lanKeyMap = new HashMap<>();
        init();
    }
    private void init() {
       for (ILangKeyValue iLangKeyValue : langKeyValue.getValues()) {
           lanKeyMap.put(language.mcCode(), iLangKeyValue.getLang(language));
       }
    }
    private void addLang(String Key, String value) {
        if (!objects.contains(Key)) objects.add(Key);
        lanKeyMap.put(Key, value);
    }

    @Override
    protected void addTranslations() {
        objects.forEach(key -> add(key, lanKeyMap.get(key)));
    }
}
