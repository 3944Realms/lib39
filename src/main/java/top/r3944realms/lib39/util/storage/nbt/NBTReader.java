package top.r3944realms.lib39.util.storage.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The type Nbt reader.
 */
@SuppressWarnings("unused")
public class NBTReader {
    private final CompoundTag nbt;

    private NBTReader(CompoundTag nbt) {
        this.nbt = nbt;
    }

    /**
     * 从CompoundTag创建读取器
     *
     * @param nbt the nbt
     * @return the nbt reader
     */
    @NotNull
    public static NBTReader of(@NotNull CompoundTag nbt) {
        return new NBTReader(nbt);
    }

    /**
     * String nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
// 基本读取方法 - 直接赋值给成员变量
    public NBTReader string(String key, Consumer<String> setter) {
        if (nbt.contains(key) && nbt.getString(key).isPresent()) {
            setter.accept(nbt.getString(key).get());
        }
        return this;
    }

    /**
     * String nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader string(String key, @NotNull Consumer<String> setter, String defaultValue) {
        setter.accept(nbt.contains(key) && nbt.getString(key).isPresent() ? nbt.getString(key).get() : defaultValue);
        return this;
    }

    /**
     * Byte value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader byteValue(String key, Consumer<Byte> setter) {
        if (nbt.contains(key) && nbt.getByte(key).isPresent()) {
            setter.accept(nbt.getByte(key).get());
        }
        return this;
    }

    /**
     * Byte value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader byteValue(String key, @NotNull Consumer<Byte> setter, byte defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getByteOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Short value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader shortValue(String key, Consumer<Short> setter) {
        if (nbt.contains(key) && nbt.getShort(key).isPresent()) {
            setter.accept(nbt.getShort(key).get());
        }
        return this;
    }

    /**
     * Short value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader shortValue(String key, @NotNull Consumer<Short> setter, short defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getShortOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Int value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader intValue(String key, Consumer<Integer> setter) {
        if (nbt.contains(key) && nbt.getInt(key).isPresent()) {
            setter.accept(nbt.getInt(key).get());
        }
        return this;
    }

    /**
     * Int value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader intValue(String key, @NotNull Consumer<Integer> setter, int defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getIntOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Long value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader longValue(String key, Consumer<Long> setter) {
        if (nbt.contains(key) && nbt.getLong(key).isPresent()) {
            setter.accept(nbt.getLong(key).get());
        }
        return this;
    }

    /**
     * Long value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader longValue(String key, @NotNull Consumer<Long> setter, long defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getLongOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Float value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader floatValue(String key, Consumer<Float> setter) {
        if (nbt.contains(key) && nbt.getFloat(key).isPresent()) {
            setter.accept(nbt.getFloat(key).get());
        }
        return this;
    }

    /**
     * Float value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader floatValue(String key, @NotNull Consumer<Float> setter, float defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getFloatOr(key, defaultValue) : defaultValue);
        return this;
    }
    /**
     * Double value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader doubleValue(String key, Consumer<Double> setter) {
        if (nbt.contains(key) && nbt.getDouble(key).isPresent()) {
            setter.accept(nbt.getDouble(key).get());
        }
        return this;
    }

    /**
     * Double value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader doubleValue(String key, @NotNull Consumer<Double> setter, double defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getDoubleOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Boolean value nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader booleanValue(String key, Consumer<Boolean> setter) {
        if (nbt.contains(key) && nbt.getBoolean(key).isPresent()) {
            setter.accept(nbt.getBoolean(key).get());
        }
        return this;
    }

    /**
     * Boolean value nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader booleanValue(String key, @NotNull Consumer<Boolean> setter, boolean defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getBooleanOr(key, defaultValue) : defaultValue);
        return this;
    }

    /**
     * Byte array nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader byteArray(String key, Consumer<byte[]> setter) {
        if (nbt.contains(key) && nbt.getByteArray(key).isPresent()) {
            setter.accept(nbt.getByteArray(key).get());
        }
        return this;
    }

    /**
     * Byte array nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader byteArray(String key, @NotNull Consumer<byte[]> setter, byte[] defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getByteArray(key).orElse(defaultValue) : defaultValue);
        return this;
    }

    /**
     * Int array nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader intArray(String key, Consumer<int[]> setter) {
        if (nbt.contains(key) && nbt.getIntArray(key).isPresent()) {
            setter.accept(nbt.getIntArray(key).get());
        }
        return this;
    }

    /**
     * Int array nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader intArray(String key, @NotNull Consumer<int[]> setter, int[] defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getIntArray(key).orElse(defaultValue) : defaultValue);
        return this;
    }

    /**
     * Long array nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader longArray(String key, Consumer<long[]> setter) {
        if (nbt.contains(key) && nbt.getLongArray(key).isPresent()) {
            setter.accept(nbt.getLongArray(key).get());
        }
        return this;
    }

    /**
     * Long array nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader longArray(String key, @NotNull Consumer<long[]> setter, long[] defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getLongArray(key).orElse(defaultValue) : defaultValue);
        return this;
    }

    /**
     * Compound nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    @SuppressWarnings("UnusedReturnValue")
    public NBTReader compound(String key, Consumer<CompoundTag> setter) {
        if (nbt.contains(key) && nbt.getCompound(key).isPresent()) {
            setter.accept(nbt.getCompound(key).get());
        }
        return this;
    }

    /**
     * Compound nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader compound(String key, @NotNull Consumer<CompoundTag> setter, CompoundTag defaultValue) {
        setter.accept(nbt.contains(key) ? nbt.getCompound(key).orElse(defaultValue) : defaultValue);
        return this;
    }

    /**
     * List nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader list(String key, Consumer<ListTag> setter) {
        if (nbt.contains(key) && nbt.getList(key).isPresent()) {
            ListTag list = nbt.getList(key).get();
            if (!list.isEmpty()) {
                setter.accept(list);
            }
        }
        return this;
    }

    /**
     * List nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader list(String key, @NotNull Consumer<ListTag> setter, ListTag defaultValue) {
        if (nbt.contains(key)) {
            ListTag list = nbt.getListOrEmpty(key);
            setter.accept(list.isEmpty() ? defaultValue : list);
        } else {
            setter.accept(defaultValue);
        }
        return this;
    }

    /**
     * Vec 3 nbt reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the nbt reader
     */
    public NBTReader vec3(String key, Consumer<Vec3> setter) {
        if (nbt.contains(key) && nbt.getCompound(key).isPresent()) {
            CompoundTag vecTag = nbt.getCompound(key).get();
            if (vecTag.contains("X") && vecTag.contains("Y") && vecTag.contains("Z")) {
                setter.accept(new Vec3(
                        vecTag.getDouble("X").orElse(0.0),
                        vecTag.getDouble("Y").orElse(0.0),
                        vecTag.getDouble("Z").orElse(0.0)
                ));
            }
        }
        return this;
    }

    /**
     * Vec 3 nbt reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public NBTReader vec3(String key, Consumer<Vec3> setter, Vec3 defaultValue) {
        if (nbt.contains(key) && nbt.getCompound(key).isPresent()) {
            CompoundTag vecTag = nbt.getCompound(key).get();
            if (vecTag.contains("X") && vecTag.contains("Y") && vecTag.contains("Z")) {
                setter.accept(new Vec3(
                        vecTag.getDouble("X").orElse(0.0),
                        vecTag.getDouble("Y").orElse(0.0),
                        vecTag.getDouble("Z").orElse(0.0)
                ));
                return this;
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    /**
     * Enum value nbt reader.
     *
     * @param <T>       the type parameter
     * @param key       the key
     * @param enumClass the enum class
     * @param setter    the setter
     * @return the nbt reader
     */
    public <T extends Enum<T>> NBTReader enumValue(String key, Class<T> enumClass, Consumer<T> setter) {
        if (nbt.contains(key) && nbt.getString(key).isPresent()) {
            String value = nbt.getString(key).get();
            try {
                setter.accept(Enum.valueOf(enumClass, value.toUpperCase()));
            } catch (IllegalArgumentException ignored) {

            }
        }
        return this;
    }

    /**
     * Enum value nbt reader.
     *
     * @param <T>          the type parameter
     * @param key          the key
     * @param enumClass    the enum class
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the nbt reader
     */
    public <T extends Enum<T>> NBTReader enumValue(String key, Class<T> enumClass, Consumer<T> setter, T defaultValue) {
        if (nbt.contains(key) && nbt.getString(key).isPresent()) {
            String value = nbt.getString(key).get();
            try {
                setter.accept(Enum.valueOf(enumClass, value.toUpperCase()));
                return this;
            } catch (IllegalArgumentException ignored) {
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    /**
     * Nested nbt reader.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the nbt reader
     */
    public NBTReader nested(String key, Consumer<NBTReader> consumer) {
        if (nbt.contains(key) && nbt.getCompound(key).isPresent()) {
            consumer.accept(new NBTReader(nbt.getCompound(key).get()));
        }
        return this;
    }

    /**
     * Nested nbt reader.
     *
     * @param key      the key
     * @param consumer the consumer
     * @param orElse   the or else
     * @return the nbt reader
     */
    public NBTReader nested(String key, Consumer<NBTReader> consumer, Runnable orElse) {
        if (nbt.contains(key) && nbt.getCompound(key).isPresent()) {
            consumer.accept(new NBTReader(nbt.getCompound(key).get()));
        } else {
            orElse.run();
        }
        return this;
    }

    /**
     * If present nbt reader.
     *
     * @param key    the key
     * @param action the action
     * @return the nbt reader
     */
    public NBTReader ifPresent(String key, Runnable action) {
        if (nbt.contains(key)) {
            action.run();
        }
        return this;
    }

    /**
     * If absent nbt reader.
     *
     * @param key    the key
     * @param action the action
     * @return the nbt reader
     */
    public NBTReader ifAbsent(String key, Runnable action) {
        if (!nbt.contains(key)) {
            action.run();
        }
        return this;
    }

    /**
     * Gets raw.
     *
     * @return the raw
     */
    @NotNull
    public CompoundTag getRaw() {
        return nbt;
    }

    /**
     * Read vec 3 vec 3.
     *
     * @param nbt the nbt
     * @return the vec 3
     */
    @NotNull
    public static Vec3 readVec3(@NotNull CompoundTag nbt) {
        if (nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")) {
            return new Vec3(
                    nbt.getDouble("X").orElse(0.0),
                    nbt.getDouble("Y").orElse(0.0),
                    nbt.getDouble("Z").orElse(0.0)
            );
        } else {
            throw new IllegalArgumentException("NBT is missing X, Y, or Z value for Vec3");
        }
    }

    /**
     * Read vec 3 safe vec 3.
     *
     * @param nbt the nbt
     * @return the vec 3
     */
    @Nullable
    public static Vec3 readVec3Safe(@NotNull CompoundTag nbt) {
        if (nbt.contains("X") && nbt.contains("Y") && nbt.contains("Z")) {
            return new Vec3(
                    nbt.getDouble("X").orElse(0.0),
                    nbt.getDouble("Y").orElse(0.0),
                    nbt.getDouble("Z").orElse(0.0)
            );
        }
        return null;
    }
}