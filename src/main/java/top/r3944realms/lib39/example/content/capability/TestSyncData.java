package top.r3944realms.lib39.example.content.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import top.r3944realms.lib39.Lib39;
import top.r3944realms.lib39.util.nbt.NBTReader;
import top.r3944realms.lib39.util.nbt.NBTWriter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 测试同步数据实现
 */
@SuppressWarnings("unused")
public class TestSyncData extends AbstractedTestSyncData {
    /**
     * The constant ID.
     */
    public static final ResourceLocation ID = new ResourceLocation(Lib39.MOD_ID, "test_sync_data");

    // NBT 键常量
    private static final String NBT_KEY_STRING = "test_string";
    private static final String NBT_KEY_INT = "test_int";
    private static final String NBT_KEY_BOOLEAN = "test_boolean";
    private static final String NBT_KEY_DOUBLE = "test_double";
    private static final String NBT_KEY_COUNTER = "counter";
    private static final String NBT_KEY_SYNC_TIME = "last_sync_time";
    private static final String NBT_KEY_CUSTOM_DATA = "custom_data";
    private static final String NBT_KEY_CUSTOM_NAME = "name";
    private static final String NBT_KEY_CUSTOM_VALUE = "value";
    private static final String NBT_KEY_CUSTOM_FLAG = "flag";

    // 数据字段
    private String testString = "default_value";
    private int testInt = 42;
    private boolean testBoolean = true;
    private double testDouble = 3.14159;
    private int counter = 0;
    private long lastSyncTime = 0L;
    private TestData customData = new TestData("default", 100, false);
    private Entity self;

    /**
     * 构造函数
     *
     * @param entity 关联的实体
     */
    public TestSyncData(Entity entity) {
        super(ID);
        this.self = entity;
    }

    /**
     * 构造函数（用于测试）
     *
     * @param entityId 实体ID
     * @param self     the self
     */
    public TestSyncData(int entityId, Entity self) {
        super(ID);
        this.self = self;
    }

    /**
     * 构造函数（用于数据包反序列化）
     *
     * @param buf 字节缓冲区
     */
    public TestSyncData(FriendlyByteBuf buf) {
        super(ID);
        this.self = null; // 实体在从数据包重建时可能为null，需要在接收端设置
        fromBytes(buf);
    }

    /**
     * 将数据写入字节缓冲区（用于网络传输）
     *
     * @param buf 字节缓冲区
     */
    public void toBytes(FriendlyByteBuf buf) {
        // 写入基本类型字段
        buf.writeUtf(testString != null ? testString : "");
        buf.writeInt(testInt);
        buf.writeBoolean(testBoolean);
        buf.writeDouble(testDouble);
        buf.writeInt(counter);
        buf.writeLong(lastSyncTime);

        // 写入自定义数据
        if (customData != null) {
            buf.writeUtf(customData.getName() != null ? customData.getName() : "");
            buf.writeInt(customData.getValue());
            buf.writeBoolean(customData.isFlag());
        } else {
            buf.writeUtf("");
            buf.writeInt(0);
            buf.writeBoolean(false);
        }

        // 写入实体ID（如果实体存在）
        if (self != null) {
            buf.writeInt(self.getId());
        } else {
            buf.writeInt(-1);
        }

        // 写入脏数据状态
        buf.writeBoolean(isDirty());
    }

    /**
     * 从字节缓冲区读取数据（用于网络传输）
     *
     * @param buf 字节缓冲区
     */
    public void fromBytes(@NotNull FriendlyByteBuf buf) {
        // 读取基本类型字段
        this.testString = buf.readUtf(32767); // Minecraft字符串最大长度
        this.testInt = buf.readInt();
        this.testBoolean = buf.readBoolean();
        this.testDouble = buf.readDouble();
        this.counter = buf.readInt();
        this.lastSyncTime = buf.readLong();

        // 读取自定义数据
        String customName = buf.readUtf();
        int customValue = buf.readInt();
        boolean customFlag = buf.readBoolean();
        this.customData = new TestData(customName, customValue, customFlag);

        // 读取实体ID（在接收端可能需要额外处理）
        int entityId = buf.readInt();

        // 读取脏数据状态
        boolean wasDirty = buf.readBoolean();
        if (wasDirty) {
            markDirty();
        }
    }

    /**
     * 静态方法：从字节缓冲区创建 TestSyncData 实例
     *
     * @param buf 字节缓冲区
     * @return 新的 TestSyncData 实例
     */
    public static TestSyncData staticFromBytes(FriendlyByteBuf buf) {
        return new TestSyncData(buf);
    }

    @Override
    public String getTestString() {
        return testString;
    }

    @Override
    public void setTestString(String value) {
        if (!java.util.Objects.equals(this.testString, value)) {
            this.testString = value;
            markDirty();
        }
    }

    @Override
    public int getTestInt() {
        return testInt;
    }

    @Override
    public void setTestInt(int value) {
        if (this.testInt != value) {
            this.testInt = value;
            markDirty();
        }
    }

    @Override
    public boolean isTestBoolean() {
        return testBoolean;
    }

    @Override
    public void setTestBoolean(boolean value) {
        if (this.testBoolean != value) {
            this.testBoolean = value;
            markDirty();
        }
    }

    @Override
    public double getTestDouble() {
        return testDouble;
    }

    @Override
    public void setTestDouble(double value) {
        if (this.testDouble != value) {
            this.testDouble = value;
            markDirty();
        }
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void incrementCounter() {
        this.counter++;
        markDirty();
    }

    @Override
    public long getLastSyncTime() {
        return lastSyncTime;
    }

    @Override
    public void updateSyncTime() {
        this.lastSyncTime = System.currentTimeMillis();
        markDirty();
    }

    @Override
    public TestData getCustomData() {
        return customData;
    }

    @Override
    public void setCustomData(TestData data) {
        if (data == null) {
            throw new IllegalArgumentException("Custom data cannot be null");
        }
        if (!java.util.Objects.equals(this.customData, data)) {
            this.customData = data;
            markDirty();
        }
    }

    @Override
    public boolean validateData() {
        return testString != null &&
                !testString.isEmpty() &&
                customData != null &&
                customData.getName() != null &&
                !customData.getName().isEmpty() &&
                counter >= 0 &&
                testInt >= 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        return NBTWriter.builder()
                .string(NBT_KEY_STRING, testString)
                .intValue(NBT_KEY_INT, testInt)
                .booleanValue(NBT_KEY_BOOLEAN, testBoolean)
                .doubleValue(NBT_KEY_DOUBLE, testDouble)
                .intValue(NBT_KEY_COUNTER, counter)
                .longValue(NBT_KEY_SYNC_TIME, lastSyncTime)
                .compound(
                        NBT_KEY_CUSTOM_DATA,
                        NBTWriter.builder()
                                .string(NBT_KEY_CUSTOM_NAME, customData.getName())
                                .intValue(NBT_KEY_CUSTOM_VALUE, customData.getValue())
                                .booleanValue(NBT_KEY_CUSTOM_FLAG, customData.isFlag())
                                .build()
                ).build();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        NBTReader.of(nbt)
                .intValue(NBT_KEY_INT, integer -> testInt = integer)
                .string(NBT_KEY_STRING, string -> testString = string)
                .booleanValue(NBT_KEY_BOOLEAN, bool -> testBoolean = bool)
                .intValue(NBT_KEY_COUNTER, integer -> counter = integer)
                .doubleValue(NBT_KEY_DOUBLE, dou -> testDouble = dou)
                .longValue(NBT_KEY_SYNC_TIME, sync -> lastSyncTime = sync)
                .compound(NBT_KEY_CUSTOM_DATA, customDataTag -> {
                    AtomicReference<String> name = new AtomicReference<>("");
                    AtomicInteger value = new AtomicInteger(-1);
                    AtomicBoolean flag = new AtomicBoolean(false);
                    NBTReader.of(customDataTag)
                            .string(NBT_KEY_CUSTOM_NAME, name::set)
                            .intValue(NBT_KEY_CUSTOM_VALUE, value::set)
                            .booleanValue(NBT_KEY_CUSTOM_FLAG, flag::set);
                    this.customData = new TestData(name.get(), value.get(), flag.get());
                });
    }

    @Override
    public int entityId() {
        return self != null ? self.getId() : -1;
    }

    /**
     * 设置关联的实体
     *
     * @param entity 关联的实体
     */
    public void setEntity(Entity entity) {
        this.self = entity;
    }


    /**
     * 获取所有数据的字符串表示（用于调试）
     */
    @Override
    public String toString() {
        return String.format(
                "TestSyncData{id=%d, string='%s', int=%d, boolean=%s, double=%.2f, counter=%d, lastSync=%d, custom=%s}",
                self.getId(), testString, testInt, testBoolean, testDouble, counter, lastSyncTime, customData
        );
    }

    /**
     * 重置为默认值
     */
    public void resetToDefaults() {
        testString = "default_value";
        testInt = 42;
        testBoolean = true;
        testDouble = 3.14159;
        counter = 0;
        lastSyncTime = 0L;
        customData = new TestData("default", 100, false);
        markDirty();
    }

    /**
     * 生成随机测试数据
     */
    public void generateRandomData() {
        testString = "random_" + System.currentTimeMillis();
        testInt = (int) (Math.random() * 1000);
        testBoolean = Math.random() > 0.5;
        testDouble = Math.random() * 100.0;
        counter++;
        lastSyncTime = System.currentTimeMillis();
        customData = new TestData(
                "custom_" + counter,
                (int) (Math.random() * 500),
                Math.random() > 0.5
        );
        markDirty();
    }

    /**
     * 创建一个不依赖实体的副本（用于网络传输）
     *
     * @return 不包含实体引用的副本 test sync data
     */
    public TestSyncData createNetworkCopy() {
        TestSyncData copy = new TestSyncData((Entity) null);
        copy.testString = this.testString;
        copy.testInt = this.testInt;
        copy.testBoolean = this.testBoolean;
        copy.testDouble = this.testDouble;
        copy.counter = this.counter;
        copy.lastSyncTime = this.lastSyncTime;
        copy.customData = new TestData(
                this.customData.getName(),
                this.customData.getValue(),
                this.customData.isFlag()
        );
        return copy;
    }

}