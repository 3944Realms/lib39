package top.r3944realms.lib39.example.content.capability;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import top.r3944realms.lib39.core.sync.IEntity;

/**
 * The type Abstracted test sync data.
 */
@SuppressWarnings("unused")
public abstract class AbstractedTestSyncData implements ValueIOSerializable, IEntity {

    /**
     * Instantiates a new Nbt sync data.
     *
     * @param id the id
     */
    protected AbstractedTestSyncData(ResourceLocation id) {

    }

    /**
     * Gets test string.
     *
     * @return the test string
     */
    abstract String getTestString();

    /**
     * Sets test string.
     *
     * @param value the value
     */
    abstract void setTestString(String value);

    /**
     * Gets test int.
     *
     * @return the test int
     */
    abstract int getTestInt();

    /**
     * Sets test int.
     *
     * @param value the value
     */
    abstract void setTestInt(int value);

    /**
     * Is test boolean boolean.
     *
     * @return the boolean
     */
    abstract boolean isTestBoolean();

    /**
     * Sets test boolean.
     *
     * @param value the value
     */
    abstract void setTestBoolean(boolean value);

    /**
     * Gets test double.
     *
     * @return the test double
     */
    abstract double getTestDouble();

    /**
     * Sets test double.
     *
     * @param value the value
     */
    abstract void setTestDouble(double value);

    /**
     * Gets counter.
     *
     * @return the counter
     */
// 计数器，用于测试数据变化
    abstract int getCounter();

    /**
     * Increment counter.
     */
    abstract void incrementCounter();

    /**
     * Gets last sync time.
     *
     * @return the last sync time
     */
// 时间戳，用于测试同步时机
    abstract long getLastSyncTime();

    /**
     * Update sync time.
     */
    abstract void updateSyncTime();

    /**
     * Gets custom data.
     *
     * @return the custom data
     */
// 自定义对象测试
    abstract TestData getCustomData();

    /**
     * Sets custom data.
     *
     * @param data the data
     */
    abstract void setCustomData(TestData data);

    /**
     * Validate data boolean.
     *
     * @return the boolean
     */
// 验证数据完整性
    abstract boolean validateData();

    /**
     * 测试数据对象
     */
    public static class TestData {
        public static StreamCodec<FriendlyByteBuf, TestData> CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, TestData::getName,
                ByteBufCodecs.INT, TestData::getValue,
                ByteBufCodecs.BOOL, TestData::isFlag,
                TestData::new
        );
        private String name;
        private int value;
        private boolean flag;


        /**
         * Instantiates a new Test data.
         */
        public TestData() {}

        /**
         * Instantiates a new Test data.
         *
         * @param name  the name
         * @param value the value
         * @param flag  the flag
         */
        public TestData(String name, int value, boolean flag) {
            this.name = name;
            this.value = value;
            this.flag = flag;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
// getters and setters
        public String getName() { return name; }

        /**
         * Sets name.
         *
         * @param name the name
         */
        public void setName(String name) { this.name = name; }

        /**
         * Gets value.
         *
         * @return the value
         */
        public int getValue() { return value; }

        /**
         * Sets value.
         *
         * @param value the value
         */
        public void setValue(int value) { this.value = value; }

        /**
         * Is flag boolean.
         *
         * @return the boolean
         */
        public boolean isFlag() { return flag; }

        /**
         * Sets flag.
         *
         * @param flag the flag
         */
        public void setFlag(boolean flag) { this.flag = flag; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TestData other)) return false;
            return value == other.value && flag == other.flag &&
                    java.util.Objects.equals(name, other.name);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name, value, flag);
        }
        }
}
