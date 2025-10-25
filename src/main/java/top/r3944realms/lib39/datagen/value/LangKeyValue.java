package top.r3944realms.lib39.datagen.value;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
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
    protected final String key;
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

    /**
     * Instantiates a new Lang key value.
     *
     * @param builder the builder
     */
    protected LangKeyValue(Builder builder) {
        this.supplier = builder.supplier;
        this.key = builder.key;
        this.MPE = builder.MPE;
        this.US_EN = builder.US_EN;
        this.SIM_CN = builder.SIM_CN;
        this.TRA_CN = builder.TRA_CN;
        this.LZH = builder.LZH;
        this.Default = builder.Default;
    }

    /**
     * Builder for LangKeyValue
     */
    public static class Builder {
        private Supplier<?> supplier;
        private String key;
        private ModPartEnum MPE;
        private String US_EN;
        private String SIM_CN;
        private String TRA_CN;
        private String LZH;
        private Boolean Default = false;

        /**
         * Set supplier
         *
         * @param supplier the supplier
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder supplier(Supplier<?> supplier) {
            this.supplier = supplier;
            return this;
        }

        /**
         * Set key
         *
         * @param key the key
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set mod part enum
         *
         * @param MPE the mpe
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder MPE(ModPartEnum MPE) {
            this.MPE = MPE;
            return this;
        }

        /**
         * Set US English translation
         *
         * @param US_EN the us en
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder US_EN(String US_EN) {
            this.US_EN = US_EN;
            return this;
        }

        /**
         * Set Simplified Chinese translation
         *
         * @param SIM_CN the sim cn
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder SIM_CN(String SIM_CN) {
            this.SIM_CN = SIM_CN;
            return this;
        }

        /**
         * Set Traditional Chinese translation
         *
         * @param TRA_CN the tra cn
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder TRA_CN(String TRA_CN) {
            this.TRA_CN = TRA_CN;
            return this;
        }

        /**
         * Set Literary Chinese translation
         *
         * @param LZH the lzh
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder LZH(String LZH) {
            this.LZH = LZH;
            return this;
        }

        /**
         * Set as default
         *
         * @param isDefault the is default
         * @return the builder
         */
        @Contract("_ -> this")
        public Builder isDefault(Boolean isDefault) {
            this.Default = isDefault;
            return this;
        }

        /**
         * Build the LangKeyValue instance
         *
         * @return the lang key value
         */
        @NotNull
        public LangKeyValue build() {
            // Validate required fields
            if (MPE == null) {
                throw new IllegalStateException("MPE (ModPartEnum) is required");
            }
            if (US_EN == null) {
                throw new IllegalStateException("US_EN translation is required");
            }
            if (SIM_CN == null) {
                throw new IllegalStateException("SIM_CN translation is required");
            }
            if (TRA_CN == null) {
                throw new IllegalStateException("TRA_CN translation is required");
            }
            // Either supplier or key must be provided, but not both
            if (supplier == null && key == null) {
                throw new IllegalStateException("Either supplier or key must be provided");
            }
            if (supplier != null && key != null) {
                throw new IllegalStateException("Cannot provide both supplier and key");
            }
            return new LangKeyValue(this);
        }
    }

    /**
     * Create a new builder instance
     *
     * @return the builder
     */
    @Contract(" -> new")
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /**
     * Create builder with supplier
     *
     * @param supplier the supplier
     * @return the builder
     */
    @Contract("_ -> new")
    public static @NotNull Builder withSupplier(Supplier<?> supplier) {
        return new Builder().supplier(supplier);
    }

    /**
     * Create builder with key
     *
     * @param key the key
     * @return the builder
     */
    @Contract("_ -> new")
    public static @NotNull Builder withKey(String key) {
        return new Builder().key(key);
    }

    // 保持原有的静态工厂方法作为便捷方法

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
        return builder()
                .supplier(supplier)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .build();
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
        return builder()
                .supplier(supplier)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .LZH(LZH)
                .build();
    }

    /**
     * Of supplier lang key value.
     *
     * @param supplier  the supplier
     * @param MPE       the mpe
     * @param US_EN     the us en
     * @param SIM_CN    the sim cn
     * @param TRA_CN    the tra cn
     * @param LZH       the lzh
     * @param isDefault the is default
     * @return the lang key value
     */
    @Contract(value = "_, _, _, _, _, _, _ -> new", pure = true)
    public static @NotNull LangKeyValue ofSupplier(Supplier<?> supplier, ModPartEnum MPE,
                                                   String US_EN, String SIM_CN, String TRA_CN, String LZH, boolean isDefault) {
        return builder()
                .supplier(supplier)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .LZH(LZH)
                .isDefault(isDefault)
                .build();
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
        return builder()
                .supplier(supplier)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .isDefault(isDefault)
                .build();
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
        return builder()
                .key(key)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .build();
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
        return builder()
                .key(key)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .LZH(LZH)
                .build();
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
        return builder()
                .key(key)
                .MPE(MPE)
                .US_EN(US_EN)
                .SIM_CN(SIM_CN)
                .TRA_CN(TRA_CN)
                .isDefault(isDefault)
                .build();
    }

    @Override
    public String getKey() {
        return Objects.requireNonNullElseGet(key, () -> switch (MPE) {
            case ITEM -> getItem().getDescriptionId();
            case BLOCK -> getBlock().getDescriptionId();
            default ->
                    throw new UnsupportedOperationException("The Key value is NULL! Please use the correct constructor and write the parameters correctly");
        });
    }

    @Override
    public String getLang(@NotNull McLocale locale) {
        return switch (locale) {
            case EN_US, JA_JP, KO_KR, RU_RU, DE_DE, ES_ES, FR_FR -> US_EN;
            case ZH_CN -> SIM_CN;
            case ZH_TW -> TRA_CN;
            case LZH -> LZH != null ? LZH : TRA_CN; // Fallback to TRA_CN if LZH is null
        };
    }


    /**
     * Gets supplier.
     *
     * @return the supplier
     */
// Getters for all fields
    public Supplier<?> getSupplier() { return supplier; }

    /**
     * Gets us en.
     *
     * @return the us en
     */
    public String getUS_EN() { return US_EN; }

    /**
     * Gets sim cn.
     *
     * @return the sim cn
     */
    public String getSIM_CN() { return SIM_CN; }

    /**
     * Gets tra cn.
     *
     * @return the tra cn
     */
    public String getTRA_CN() { return TRA_CN; }

    /**
     * Gets lzh.
     *
     * @return the lzh
     */
    public String getLZH() { return LZH; }

    /**
     * Is default boolean.
     *
     * @return the boolean
     */
    public Boolean isDefault() { return Default; }

    /**
     * Gets mpe.
     *
     * @return the mpe
     */
    public ModPartEnum getMPE() { return MPE; }

    /**
     * Gets item.
     *
     * @return the item
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Item getItem() throws IllegalArgumentException {
        if(MPE == ModPartEnum.ITEM) {
            return (Item) supplier.get();
        }
        else throw new IllegalCallerException("Target's MPE is not ModPartEnum#ITEM.");
    }

    /**
     * Gets block.
     *
     * @return the block
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Block getBlock() throws IllegalArgumentException  {
        if(MPE == ModPartEnum.BLOCK) {
            return (Block) supplier.get();
        }
        else throw new IllegalCallerException("Target's MPE is not ModPartEnum#BLOCK.");
    }
    @Override
    public String toString() {
        return "LangKeyValue{" +
                "key='" + key + '\'' +
                ", US_EN='" + US_EN + '\'' +
                ", SIM_CN='" + SIM_CN + '\'' +
                ", MPE=" + MPE +
                '}';
    }
}