package top.r3944realms.lib39.core.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import top.r3944realms.lib39.datagen.value.ILocaleEntry;
import top.r3944realms.lib39.datagen.value.McLocale;

import java.util.*;

/**
 * The type Locale registry.
 */
@SuppressWarnings("unused")
public class LocaleRegistry {
    private static final Map<String, ILocaleEntry> REGISTRY = new LinkedHashMap<>();

    // 初始化：注册所有枚举值
    static {
        for (McLocale loc : McLocale.values()) {
            register(loc);
        }
    }

    /**
     * 注册（覆盖已有时直接返回旧值）  @param entry the entry
     *
     * @param entry the entry
     * @return the locale entry
     */
    @SuppressWarnings("UnusedReturnValue")
    public static ILocaleEntry register(ILocaleEntry entry) {
        return REGISTRY.putIfAbsent(entry.mcCode().toLowerCase(), entry);
    }

    /**
     * 通过 Minecraft 代码查找  @param code the code
     *
     * @param code the code
     * @return the locale entry
     */
    public static ILocaleEntry fromMcCode(@NotNull String code) {
        return REGISTRY.get(code.toLowerCase());
    }

    /**
     * 列出所有  @return the collection
     *
     * @return the collection
     */
    public static @NotNull @UnmodifiableView Collection<ILocaleEntry> allValues() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    /**
     * 动态注册一个扩展 Locale  @param mcCode the mc code
     *
     * @param mcCode the mc code
     * @param locale the locale
     * @return the locale entry
     */
    public static ILocaleEntry registerDynamic(@NotNull String mcCode, Locale locale) {
        return REGISTRY.computeIfAbsent(mcCode.toLowerCase(),
                k -> new ExtendedLocale(mcCode.toLowerCase(), locale));
    }

    /**
     * 扩展类型
     */
        private record ExtendedLocale(String mcCode, Locale javaLocale) implements ILocaleEntry {

        @Contract(pure = true)
        @Override
            public @NotNull String toString() {
                return "ExtendedLocale[" + mcCode + "]";
            }
        }
}
