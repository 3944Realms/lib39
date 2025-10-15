package top.r3944realms.lib39.util.nbt;

import net.minecraft.nbt.*;

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
    public static NBTBuilder builder() {
        return new NBTBuilder();
    }

    /**
     * 基于现有CompoundTag创建构建器
     */
    public static NBTBuilder of(CompoundTag existingTag) {
        return new NBTBuilder(existingTag);
    }

    // 基本数据类型
    public NBTBuilder string(String key, String value) {
        root.putString(key, value);
        return this;
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

    // 数组类型
    public NBTBuilder byteArray(String key, byte[] value) {
        root.putByteArray(key, value);
        return this;
    }

    public NBTBuilder intArray(String key, int[] value) {
        root.putIntArray(key, value);
        return this;
    }

    public NBTBuilder longArray(String key, long[] value) {
        root.putLongArray(key, value);
        return this;
    }

    // UUID支持
    public NBTBuilder uuid(String key, UUID value) {
        root.putUUID(key, value);
        return this;
    }

    // 嵌套CompoundTag
    public NBTBuilder compound(String key, Consumer<NBTBuilder> consumer) {
        NBTBuilder nestedBuilder = new NBTBuilder();
        consumer.accept(nestedBuilder);
        root.put(key, nestedBuilder.build());
        return this;
    }

    public NBTBuilder compound(String key, CompoundTag compoundTag) {
        root.put(key, compoundTag);
        return this;
    }

    // ListTag支持
    public NBTBuilder list(String key, Consumer<ListNBTBuilder> consumer) {
        ListNBTBuilder listBuilder = new ListNBTBuilder();
        consumer.accept(listBuilder);
        root.put(key, listBuilder.build());
        return this;
    }

    public NBTBuilder list(String key, ListTag listTag) {
        root.put(key, listTag);
        return this;
    }

    // 直接操作Tag
    public NBTBuilder tag(String key, Tag tag) {
        if (tag != null) {
            root.put(key, tag);
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
     * ListTag专用的构建器
     */
    public static class ListNBTBuilder {
        private final ListTag list;

        private ListNBTBuilder() {
            this.list = new ListTag();
        }

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
            list.add(new ByteArrayTag(value));
            return this;
        }

        public ListNBTBuilder addIntArray(int[] value) {
            list.add(new IntArrayTag(value));
            return this;
        }

        public ListNBTBuilder addLongArray(long[] value) {
            list.add(new LongArrayTag(value));
            return this;
        }

        public ListNBTBuilder addCompound(Consumer<NBTBuilder> consumer) {
            NBTBuilder compoundBuilder = new NBTBuilder();
            consumer.accept(compoundBuilder);
            list.add(compoundBuilder.build());
            return this;
        }

        public ListNBTBuilder addTag(Tag tag) {
            if (tag != null) {
                list.add(tag);
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
        consumer.accept(builder);
        return builder.build();
    }

    public static ListTag createList(Consumer<ListNBTBuilder> consumer) {
        ListNBTBuilder builder = new ListNBTBuilder();
        consumer.accept(builder);
        return builder.build();
    }
}