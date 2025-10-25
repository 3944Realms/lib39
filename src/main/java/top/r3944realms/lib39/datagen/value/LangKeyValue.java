package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * The type Lang key value.
 */
@SuppressWarnings("unused")
public class LangKeyValue implements ILangKeyValue {
    /**
     * The Supplier.
     */
    protected final Supplier<?> supplier;
    /**
     * The Key.
     */
    protected String key;
    /**
     * The Us en.
     */
    protected final String US_EN;
    /**
     * The Sim cn.
     */
    protected final String SIM_CN;
    /**
     * The Tra cn.
     */
    protected final String TRA_CN;
    /**
     * The Lzh.
     */
    protected final String LZH;
    /**
     * The Default.
     */
    protected final Boolean Default;
    /**
     * The Mpe.
     */
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

    /**
     * Of supplier lang key value.
     *
     * @param supplier the supplier
     * @param MPE      the mpe
     * @param US_EN    the us en
     * @param SIM_CN   the sim cn
     * @param TRA_CN   the tra cn
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }

    /**
     * Of supplier lang key value.
     *
     * @param supplier the supplier
     * @param MPE      the mpe
     * @param US_EN    the us en
     * @param SIM_CN   the sim cn
     * @param TRA_CN   the tra cn
     * @param LZH      the lzh
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }

    /**
     * Of supplier lang key value.
     *
     * @param supplier  the supplier
     * @param MPE       the mpe
     * @param US_EN     the us en
     * @param SIM_CN    the sim cn
     * @param TRA_CN    the tra cn
     * @param isDefault the is default
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        return new LangKeyValue(supplier, null, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }

    /**
     * Of key lang key value.
     *
     * @param key    the key
     * @param MPE    the mpe
     * @param US_EN  the us en
     * @param SIM_CN the sim cn
     * @param TRA_CN the tra cn
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, null, false);
    }

    /**
     * Of key lang key value.
     *
     * @param key    the key
     * @param MPE    the mpe
     * @param US_EN  the us en
     * @param SIM_CN the sim cn
     * @param TRA_CN the tra cn
     * @param LZH    the lzh
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN, String LZH) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, LZH, false);
    }

    /**
     * Of key lang key value.
     *
     * @param key       the key
     * @param MPE       the mpe
     * @param US_EN     the us en
     * @param SIM_CN    the sim cn
     * @param TRA_CN    the tra cn
     * @param isDefault the is default
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofKey(String key, ModPartEnum MPE,
                                              String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        return new LangKeyValue(null, key, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }

    /**
     * Gets key.
     *
     * @return the key
     */
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
