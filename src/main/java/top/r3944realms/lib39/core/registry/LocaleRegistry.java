package top.r3944realms.lib39.core.registry;

import top.r3944realms.lib39.datagen.value.ILocaleEntry;
import top.r3944realms.lib39.datagen.value.McLocale;

import java.util.*;

@SuppressWarnings("unused")
public class LocaleRegistry {
    private static final Map<String, ILocaleEntry> REGISTRY = new LinkedHashMap<>();

    // 初始化：注册所有枚举值
    static {
        for (McLocale loc : McLocale.values()) {
            register(loc);
        }
    }

    /** 注册（覆盖已有时直接返回旧值） */
    @SuppressWarnings("UnusedReturnValue")
    public static ILocaleEntry register(ILocaleEntry entry) {
        return REGISTRY.putIfAbsent(entry.mcCode().toLowerCase(), entry);
    }

    /** 通过 Minecraft 代码查找 */
    public static ILocaleEntry fromMcCode(String code) {
        return REGISTRY.get(code.toLowerCase());
    }

    /** 列出所有 */
    public static Collection<ILocaleEntry> allValues() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }

    /** 动态注册一个扩展 Locale */
    public static ILocaleEntry registerDynamic(String mcCode, Locale locale) {
        return REGISTRY.computeIfAbsent(mcCode.toLowerCase(),
                k -> new ExtendedLocale(mcCode.toLowerCase(), locale));
    }

    /**
     * 扩展类型
     */
        private record ExtendedLocale(String mcCode, Locale javaLocale) implements ILocaleEntry {

        @Override
            public String toString() {
                return "ExtendedLocale[" + mcCode + "]";
            }
        }
}
