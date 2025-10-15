package top.r3944realms.lib39.util.nbt;

import net.minecraft.nbt.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class NBTBuilder {
    private final CompoundTag root;

    private NBTBuilder() {
        this.root = new CompoundTag();
    }

    private NBTBuilder(CompoundTag existingTag) {
        this.root = existingTag;
    }

    /**
     * 创建一个新的NBT构建器
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull NBTBuilder builder() {
        return new NBTBuilder();
    }

    /**
     * 基于现有CompoundTag创建构建器
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull NBTBuilder of(CompoundTag existingTag) {
        return new NBTBuilder(existingTag);
    }

    public NBTBuilder byteValue(String key, byte value) {
        root.putByte(key, value);
        return this;
    }

    public NBTBuilder shortValue(String key, short value) {
        root.putShort(key, value);
        return this;
    }

    public NBTBuilder intValue(String key, int value) {
        root.putInt(key, value);
        return this;
    }

    public NBTBuilder longValue(String key, long value) {
        root.putLong(key, value);
        return this;
    }

    public NBTBuilder floatValue(String key, float value) {
        root.putFloat(key, value);
        return this;
    }

    public NBTBuilder doubleValue(String key, double value) {
        root.putDouble(key, value);
        return this;
    }

    public NBTBuilder booleanValue(String key, boolean value) {
        root.putBoolean(key, value);
        return this;
    }
    public NBTBuilder string(String key, String value) {
        if (value != null) {
            root.putString(key, value);
        }
        return this;
    }
    // 包装类型 - null安全的版本
    public NBTBuilder string(String key, String value, String defaultValue) {
        if (value != null) {
            root.putString(key, value);
        } else if (defaultValue != null) {
            root.putString(key, defaultValue);
        }
        return this;
    }


    public NBTBuilder byteValue(String key, Byte value) {
        if (value != null) {
            root.putByte(key, value);
        }
        return this;
    }

    public NBTBuilder byteValue(String key, Byte value, byte defaultValue) {
        root.putByte(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder shortValue(String key, Short value) {
        if (value != null) {
            root.putShort(key, value);
        }
        return this;
    }

    public NBTBuilder shortValue(String key, Short value, short defaultValue) {
        root.putShort(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder intValue(String key, Integer value) {
        if (value != null) {
            root.putInt(key, value);
        }
        return this;
    }

    public NBTBuilder intValue(String key, Integer value, int defaultValue) {
        root.putInt(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder longValue(String key, Long value) {
        if (value != null) {
            root.putLong(key, value);
        }
        return this;
    }

    public NBTBuilder longValue(String key, Long value, long defaultValue) {
        root.putLong(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder floatValue(String key, Float value) {
        if (value != null) {
            root.putFloat(key, value);
        }
        return this;
    }

    public NBTBuilder floatValue(String key, Float value, float defaultValue) {
        root.putFloat(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder doubleValue(String key, Double value) {
        if (value != null) {
            root.putDouble(key, value);
        }
        return this;
    }

    public NBTBuilder doubleValue(String key, Double value, double defaultValue) {
        root.putDouble(key, value != null ? value : defaultValue);
        return this;
    }

    public NBTBuilder booleanValue(String key, Boolean value) {
        if (value != null) {
            root.putBoolean(key, value);
        }
        return this;
    }

    public NBTBuilder booleanValue(String key, Boolean value, boolean defaultValue) {
        root.putBoolean(key, value != null ? value : defaultValue);
        return this;
    }

    // 数组类型 - 原始数组
    public NBTBuilder byteArray(String key, byte[] value) {
        if (value != null) {
            root.putByteArray(key, value);
        }
        return this;
    }

    public NBTBuilder intArray(String key, int[] value) {
        if (value != null) {
            root.putIntArray(key, value);
        }
        return this;
    }

    public NBTBuilder longArray(String key, long[] value) {
        if (value != null) {
            root.putLongArray(key, value);
        }
        return this;
    }

    // UUID支持
    public NBTBuilder uuid(String key, UUID value) {
        if (value != null) {
            root.putUUID(key, value);
        }
        return this;
    }

    public NBTBuilder uuid(String key, UUID value, UUID defaultValue) {
        if (value != null) {
            root.putUUID(key, value);
        } else if (defaultValue != null) {
            root.putUUID(key, defaultValue);
        }
        return this;
    }

    // 嵌套CompoundTag
    public NBTBuilder compound(String key, Consumer<NBTBuilder> consumer) {
        if (consumer != null) {
            NBTBuilder nestedBuilder = new NBTBuilder();
            consumer.accept(nestedBuilder);
            CompoundTag nestedTag = nestedBuilder.build();
            if (!nestedTag.isEmpty()) {
                root.put(key, nestedTag);
            }
        }
        return this;
    }

    public NBTBuilder compound(String key, CompoundTag compoundTag) {
        if (compoundTag != null && !compoundTag.isEmpty()) {
            root.put(key, compoundTag);
        }
        return this;
    }

    public NBTBuilder compoundIf(String key, boolean condition, Consumer<NBTBuilder> consumer) {
        if (condition && consumer != null) {
            return compound(key, consumer);
        }
        return this;
    }

    // ListTag支持
    public NBTBuilder list(String key, Consumer<ListNBTBuilder> consumer) {
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

    public NBTBuilder list(String key, ListTag listTag) {
        if (listTag != null && !listTag.isEmpty()) {
            root.put(key, listTag);
        }
        return this;
    }

    public NBTBuilder listIf(String key, boolean condition, Consumer<ListNBTBuilder> consumer) {
        if (condition && consumer != null) {
            return list(key, consumer);
        }
        return this;
    }

    // 直接操作Tag
    public NBTBuilder tag(String key, Tag tag) {
        if (tag != null) {
            root.put(key, tag);
        }
        return this;
    }

    // 条件添加方法
    public NBTBuilder stringIf(String key, String value, boolean condition) {
        if (condition && value != null) {
            root.putString(key, value);
        }
        return this;
    }

    public NBTBuilder intValueIf(String key, Integer value, boolean condition) {
        if (condition && value != null) {
            root.putInt(key, value);
        }
        return this;
    }

    public NBTBuilder longValueIf(String key, Long value, boolean condition) {
        if (condition && value != null) {
            root.putLong(key, value);
        }
        return this;
    }

    public NBTBuilder booleanValueIf(String key, Boolean value, boolean condition) {
        if (condition && value != null) {
            root.putBoolean(key, value);
        }
        return this;
    }

    // 移除标签
    public NBTBuilder remove(String key) {
        root.remove(key);
        return this;
    }

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

        // 原始类型方法
        public ListNBTBuilder addString(String value) {
            list.add(StringTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addByte(byte value) {
            list.add(ByteTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addShort(short value) {
            list.add(ShortTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addInt(int value) {
            list.add(IntTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addLong(long value) {
            list.add(LongTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addFloat(float value) {
            list.add(FloatTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addDouble(double value) {
            list.add(DoubleTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addBoolean(boolean value) {
            list.add(ByteTag.valueOf(value));
            return this;
        }

        public ListNBTBuilder addByteArray(byte[] value) {
            if (value != null) {
                list.add(new ByteArrayTag(value));
            }
            return this;
        }

        public ListNBTBuilder addIntArray(int[] value) {
            if (value != null) {
                list.add(new IntArrayTag(value));
            }
            return this;
        }

        public ListNBTBuilder addLongArray(long[] value) {
            if (value != null) {
                list.add(new LongArrayTag(value));
            }
            return this;
        }

        // 包装类型方法 - null安全
        public ListNBTBuilder addString(String value, String defaultValue) {
            list.add(StringTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addStringIf(String value) {
            if (value != null) {
                list.add(StringTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addByte(Byte value) {
            if (value != null) {
                list.add(ByteTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addByte(Byte value, byte defaultValue) {
            list.add(ByteTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addShort(Short value) {
            if (value != null) {
                list.add(ShortTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addShort(Short value, short defaultValue) {
            list.add(ShortTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addInt(Integer value) {
            if (value != null) {
                list.add(IntTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addInt(Integer value, int defaultValue) {
            list.add(IntTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addLong(Long value) {
            if (value != null) {
                list.add(LongTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addLong(Long value, long defaultValue) {
            list.add(LongTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addFloat(Float value) {
            if (value != null) {
                list.add(FloatTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addFloat(Float value, float defaultValue) {
            list.add(FloatTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addDouble(Double value) {
            if (value != null) {
                list.add(DoubleTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addDouble(Double value, double defaultValue) {
            list.add(DoubleTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addBoolean(Boolean value) {
            if (value != null) {
                list.add(ByteTag.valueOf(value));
            }
            return this;
        }

        public ListNBTBuilder addBoolean(Boolean value, boolean defaultValue) {
            list.add(ByteTag.valueOf(value != null ? value : defaultValue));
            return this;
        }

        public ListNBTBuilder addCompound(Consumer<NBTBuilder> consumer) {
            if (consumer != null) {
                NBTBuilder compoundBuilder = new NBTBuilder();
                consumer.accept(compoundBuilder);
                CompoundTag compoundTag = compoundBuilder.build();
                if (!compoundTag.isEmpty()) {
                    list.add(compoundTag);
                }
            }
            return this;
        }

        public ListNBTBuilder addTag(Tag tag) {
            if (tag != null) {
                list.add(tag);
            }
            return this;
        }

        public ListNBTBuilder addIf(boolean condition, Consumer<ListNBTBuilder> consumer) {
            if (condition && consumer != null) {
                consumer.accept(this);
            }
            return this;
        }

        public ListTag build() {
            return list;
        }
    }

    // 便捷静态方法
    public static CompoundTag create(Consumer<NBTBuilder> consumer) {
        NBTBuilder builder = new NBTBuilder();
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    public static ListTag createList(Consumer<ListNBTBuilder> consumer) {
        ListNBTBuilder builder = new ListNBTBuilder();
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    /**
     * 检查构建的NBT是否为空
     */
    public boolean isEmpty() {
        return root.isEmpty();
    }

    /**
     * 获取NBT中所有键的集合
     */
    public java.util.Set<String> getAllKeys() {
        return root.getAllKeys();
    }
}