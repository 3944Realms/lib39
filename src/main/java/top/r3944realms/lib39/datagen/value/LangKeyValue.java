package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class LangKeyValue implements ILangKeyValue {
    private final Supplier<?> supplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    private final String LZH;
    private final Boolean Default;
    private final ModPartEnum MPE;
    LangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = Supplier;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    LangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = null;
        this.key = ResourceKey;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    LangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        this(Supplier, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }
    LangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(Supplier, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    LangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(ResourceKey, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    LangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        this(ResourceKey, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }
    LangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN) {
        this(Supplier, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }
    LangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN) {
        this(ResourceKey, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }
    public String getKey() {
        return key;
    }
    @Override
    public String getLang(@NotNull McLocale locale) {
        return switch (locale) {
            case EN_US, JA_JP, KO_KR, RU_RU, DE_DE, ES_ES, FR_FR -> US_EN;
            case ZH_CN -> SIM_CN;
            case ZH_TW -> TRA_CN;
            case LZH -> LZH;
        };
    }
}
