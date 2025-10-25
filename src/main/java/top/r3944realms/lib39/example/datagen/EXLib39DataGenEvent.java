package top.r3944realms.lib39.example.datagen;

import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.datagen.provider.SimpleLanguageProvider;
import top.r3944realms.lib39.datagen.value.McLocale;
import top.r3944realms.lib39.example.datagen.data.ExLib39LangKeys;
import top.r3944realms.lib39.example.datagen.provider.ExItemModelProvider;

/**
 * The type Ex lib 39 data gen event.
 */
public class EXLib39DataGenEvent {
    /**
     * The Logger.
     */
    static Logger logger = LoggerFactory.getLogger(EXLib39DataGenEvent.class);

    /**
     * Gather data.
     *
     * @param event the event
     */
    public static void gatherData(GatherDataEvent event) {
        logger.info("GatherDataEvent thread: {}", Thread.currentThread().getName());
        LanguageGenerator(event, McLocale.EN_US);
        LanguageGenerator(event, McLocale.ZH_CN);
        LanguageGenerator(event, McLocale.ZH_TW);
        LanguageGenerator(event, McLocale.LZH);
        ModelDataGenerate(event);
    }
    private static void LanguageGenerator(GatherDataEvent event, McLocale language) {
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<SimpleLanguageProvider>) pOutput -> new SimpleLanguageProvider(pOutput, Lib39.MOD_ID ,language ,ExLib39LangKeys.INSTANCE)
        );
    }

    private static void ModelDataGenerate(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ExItemModelProvider>) pOutput -> new ExItemModelProvider(pOutput, event.getExistingFileHelper())
        );
    }
}
