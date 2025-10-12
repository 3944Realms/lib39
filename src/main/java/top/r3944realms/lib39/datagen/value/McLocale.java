package top.r3944realms.lib39.datagen.value;

import java.util.Locale;

public enum McLocale implements ILocaleEntry {
    EN_US("en_us", Locale.US),
    ZH_CN("zh_cn", Locale.SIMPLIFIED_CHINESE),
    ZH_TW("zh_tw", Locale.TRADITIONAL_CHINESE),
    LZH("lzh", new Locale("lzh", "ZH")),
    JA_JP("ja_jp", Locale.JAPAN),
    KO_KR("ko_kr", Locale.KOREA),
    RU_RU("ru_ru", new Locale("ru", "RU")),
    FR_FR("fr_fr", Locale.FRANCE),
    DE_DE("de_de", Locale.GERMANY),
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
