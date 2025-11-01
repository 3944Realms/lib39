package top.r3944realms.lib39.example.content.capability;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.example.core.register.ExLib39Attachments;
import top.r3944realms.lib39.example.core.register.ExLib39ItemResourceKeys;
import top.r3944realms.lib39.util.storage.valueio.ValueInputReader;
import top.r3944realms.lib39.util.storage.valueio.ValueOutputWriter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 测试同步数据实现
 */
public class TestSyncData extends AbstractedTestSyncData {
    private static final Random RANDOM = new Random();

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Lib39.MOD_ID, "test_sync_data");

    // 网络同步编解码器
    public static final StreamCodec<FriendlyByteBuf, TestSyncData> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TestSyncData::getTestString,
            ByteBufCodecs.INT, TestSyncData::getTestInt,
            ByteBufCodecs.BOOL, TestSyncData::isTestBoolean,
            ByteBufCodecs.DOUBLE, TestSyncData::getTestDouble,
            TestData.CODEC, TestSyncData::getCustomData,
            ByteBufCodecs.INT, TestSyncData::getCounter,
            ByteBufCodecs.LONG, TestSyncData::getLastSyncTime,
            (testString, testInt, testBoolean, testDouble, customData, counter, lastSyncTime) -> {
                TestSyncData data = new TestSyncData();
                data.testString = testString;
                data.testInt = testInt;
                data.testBoolean = testBoolean;
                data.testDouble = testDouble;
                data.customData = customData;
                data.counter = counter;
                data.lastSyncTime = lastSyncTime;
                return data;
            }
    );
    /** 重置为默认值 */
    public void resetToDefaults() {
        this.testString = "default_string";
        this.testInt = 0;
        this.testDouble = 0.0;
        this.testBoolean = false;
        this.counter = 0;
        this.lastSyncTime = System.currentTimeMillis();
        this.customData = new TestData("init", 0, false);
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    /** 随机生成一组数据 */
    public void generateRandomData() {
        this.testString = "rand_" + RANDOM.nextInt(10000);
        this.testInt = RANDOM.nextInt(1000);
        this.testDouble = RANDOM.nextDouble() * 100.0;
        this.testBoolean = RANDOM.nextBoolean();
        this.counter = RANDOM.nextInt(20);
        this.lastSyncTime = System.currentTimeMillis();
        this.customData = new TestData(
                "custom_" + RANDOM.nextInt(1000),
                RANDOM.nextInt(500),
                RANDOM.nextBoolean()
        );
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }
    // 数据字段
    private String testString = "default_value";
    private int testInt = 42;
    private boolean testBoolean = true;
    private double testDouble = 3.14159;
    private int counter = 0;
    private long lastSyncTime = 0L;
    private TestData customData = new TestData("default", 100, false);
    private Entity self;

    // --- 构造函数区域 ---

    /** 默认构造函数（反序列化/同步使用） */
    public TestSyncData() {
        super(ID);
    }

    /** 用于实体附加 */
    public TestSyncData(Entity entity) {
        super(ID);
        this.self = entity;
    }

    /** 用于 AttachmentType.serializable(holder -> ...) */
    public TestSyncData(IAttachmentHolder holder) {
        this(holder instanceof Entity e ? e : null);
        if (self == null) throw new IllegalArgumentException("TestSyncData must be attached to Entity");
    }

    // --- 数据逻辑部分 ---

    public String getTestString() { return testString; }
    public void setTestString(String s) {
        this.testString = s;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public int getTestInt() { return testInt; }
    public void setTestInt(int i) {
        this.testInt = i;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public boolean isTestBoolean() { return testBoolean; }
    public void setTestBoolean(boolean b) { this.testBoolean = b;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public double getTestDouble() { return testDouble; }
    public void setTestDouble(double d) {
        this.testDouble = d;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public int getCounter() { return counter; }
    public void incrementCounter() {
        this.counter++;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public long getLastSyncTime() { return lastSyncTime; }
    public void updateSyncTime() {
        this.lastSyncTime = System.currentTimeMillis();
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public TestData getCustomData() { return customData; }
    public void setCustomData(TestData data) {
        this.customData = data;
        self.syncData(ExLib39Attachments.TEST_DATA_ATTACHMENT);
    }

    public boolean validateData() {
        return testString != null && !testString.isEmpty()
                && customData != null && customData.getName() != null
                && counter >= 0 && testInt >= 0;
    }

    // --- 实体管理 ---

    public int entityId() { return self != null ? self.getId() : -1; }

    public void setSelf(Entity entity) {
        this.self = entity;
    }
    public TestSyncData createSyncCopy(Entity entity) {
        TestSyncData copy = new TestSyncData(entity);
        copy.testString = testString;
        copy.testInt = testInt;
        copy.testBoolean = testBoolean;
        copy.testDouble = testDouble;
        copy.counter = counter;
        copy.lastSyncTime = lastSyncTime;
        copy.customData = new TestData(customData.getName(), customData.getValue(), customData.isFlag());
        return copy;
    }

    @Override
    public void serialize(@NotNull ValueOutput out) {
        ValueOutputWriter.of(out)
                .string("test_string", testString)
                .intValue("test_int", testInt)
                .booleanValue("test_boolean", testBoolean)
                .doubleValue("test_double", testDouble)
                .intValue("counter", counter)
                .longValue("last_sync_time", lastSyncTime)
                .nested("custom_data", n ->
                        n.string("name", customData.getName())
                                .intValue("value", customData.getValue())
                                .booleanValue("flag", customData.isFlag()));
    }

    @Override
    public void deserialize(@NotNull ValueInput in) {
        ValueInputReader.of(in)
                .string("test_string", s -> testString = s)
                .intValue("test_int", i -> testInt = i)
                .booleanValue("test_boolean", b -> testBoolean = b)
                .doubleValue("test_double", d -> testDouble = d)
                .intValue("counter", c -> counter = c)
                .longValue("last_sync_time", t -> lastSyncTime = t)
                .nested("custom_data", n -> {
                    AtomicReference<String> name = new AtomicReference<>("default");
                    AtomicInteger value = new AtomicInteger(100);
                    AtomicBoolean flag = new AtomicBoolean(false);
                    n.string("name", name::set)
                            .intValue("value", value::set)
                            .booleanValue("flag", flag::set);
                    customData = new TestData(name.get(), value.get(), flag.get());
                });
    }

    @Override
    public String toString() {
        return String.format(
                "TestSyncData[%s,id=%d]{str=%s,int=%d,bool=%s,double=%.2f,counter=%d,time=%d,custom=%s}",
                self == null ? "null" : self.getClass().getSimpleName(),
                entityId(), testString, testInt, testBoolean, testDouble, counter, lastSyncTime, customData
        );
    }
}
