package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The interface Lang key value collection.
 */
public interface ILangKeyValueCollection {

    /**
     * Gets values.
     *
     * @return the values
     */
    List<? extends ILangKeyValue> getValues();

    /**
     * Gets lang.
     *
     * @param locale the locale
     * @param key    the key
     * @return the lang
     */
    static String getLang(McLocale locale, @NotNull ILangKeyValue key) {
        return key.getLang(locale);
    }

}
