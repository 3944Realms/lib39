package top.r3944realms.lib39.datagen.value;

/**
 * The interface Lang key value.
 */
public interface ILangKeyValue {
    /**
     * Gets key.
     *
     * @return the key
     */
    String getKey();

    /**
     * Gets lang.
     *
     * @param locale the locale
     * @return the lang
     */
    String getLang(McLocale locale);

}
