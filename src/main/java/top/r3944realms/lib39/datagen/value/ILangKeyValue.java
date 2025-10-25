package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The interface Lang key value.
 */
public interface ILangKeyValue {
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

    /**
     * Gets lang.
     *
     * @param locale the locale
     * @return the lang
     */
    String getLang(McLocale locale);

    /**
     * Gets values.
     *
     * @return the values
     */
    List<ILangKeyValue> getValues();
}
