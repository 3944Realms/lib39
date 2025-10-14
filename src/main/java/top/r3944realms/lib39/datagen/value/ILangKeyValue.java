package top.r3944realms.lib39.datagen.value;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ILangKeyValue {
    static String getLang(McLocale locale, @NotNull ILangKeyValue key) {
        return key.getLang(locale);
    }
    String getLang(McLocale locale);
    List<ILangKeyValue> getValues();
}
