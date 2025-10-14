package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class LangKeyValue implements ILangKeyValue {
    protected final Supplier<?> supplier;
    protected String key;
    protected final String US_EN;
    protected final String SIM_CN;
    protected final String TRA_CN;
    protected final String LZH;
    protected final Boolean Default;
    protected final ModPartEnum MPE;
    private LangKeyValue(Supplier<?> supplier, String key, ModPartEnum MPE,
                         String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = supplier;
        this.key = key;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }

    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }

    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
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

    @Override
    public List<ILangKeyValue> getValues() {
        return List.of();
    }
}
