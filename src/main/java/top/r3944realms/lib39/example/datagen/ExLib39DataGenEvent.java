package top.r3944realms.lib39.example.datagen;

import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.datagen.provider.SimpleLanguageProvider;
import top.r3944realms.lib39.datagen.value.McLocale;
import top.r3944realms.lib39.example.datagen.data.ExLib39LangKeys;
import top.r3944realms.lib39.example.datagen.provider.ExModelDataProvider;

/**
 * The type Ex lib 39 data gen event.
 */
public class ExLib39DataGenEvent {
    /**
     * The Logger.
     */
    static Logger logger = LoggerFactory.getLogger(ExLib39DataGenEvent.class);

    /**
     * Gather data.
     *
     * @param event the event
     */
    public static void gatherData(GatherDataEvent.Client event) {
        logger.info("GatherDataEvent thread: {}", Thread.currentThread().getName());
        LanguageGenerate(event, McLocale.EN_US);
        LanguageGenerate(event, McLocale.ZH_CN);
        LanguageGenerate(event, McLocale.ZH_TW);
        LanguageGenerate(event, McLocale.LZH);
        ModelDataGenerate(event);
    }
    private static void LanguageGenerate(GatherDataEvent event, McLocale language) {
        event.getGenerator().addProvider(
                true,
                (DataProvider.Factory<SimpleLanguageProvider>) pOutput -> new SimpleLanguageProvider(pOutput, Lib39.MOD_ID ,language ,ExLib39LangKeys.INSTANCE)
        );
    }

    private static void ModelDataGenerate(GatherDataEvent event) {
        event.getGenerator().addProvider(
                true,
                (DataProvider.Factory<ExModelDataProvider>) pOutput -> new ExModelDataProvider(pOutput, Lib39.MOD_ID)
        );
    }
}
