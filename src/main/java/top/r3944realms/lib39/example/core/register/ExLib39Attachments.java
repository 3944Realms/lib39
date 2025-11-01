package top.r3944realms.lib39.example.core.register;

import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.content.capability.TestSyncData;

import java.util.function.Supplier;

public class ExLib39Attachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Lib39.MOD_ID);

    public static final Supplier<AttachmentType<TestSyncData>> TEST_DATA_ATTACHMENT =
            ATTACHMENTS.register(
                    "test_data",
                    () -> AttachmentType
                            .serializable(holder -> new TestSyncData(holder))
                            .copyOnDeath()
                            .copyHandler((attachment, holder, provider) -> {
                                if (!(holder instanceof Entity newEntity)) {
                                    throw new IllegalArgumentException("TestSyncData can only be copied to entities");
                                }
                                return attachment.createSyncCopy(newEntity);
                            })
                            .sync(TestSyncData.CODEC)
                            .build()
            );

    public static void register(IEventBus iModBus) {
        ATTACHMENTS.register(iModBus);
    }
}