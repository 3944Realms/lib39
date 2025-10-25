package top.r3944realms.lib39.datagen.value;

import java.util.Locale;

/**
 * The enum Mc locale.
 */
public enum McLocale implements ILocaleEntry {
    /**
     * En us mc locale.
     */
    EN_US("en_us", Locale.US),
    /**
     * Zh cn mc locale.
     */
    ZH_CN("zh_cn", Locale.SIMPLIFIED_CHINESE),
    /**
     * Zh tw mc locale.
     */
    ZH_TW("zh_tw", Locale.TRADITIONAL_CHINESE),
    /**
     * The Lzh.
     */
    LZH("lzh", new Locale("lzh", "ZH")),
    /**
     * Ja jp mc locale.
     */
    JA_JP("ja_jp", Locale.JAPAN),
    /**
     * Ko kr mc locale.
     */
    KO_KR("ko_kr", Locale.KOREA),
    /**
     * The Ru ru.
     */
    RU_RU("ru_ru", new Locale("ru", "RU")),
    /**
     * Fr fr mc locale.
     */
    FR_FR("fr_fr", Locale.FRANCE),
    /**
     * De de mc locale.
     */
    DE_DE("de_de", Locale.GERMANY),
    /**
     * The Es es.
     */
    ES_ES("es_es", new Locale("es", "ES"));

    private final String mcCode;
    private final Locale javaLocale;

    McLocale(String mcCode, Locale javaLocale) {
        this.mcCode = mcCode;
        this.javaLocale = javaLocale;
    }

    @Override
    public String mcCode() { return mcCode; }

    @Override
    public Locale javaLocale() { return javaLocale; }
}
