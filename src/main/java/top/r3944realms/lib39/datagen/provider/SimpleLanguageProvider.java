package top.r3944realms.lib39.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.datagen.value.ILangKeyValue;
import top.r3944realms.lib39.datagen.value.ILangKeyValueCollection;
import top.r3944realms.lib39.datagen.value.McLocale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Simple language provider.
 */
public class SimpleLanguageProvider extends LanguageProvider {
    private final McLocale language;
    private final ILangKeyValueCollection langKeyValueCollection;
    private final Map<String, String> translationMap; // Better naming
    private final List<String> orderedKeys; // Better naming than "objects"

    /**
     * Instantiates a new Simple language provider.
     *
     * @param output                 the output
     * @param modId                  the mod id
     * @param language               the language
     * @param langKeyValueCollection the lang key value collection
     */
    public SimpleLanguageProvider(PackOutput output, String modId,
                                  @NotNull McLocale language,
                                  ILangKeyValueCollection langKeyValueCollection) {
        super(output, modId, language.mcCode());
        this.language = language;
        this.langKeyValueCollection = langKeyValueCollection;
        this.translationMap = new HashMap<>();
        this.orderedKeys = new ArrayList<>();
        initializeTranslations();
    }

    private void initializeTranslations() {
        for (ILangKeyValue langKeyValue : langKeyValueCollection.getValues()) {
            String key = langKeyValue.getKey();
            String value = langKeyValue.getLang(language);

            if (!translationMap.containsKey(key)) {
                orderedKeys.add(key);
            }
            translationMap.put(key, value);
        }
    }

    @Override
    protected void addTranslations() {
        orderedKeys.forEach(key -> add(key, translationMap.get(key)));
        validateTranslations();
    }

    private void validateTranslations() {
        long addedCount = orderedKeys.stream()
                .filter(translationMap::containsKey)
                .count();

        LOGGER.info("Added {}/{} translations for {}",
                addedCount, orderedKeys.size(), language.mcCode());
    }
}
