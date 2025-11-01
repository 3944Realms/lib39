package top.r3944realms.lib39.util.storage.nbt;

import net.minecraft.nbt.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The type Nbt writer.
 */
@SuppressWarnings("unused")
public class NBTWriter {
    private final CompoundTag root;

    private NBTWriter() {
        this.root = new CompoundTag();
    }

    private NBTWriter(CompoundTag existingTag) {
        this.root = existingTag;
    }

    /**
     * 创建一个新的NBT构建器
     *
     * @return the nbt writer
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull NBTWriter builder() {
        return new NBTWriter();
    }

    /**
     * 基于现有CompoundTag创建构建器
     *
     * @param existingTag the existing tag
     * @return the nbt writer
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull NBTWriter of(CompoundTag existingTag) {
        return new NBTWriter(existingTag);
    }

    /**
     * Byte value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter byteValue(String key, byte value) {
        root.putByte(key, value);
        return this;
    }

    /**
     * Short value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter shortValue(String key, short value) {
        root.putShort(key, value);
        return this;
    }

    /**
     * Int value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter intValue(String key, int value) {
        root.putInt(key, value);
        return this;
    }

    /**
     * Long value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter longValue(String key, long value) {
        root.putLong(key, value);
        return this;
    }

    /**
     * Float value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter floatValue(String key, float value) {
        root.putFloat(key, value);
        return this;
    }

    /**
     * Double value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter doubleValue(String key, double value) {
        root.putDouble(key, value);
        return this;
    }

    /**
     * Boolean value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter booleanValue(String key, boolean value) {
        root.putBoolean(key, value);
        return this;
    }

    /**
     * String nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter string(String key, String value) {
        if (value != null) {
            root.putString(key, value);
        }
        return this;
    }

    /**
     * String nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
// 包装类型 - null安全的版本
    public NBTWriter string(String key, String value, String defaultValue) {
        if (value != null) {
            root.putString(key, value);
        } else if (defaultValue != null) {
            root.putString(key, defaultValue);
        }
        return this;
    }


    /**
     * Byte value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter byteValue(String key, Byte value) {
        if (value != null) {
            root.putByte(key, value);
        }
        return this;
    }

    /**
     * Byte value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter byteValue(String key, Byte value, byte defaultValue) {
        root.putByte(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Short value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter shortValue(String key, Short value) {
        if (value != null) {
            root.putShort(key, value);
        }
        return this;
    }

    /**
     * Short value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter shortValue(String key, Short value, short defaultValue) {
        root.putShort(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Int value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter intValue(String key, Integer value) {
        if (value != null) {
            root.putInt(key, value);
        }
        return this;
    }

    /**
     * Int value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter intValue(String key, Integer value, int defaultValue) {
        root.putInt(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Long value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter longValue(String key, Long value) {
        if (value != null) {
            root.putLong(key, value);
        }
        return this;
    }

    /**
     * Long value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter longValue(String key, Long value, long defaultValue) {
        root.putLong(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Float value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter floatValue(String key, Float value) {
        if (value != null) {
            root.putFloat(key, value);
        }
        return this;
    }

    /**
     * Float value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter floatValue(String key, Float value, float defaultValue) {
        root.putFloat(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Double value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter doubleValue(String key, Double value) {
        if (value != null) {
            root.putDouble(key, value);
        }
        return this;
    }

    /**
     * Double value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter doubleValue(String key, Double value, double defaultValue) {
        root.putDouble(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Boolean value nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter booleanValue(String key, Boolean value) {
        if (value != null) {
            root.putBoolean(key, value);
        }
        return this;
    }

    /**
     * Boolean value nbt writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the nbt writer
     */
    public NBTWriter booleanValue(String key, Boolean value, boolean defaultValue) {
        root.putBoolean(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * Byte array nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
// 数组类型 - 原始数组
    public NBTWriter byteArray(String key, byte[] value) {
        if (value != null) {
            root.putByteArray(key, value);
        }
        return this;
    }

    /**
     * Int array nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter intArray(String key, int[] value) {
        if (value != null) {
            root.putIntArray(key, value);
        }
        return this;
    }

    /**
     * Long array nbt writer.
     *
     * @param key   the key
     * @param value the value
     * @return the nbt writer
     */
    public NBTWriter longArray(String key, long[] value) {
        if (value != null) {
            root.putLongArray(key, value);
        }
        return this;
    }

    /**
     * Compound nbt writer.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the nbt writer
     */
// 嵌套CompoundTag
    public NBTWriter compound(String key, Consumer<NBTWriter> consumer) {
        if (consumer != null) {
            NBTWriter nestedBuilder = new NBTWriter();
            consumer.accept(nestedBuilder);
            CompoundTag nestedTag = nestedBuilder.build();
            if (!nestedTag.isEmpty()) {
                root.put(key, nestedTag);
            }
        }
        return this;
    }

    /**
     * Compound nbt writer.
     *
     * @param key         the key
     * @param compoundTag the compound tag
     * @return the nbt writer
     */
    public NBTWriter compound(String key, CompoundTag compoundTag) {
        if (compoundTag != null && !compoundTag.isEmpty()) {
            root.put(key, compoundTag);
        }
        return this;
    }

    /**
     * Compound if nbt writer.
     *
     * @param key       the key
     * @param condition the condition
     * @param consumer  the consumer
     * @return the nbt writer
     */
    public NBTWriter compoundIf(String key, boolean condition, Consumer<NBTWriter> consumer) {
        if (condition && consumer != null) {
            return compound(key, consumer);
        }
        return this;
    }

    /**
     * List nbt writer.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the nbt writer
     */
// ListTag支持
    public NBTWriter list(String key, Consumer<ListNBTBuilder> consumer) {
        if (consumer != null) {
            ListNBTBuilder listBuilder = new ListNBTBuilder();
            consumer.accept(listBuilder);
            ListTag listTag = listBuilder.build();
            if (!listTag.isEmpty()) {
                root.put(key, listTag);
            }
        }
        return this;
    }

    /**
     * List nbt writer.
     *
     * @param key     the key
     * @param listTag the list tag
     * @return the nbt writer
     */
    public NBTWriter list(String key, ListTag listTag) {
        if (listTag != null && !listTag.isEmpty()) {
            root.put(key, listTag);
        }
        return this;
    }

    /**
     * List if nbt writer.
     *
     * @param key       the key
     * @param condition the condition
     * @param consumer  the consumer
     * @return the nbt writer
     */
    public NBTWriter listIf(String key, boolean condition, Consumer<ListNBTBuilder> consumer) {
        if (condition && consumer != null) {
            return list(key, consumer);
        }
        return this;
    }

    /**
     * Tag nbt writer.
     *
     * @param key the key
     * @param tag the tag
     * @return the nbt writer
     */
// 直接操作Tag
    public NBTWriter tag(String key, Tag tag) {
        if (tag != null) {
            root.put(key, tag);
        }
        return this;
    }

    /**
     * String if nbt writer.
     *
     * @param key       the key
     * @param value     the value
     * @param condition the condition
     * @return the nbt writer
     */
// 条件添加方法
    public NBTWriter stringIf(String key, String value, boolean condition) {
        if (condition && value != null) {
            root.putString(key, value);
        }
        return this;
    }

    /**
     * Int value if nbt writer.
     *
     * @param key       the key
     * @param value     the value
     * @param condition the condition
     * @return the nbt writer
     */
    public NBTWriter intValueIf(String key, Integer value, boolean condition) {
        if (condition && value != null) {
            root.putInt(key, value);
        }
        return this;
    }

    /**
     * Long value if nbt writer.
     *
     * @param key       the key
     * @param value     the value
     * @param condition the condition
     * @return the nbt writer
     */
    public NBTWriter longValueIf(String key, Long value, boolean condition) {
        if (condition && value != null) {
            root.putLong(key, value);
        }
        return this;
    }

    /**
     * Boolean value if nbt writer.
     *
     * @param key       the key
     * @param value     the value
     * @param condition the condition
     * @return the nbt writer
     */
    public NBTWriter booleanValueIf(String key, Boolean value, boolean condition) {
        if (condition && value != null) {
            root.putBoolean(key, value);
        }
        return this;
    }

    /**
     * Remove nbt writer.
     *
     * @param key the key
     * @return the nbt writer
     */
// 移除标签
    public NBTWriter remove(String key) {
        root.remove(key);
        return this;
    }

    /**
     * Build compound tag.
     *
     * @return the compound tag
     */
// 构建最终的CompoundTag
    public CompoundTag build() {
        return root;
    }


    /**
     * ListTag专用的构建器 - 同样支持null安全
     */
    public static class ListNBTBuilder {
        private final ListTag list;

        private ListNBTBuilder() {
            this.list = new ListTag();
        }

        /**
         * Add string list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
// 原始类型方法
        public ListNBTBuilder addString(String value) {
            list.add(StringTag.valueOf(value));
            return this;
        }

        /**
         * Add byte list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addByte(byte value) {
            list.add(ByteTag.valueOf(value));
            return this;
        }

        /**
         * Add short list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addShort(short value) {
            list.add(ShortTag.valueOf(value));
            return this;
        }

        /**
         * Add int list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addInt(int value) {
            list.add(IntTag.valueOf(value));
            return this;
        }

        /**
         * Add long list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addLong(long value) {
            list.add(LongTag.valueOf(value));
            return this;
        }

        /**
         * Add float list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addFloat(float value) {
            list.add(FloatTag.valueOf(value));
            return this;
        }

        /**
         * Add double list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addDouble(double value) {
            list.add(DoubleTag.valueOf(value));
            return this;
        }

        /**
         * Add boolean list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addBoolean(boolean value) {
            list.add(ByteTag.valueOf(value));
            return this;
        }

        /**
         * Add byte array list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addByteArray(byte[] value) {
            if (value != null) {
                list.add(new ByteArrayTag(value));
            }
            return this;
        }

        /**
         * Add int array list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addIntArray(int[] value) {
            if (value != null) {
                list.add(new IntArrayTag(value));
            }
            return this;
        }

        /**
         * Add long array list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addLongArray(long[] value) {
            if (value != null) {
                list.add(new LongArrayTag(value));
            }
            return this;
        }

        /**
         * Add string list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
// 包装类型方法 - null安全
        public ListNBTBuilder addString(String value, String defaultValue) {
            list.add(StringTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add string if list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addStringIf(String value) {
            if (value != null) {
                list.add(StringTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add byte list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addByte(Byte value) {
            if (value != null) {
                list.add(ByteTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add byte list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addByte(Byte value, byte defaultValue) {
            list.add(ByteTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add short list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addShort(Short value) {
            if (value != null) {
                list.add(ShortTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add short list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addShort(Short value, short defaultValue) {
            list.add(ShortTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add int list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addInt(Integer value) {
            if (value != null) {
                list.add(IntTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add int list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addInt(Integer value, int defaultValue) {
            list.add(IntTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add long list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addLong(Long value) {
            if (value != null) {
                list.add(LongTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add long list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addLong(Long value, long defaultValue) {
            list.add(LongTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add float list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addFloat(Float value) {
            if (value != null) {
                list.add(FloatTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add float list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addFloat(Float value, float defaultValue) {
            list.add(FloatTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add double list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addDouble(Double value) {
            if (value != null) {
                list.add(DoubleTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add double list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addDouble(Double value, double defaultValue) {
            list.add(DoubleTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add boolean list nbt builder.
         *
         * @param value the value
         * @return the list nbt builder
         */
        public ListNBTBuilder addBoolean(Boolean value) {
            if (value != null) {
                list.add(ByteTag.valueOf(value));
            }
            return this;
        }

        /**
         * Add boolean list nbt builder.
         *
         * @param value        the value
         * @param defaultValue the default value
         * @return the list nbt builder
         */
        public ListNBTBuilder addBoolean(Boolean value, boolean defaultValue) {
            list.add(ByteTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        /**
         * Add compound list nbt builder.
         *
         * @param consumer the consumer
         * @return the list nbt builder
         */
        public ListNBTBuilder addCompound(Consumer<NBTWriter> consumer) {
            if (consumer != null) {
                NBTWriter compoundBuilder = new NBTWriter();
                consumer.accept(compoundBuilder);
                CompoundTag compoundTag = compoundBuilder.build();
                if (!compoundTag.isEmpty()) {
                    list.add(compoundTag);
                }
            }
            return this;
        }

        /**
         * Add tag list nbt builder.
         *
         * @param tag the tag
         * @return the list nbt builder
         */
        public ListNBTBuilder addTag(Tag tag) {
            if (tag != null) {
                list.add(tag);
            }
            return this;
        }

        /**
         * Add if list nbt builder.
         *
         * @param condition the condition
         * @param consumer  the consumer
         * @return the list nbt builder
         */
        public ListNBTBuilder addIf(boolean condition, Consumer<ListNBTBuilder> consumer) {
            if (condition && consumer != null) {
                consumer.accept(this);
            }
            return this;
        }

        /**
         * Build list tag.
         *
         * @return the list tag
         */
        public ListTag build() {
            return list;
        }
    }

    /**
     * Create compound tag.
     *
     * @param consumer the consumer
     * @return the compound tag
     */
// 便捷静态方法
    public static CompoundTag create(Consumer<NBTWriter> consumer) {
        NBTWriter builder = new NBTWriter();
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    /**
     * Create list list tag.
     *
     * @param consumer the consumer
     * @return the list tag
     */
    public static ListTag createList(Consumer<ListNBTBuilder> consumer) {
        ListNBTBuilder builder = new ListNBTBuilder();
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    /**
     * 检查构建的NBT是否为空
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * 获取NBT中所有键的集合
     *
     * @return the all keys
     */
    public java.util.Set<String> getAllKeys() {
        return root.tags.keySet();
    }

    /**
     * Write vec 3 compound tag.
     *
     * @param vec the vec
     * @return the compound tag
     */
    @Contract("null -> fail")
    public static @NotNull CompoundTag writeVec3(Vec3 vec) {
        CompoundTag nbt = new CompoundTag();
        if (vec == null) throw new IllegalArgumentException("Vec3 cannot be null");

        nbt.putDouble("X", vec.x);
        nbt.putDouble("Y", vec.y);
        nbt.putDouble("Z", vec.z);
        return nbt;
    }

    /**
     * Write vec 3 safe compound tag.
     *
     * @param vec the vec
     * @return the compound tag
     */
    public static @Nullable CompoundTag writeVec3Safe(Vec3 vec) {
        CompoundTag nbt = new CompoundTag();
        if (vec == null) return null;

        nbt.putDouble("X", vec.x);
        nbt.putDouble("Y", vec.y);
        nbt.putDouble("Z", vec.z);
        return nbt;
    }
}