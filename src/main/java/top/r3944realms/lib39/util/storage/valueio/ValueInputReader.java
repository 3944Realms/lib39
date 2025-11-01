package top.r3944realms.lib39.util.storage.valueio;


import com.mojang.serialization.Codec;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * ValueInput Helper类
 * 提供链式操作和类型安全的ValueInput数据读取
 */
@SuppressWarnings("unused")
public class ValueInputReader {
    private final net.minecraft.world.level.storage.ValueInput valueInput;

    private ValueInputReader(net.minecraft.world.level.storage.ValueInput valueInput) {
        this.valueInput = valueInput;
    }

    /**
     * 从ValueInput创建读取器
     *
     * @param valueInput the value input
     * @return the value input reader
     */
    @NotNull
    public static ValueInputReader of(@NotNull net.minecraft.world.level.storage.ValueInput valueInput) {
        return new ValueInputReader(valueInput);
    }

    /**
     * String value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    // 基本读取方法 - 直接赋值给成员变量
    public ValueInputReader string(String key, Consumer<String> setter) {
        valueInput.getString(key).ifPresent(setter);
        return this;
    }

    /**
     * String value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader string(String key, @NotNull Consumer<String> setter, String defaultValue) {
        setter.accept(valueInput.getStringOr(key, defaultValue));
        return this;
    }

    /**
     * Boolean value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader booleanValue(String key, Consumer<Boolean> setter) {
        valueInput.read(key, Codec.BOOL).ifPresent(setter);
        return this;
    }

    /**
     * Boolean value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader booleanValue(String key, @NotNull Consumer<Boolean> setter, boolean defaultValue) {
        setter.accept(valueInput.getBooleanOr(key, defaultValue));
        return this;
    }

    /**
     * Byte value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader byteValue(String key, Consumer<Byte> setter) {
        valueInput.read(key, Codec.BYTE).ifPresent(setter);
        return this;
    }

    /**
     * Byte value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader byteValue(String key, @NotNull Consumer<Byte> setter, byte defaultValue) {
        setter.accept(valueInput.getByteOr(key, defaultValue));
        return this;
    }

    /**
     * Short value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader shortValue(String key, Consumer<Short> setter) {
        valueInput.read(key, Codec.SHORT).ifPresent(setter);
        return this;
    }

    /**
     * Short value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader shortValue(String key, @NotNull Consumer<Short> setter, short defaultValue) {
        setter.accept((short) valueInput.getShortOr(key, defaultValue));
        return this;
    }

    /**
     * Int value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader intValue(String key, Consumer<Integer> setter) {
        valueInput.getInt(key).ifPresent(setter);
        return this;
    }

    /**
     * Int value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader intValue(String key, @NotNull Consumer<Integer> setter, int defaultValue) {
        setter.accept(valueInput.getIntOr(key, defaultValue));
        return this;
    }

    /**
     * Long value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader longValue(String key, Consumer<Long> setter) {
        valueInput.getLong(key).ifPresent(setter);
        return this;
    }

    /**
     * Long value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader longValue(String key, @NotNull Consumer<Long> setter, long defaultValue) {
        setter.accept(valueInput.getLongOr(key, defaultValue));
        return this;
    }

    /**
     * Float value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader floatValue(String key, Consumer<Float> setter) {
        valueInput.read(key, Codec.FLOAT).ifPresent(setter);
        return this;
    }

    /**
     * Float value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader floatValue(String key, @NotNull Consumer<Float> setter, float defaultValue) {
        setter.accept(valueInput.getFloatOr(key, defaultValue));
        return this;
    }

    /**
     * Double value value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader doubleValue(String key, Consumer<Double> setter) {
        valueInput.read(key, Codec.DOUBLE).ifPresent(setter);
        return this;
    }

    /**
     * Double value value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader doubleValue(String key, @NotNull Consumer<Double> setter, double defaultValue) {
        setter.accept(valueInput.getDoubleOr(key, defaultValue));
        return this;
    }

    /**
     * Int array value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader intArray(String key, Consumer<int[]> setter) {
        valueInput.getIntArray(key).ifPresent(setter);
        return this;
    }

    /**
     * Int array value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader intArray(String key, @NotNull Consumer<int[]> setter, int[] defaultValue) {
        setter.accept(valueInput.getIntArray(key).orElse(defaultValue));
        return this;
    }

    /**
     * Codec value value input reader.
     *
     * @param <T>    the type parameter
     * @param key    the key
     * @param codec  the codec
     * @param setter the setter
     * @return the value input reader
     */
    public <T> ValueInputReader codecValue(String key, Codec<T> codec, Consumer<T> setter) {
        valueInput.read(key, codec).ifPresent(setter);
        return this;
    }

    /**
     * Codec value value input reader.
     *
     * @param <T>          the type parameter
     * @param key          the key
     * @param codec        the codec
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public <T> ValueInputReader codecValue(String key, Codec<T> codec, @NotNull Consumer<T> setter, T defaultValue) {
        setter.accept(valueInput.read(key, codec).orElse(defaultValue));
        return this;
    }

    /**
     * Vec 3 value input reader.
     *
     * @param key    the key
     * @param setter the setter
     * @return the value input reader
     */
    public ValueInputReader vec3(String key, Consumer<Vec3> setter) {
        valueInput.child(key).ifPresent(child -> {
            try {
                Vec3 vec = readVec3(child);
                setter.accept(vec);
            } catch (IllegalArgumentException ignored) {
                // 忽略解析错误
            }
        });
        return this;
    }

    /**
     * Vec 3 value input reader.
     *
     * @param key          the key
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public ValueInputReader vec3(String key, Consumer<Vec3> setter, Vec3 defaultValue) {
        Optional<net.minecraft.world.level.storage.ValueInput> child = valueInput.child(key);
        if (child.isPresent()) {
            try {
                Vec3 vec = readVec3(child.get());
                setter.accept(vec);
                return this;
            } catch (IllegalArgumentException ignored) {
                // 忽略解析错误，使用默认值
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    /**
     * Enum value value input reader.
     *
     * @param <T>       the type parameter
     * @param key       the key
     * @param enumClass the enum class
     * @param setter    the setter
     * @return the value input reader
     */
    public <T extends Enum<T>> ValueInputReader enumValue(String key, Class<T> enumClass, Consumer<T> setter) {
        valueInput.getString(key).ifPresent(value -> {
            try {
                setter.accept(Enum.valueOf(enumClass, value.toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                // 忽略枚举解析错误
            }
        });
        return this;
    }

    /**
     * Enum value value input reader.
     *
     * @param <T>          the type parameter
     * @param key          the key
     * @param enumClass    the enum class
     * @param setter       the setter
     * @param defaultValue the default value
     * @return the value input reader
     */
    public <T extends Enum<T>> ValueInputReader enumValue(String key, Class<T> enumClass, Consumer<T> setter, T defaultValue) {
        Optional<String> value = valueInput.getString(key);
        if (value.isPresent()) {
            try {
                setter.accept(Enum.valueOf(enumClass, value.get().toUpperCase()));
                return this;
            } catch (IllegalArgumentException ignored) {
                // 忽略枚举解析错误
            }
        }
        setter.accept(defaultValue);
        return this;
    }

    /**
     * Nested value input reader.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the value input reader
     */
    public ValueInputReader nested(String key, Consumer<ValueInputReader> consumer) {
        valueInput.child(key).ifPresent(child -> consumer.accept(new ValueInputReader(child)));
        return this;
    }

    /**
     * Nested value input reader.
     *
     * @param key      the key
     * @param consumer the consumer
     * @param orElse   the or else
     * @return the value input reader
     */
    public ValueInputReader nested(String key, Consumer<ValueInputReader> consumer, Runnable orElse) {
        Optional<net.minecraft.world.level.storage.ValueInput> child = valueInput.child(key);
        if (child.isPresent()) {
            consumer.accept(new ValueInputReader(child.get()));
        } else {
            orElse.run();
        }
        return this;
    }

    /**
     * List value input reader.
     *
     * @param <T>      the type parameter
     * @param key      the key
     * @param codec    the codec
     * @param consumer the consumer
     * @return the value input reader
     */
    public <T> ValueInputReader list(String key, Codec<T> codec, Consumer<java.util.stream.Stream<T>> consumer) {
        valueInput.list(key, codec).ifPresent(list -> {
            if (!list.isEmpty()) {
                consumer.accept(list.stream());
            }
        });
        return this;
    }

    /**
     * List value input reader.
     *
     * @param <T>          the type parameter
     * @param key          the key
     * @param codec        the codec
     * @param consumer     the consumer
     * @param defaultValue the default value
     * @return the value input reader
     */
    public <T> ValueInputReader list(String key, Codec<T> codec, @NotNull Consumer<java.util.stream.Stream<T>> consumer, java.util.stream.Stream<T> defaultValue) {
        Optional<net.minecraft.world.level.storage.ValueInput.TypedInputList<T>> list = valueInput.list(key, codec);
        if (list.isPresent() && !list.get().isEmpty()) {
            consumer.accept(list.get().stream());
        } else {
            consumer.accept(defaultValue);
        }
        return this;
    }

    /**
     * Children list value input reader.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the value input reader
     */
    public ValueInputReader childrenList(String key, Consumer<java.util.stream.Stream<ValueInputReader>> consumer) {
        valueInput.childrenList(key).ifPresent(list -> {
            if (!list.isEmpty()) {
                consumer.accept(list.stream().map(ValueInputReader::new));
            }
        });
        return this;
    }

    /**
     * If present value input reader.
     *
     * @param key    the key
     * @param action the action
     * @return the value input reader
     */
    public ValueInputReader ifPresent(String key, Runnable action) {
        if (valueInput.child(key).isPresent()) {
            action.run();
        }
        return this;
    }

    /**
     * If absent value input reader.
     *
     * @param key    the key
     * @param action the action
     * @return the value input reader
     */
    public ValueInputReader ifAbsent(String key, Runnable action) {
        if (valueInput.child(key).isEmpty()) {
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
    public net.minecraft.world.level.storage.ValueInput getRaw() {
        return valueInput;
    }

    /**
     * Read vec 3 vec 3.
     *
     * @param valueInput the value input
     * @return the vec 3
     */
    @NotNull
    public static Vec3 readVec3(@NotNull net.minecraft.world.level.storage.ValueInput valueInput) {
        double x = valueInput.getDoubleOr("X", 0.0);
        double y = valueInput.getDoubleOr("Y", 0.0);
        double z = valueInput.getDoubleOr("Z", 0.0);
        return new Vec3(x, y, z);
    }

    /**
     * Read vec 3 safe vec 3.
     *
     * @param valueInput the value input
     * @return the vec 3
     */
    @Nullable
    public static Vec3 readVec3Safe(@NotNull net.minecraft.world.level.storage.ValueInput valueInput) {
        try {
            return readVec3(valueInput);
        } catch (Exception e) {
            return null;
        }
    }
}
