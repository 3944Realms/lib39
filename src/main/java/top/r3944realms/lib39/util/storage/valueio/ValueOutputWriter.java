package top.r3944realms.lib39.util.storage.valueio;

import com.mojang.serialization.Codec;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ValueOutput Helper类
 * 提供链式操作和类型安全的ValueOutput数据写入
 */
@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType", "UnusedReturnValue"})
public class ValueOutputWriter {
    private final net.minecraft.world.level.storage.ValueOutput valueOutput;
    private ValueOutputWriter() {
        this.valueOutput = TagValueOutput.createWithoutContext(ProblemReporter.DISCARDING);
    }
    private ValueOutputWriter(net.minecraft.world.level.storage.ValueOutput valueOutput) {
        this.valueOutput = valueOutput;
    }
    /**
     * 创建一个新的NBT构建器
     *
     * @return the nbt writer
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull ValueOutputWriter builder() {
        return new ValueOutputWriter();
    }

    /**
     * 从ValueOutput创建写入器
     *
     * @param valueOutput the value output
     * @return the value output writer
     */
    @NotNull
    public static ValueOutputWriter of(@NotNull net.minecraft.world.level.storage.ValueOutput valueOutput) {
        return new ValueOutputWriter(valueOutput);
    }

    /**
     * String value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    // 基本写入方法
    public ValueOutputWriter string(String key, String value) {
        if (value != null) {
            valueOutput.putString(key, value);
        }
        return this;
    }

    /**
     * String value output writer.
     *
     * @param key          the key
     * @param value        the value
     * @param defaultValue the default value
     * @return the value output writer
     */
    public ValueOutputWriter string(String key, @Nullable String value, String defaultValue) {
        valueOutput.putString(key, value != null ? value : defaultValue);
        return this;
    }

    /**
     * String value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter string(String key, Optional<String> value) {
        value.ifPresent(v -> valueOutput.putString(key, v));
        return this;
    }

    /**
     * Boolean value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter booleanValue(String key, boolean value) {
        valueOutput.putBoolean(key, value);
        return this;
    }

    /**
     * Boolean value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter booleanValue(String key, Optional<Boolean> value) {
        value.ifPresent(v -> valueOutput.putBoolean(key, v));
        return this;
    }

    /**
     * Byte value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter byteValue(String key, byte value) {
        valueOutput.putByte(key, value);
        return this;
    }

    /**
     * Byte value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter byteValue(String key, Optional<Byte> value) {
        value.ifPresent(v -> valueOutput.putByte(key, v));
        return this;
    }

    /**
     * Short value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter shortValue(String key, short value) {
        valueOutput.putShort(key, value);
        return this;
    }

    /**
     * Short value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter shortValue(String key, Optional<Short> value) {
        value.ifPresent(v -> valueOutput.putShort(key, v));
        return this;
    }

    /**
     * Int value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter intValue(String key, int value) {
        valueOutput.putInt(key, value);
        return this;
    }

    /**
     * Int value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter intValue(String key, Optional<Integer> value) {
        value.ifPresent(v -> valueOutput.putInt(key, v));
        return this;
    }

    /**
     * Long value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter longValue(String key, long value) {
        valueOutput.putLong(key, value);
        return this;
    }

    /**
     * Long value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter longValue(String key, Optional<Long> value) {
        value.ifPresent(v -> valueOutput.putLong(key, v));
        return this;
    }

    /**
     * Float value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter floatValue(String key, float value) {
        valueOutput.putFloat(key, value);
        return this;
    }

    /**
     * Float value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter floatValue(String key, Optional<Float> value) {
        value.ifPresent(v -> valueOutput.putFloat(key, v));
        return this;
    }

    /**
     * Double value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter doubleValue(String key, double value) {
        valueOutput.putDouble(key, value);
        return this;
    }

    /**
     * Double value value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter doubleValue(String key, Optional<Double> value) {
        value.ifPresent(v -> valueOutput.putDouble(key, v));
        return this;
    }

    /**
     * Int array value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter intArray(String key, int[] value) {
        if (value != null && value.length > 0) {
            valueOutput.putIntArray(key, value);
        }
        return this;
    }

    /**
     * Int array value output writer.
     *
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public ValueOutputWriter intArray(String key, Optional<int[]> value) {
        value.ifPresent(v -> {
            if (v.length > 0) {
                valueOutput.putIntArray(key, v);
            }
        });
        return this;
    }

    /**
     * Codec value value output writer.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param codec the codec
     * @param value the value
     * @return the value output writer
     */
    public <T> ValueOutputWriter codecValue(String key, Codec<T> codec, T value) {
        if (value != null) {
            valueOutput.store(key, codec, value);
        }
        return this;
    }

    /**
     * Codec value value output writer.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param codec the codec
     * @param value the value
     * @return the value output writer
     */
    public <T> ValueOutputWriter codecValue(String key, Codec<T> codec, Optional<T> value) {
        value.ifPresent(v -> valueOutput.store(key, codec, v));
        return this;
    }

    /**
     * Codec nullable value value output writer.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param codec the codec
     * @param value the value
     * @return the value output writer
     */
    public <T> ValueOutputWriter codecNullable(String key, Codec<T> codec, @Nullable T value) {
        valueOutput.storeNullable(key, codec, value);
        return this;
    }

    /**
     * Vec 3 value output writer.
     *
     * @param key the key
     * @param vec the vec
     * @return the value output writer
     */
    public ValueOutputWriter vec3(String key, Vec3 vec) {
        if (vec != null) {
            ValueOutput child = valueOutput.child(key);
            writeVec3(child, vec);
        }
        return this;
    }

    /**
     * Vec 3 value output writer.
     *
     * @param key the key
     * @param vec the vec
     * @return the value output writer
     */
    public ValueOutputWriter vec3(String key, Optional<Vec3> vec) {
        vec.ifPresent(v -> vec3(key, v));
        return this;
    }

    /**
     * Enum value value output writer.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public <T extends Enum<T>> ValueOutputWriter enumValue(String key, T value) {
        if (value != null) {
            valueOutput.putString(key, value.name().toLowerCase());
        }
        return this;
    }

    /**
     * Enum value value output writer.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
     * @return the value output writer
     */
    public <T extends Enum<T>> ValueOutputWriter enumValue(String key, Optional<T> value) {
        value.ifPresent(v -> valueOutput.putString(key, v.name().toLowerCase()));
        return this;
    }

    /**
     * Nested value output writer.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the value output writer
     */
    public ValueOutputWriter nested(String key, Consumer<ValueOutputWriter> consumer) {
        ValueOutput child = valueOutput.child(key);
        consumer.accept(new ValueOutputWriter(child));
        return this;
    }

    /**
     * Nested if present value output writer.
     *
     * @param key      the key
     * @param supplier the supplier
     * @param consumer the consumer
     * @return the value output writer
     */
    public ValueOutputWriter nestedIfPresent(String key, Supplier<Boolean> supplier, Consumer<ValueOutputWriter> consumer) {
        if (supplier.get()) {
            ValueOutput child = valueOutput.child(key);
            consumer.accept(new ValueOutputWriter(child));
        }
        return this;
    }

    /**
     * List value output writer.
     *
     * @param <T>        the type parameter
     * @param key        the key
     * @param elementCodec the element codec
     * @param elements   the elements
     * @param consumer   the consumer
     * @return the value output writer
     */
    public <T> ValueOutputWriter list(String key, Codec<T> elementCodec, Iterable<T> elements, Consumer<ValueOutputWriter.TypedListWriter<T>> consumer) {
        ValueOutput.TypedOutputList<T> list = valueOutput.list(key, elementCodec);
        if (!list.isEmpty()) {
            TypedListWriter<T> writer = new TypedListWriter<>(list);
            consumer.accept(writer);
        }
        return this;
    }

    /**
     * List value output writer.
     *
     * @param <T>        the type parameter
     * @param key        the key
     * @param elementCodec the element codec
     * @param elements   the elements
     * @return the value output writer
     */
    public <T> ValueOutputWriter list(String key, Codec<T> elementCodec, Iterable<T> elements) {
        ValueOutput.TypedOutputList<T> list = valueOutput.list(key, elementCodec);
        for (T element : elements) {
            if (element != null) {
                list.add(element);
            }
        }
        return this;
    }

    /**
     * Children list value output writer.
     *
     * @param key      the key
     * @param consumer the consumer
     * @return the value output writer
     */
    public ValueOutputWriter childrenList(String key, Consumer<ValueOutputWriter.ChildrenListWriter> consumer) {
        ValueOutput.ValueOutputList list = valueOutput.childrenList(key);
        if (!list.isEmpty()) {
            ChildrenListWriter writer = new ChildrenListWriter(list);
            consumer.accept(writer);
        }
        return this;
    }

    /**
     * If present value output writer.
     *
     * @param condition the condition
     * @param action    the action
     * @return the value output writer
     */
    public ValueOutputWriter ifPresent(boolean condition, Consumer<ValueOutputWriter> action) {
        if (condition) {
            action.accept(this);
        }
        return this;
    }

    /**
     * If present value output writer.
     *
     * @param condition the condition
     * @param action    the action
     * @return the value output writer
     */
    public ValueOutputWriter ifPresent(Supplier<Boolean> condition, Consumer<ValueOutputWriter> action) {
        if (condition.get()) {
            action.accept(this);
        }
        return this;
    }

    /**
     * Discard value output writer.
     *
     * @param key the key
     * @return the value output writer
     */
    public ValueOutputWriter discard(String key) {
        valueOutput.discard(key);
        return this;
    }

    /**
     * Gets raw.
     *
     * @return the raw
     */
    @NotNull
    public net.minecraft.world.level.storage.ValueOutput getRaw() {
        return valueOutput;
    }

    /**
     * Write vec 3.
     *
     * @param valueOutput the value output
     * @param vec         the vec
     */
    public static void writeVec3(@NotNull net.minecraft.world.level.storage.ValueOutput valueOutput, @NotNull Vec3 vec) {
        valueOutput.putDouble("X", vec.x);
        valueOutput.putDouble("Y", vec.y);
        valueOutput.putDouble("Z", vec.z);
    }

    /**
     * Write vec 3 safe.
     *
     * @param valueOutput the value output
     * @param vec         the vec
     */
    public static void writeVec3Safe(@NotNull net.minecraft.world.level.storage.ValueOutput valueOutput, @Nullable Vec3 vec) {
        if (vec != null) {
            writeVec3(valueOutput, vec);
        }
    }

    /**
     * The type Typed list writer.
     *
     * @param <T> the type parameter
     */
    public static class TypedListWriter<T> {
        private final ValueOutput.TypedOutputList<T> list;

        private TypedListWriter(ValueOutput.TypedOutputList<T> list) {
            this.list = list;
        }

        /**
         * Add typed list writer.
         *
         * @param element the element
         * @return the typed list writer
         */
        public TypedListWriter<T> add(T element) {
            if (element != null) {
                list.add(element);
            }
            return this;
        }

        /**
         * Add all typed list writer.
         *
         * @param elements the elements
         * @return the typed list writer
         */
        public TypedListWriter<T> addAll(Iterable<T> elements) {
            for (T element : elements) {
                if (element != null) {
                    list.add(element);
                }
            }
            return this;
        }

        /**
         * Is empty boolean.
         *
         * @return the boolean
         */
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }

    /**
     * The type Children list writer.
     */
    public static class ChildrenListWriter {
        private final ValueOutput.ValueOutputList list;

        private ChildrenListWriter(ValueOutput.ValueOutputList list) {
            this.list = list;
        }

        /**
         * Add child value output writer.
         *
         * @param consumer the consumer
         * @return the children list writer
         */
        public ChildrenListWriter addChild(Consumer<ValueOutputWriter> consumer) {
            ValueOutput child = list.addChild();
            consumer.accept(new ValueOutputWriter(child));
            return this;
        }

        /**
         * Discard last children list writer.
         *
         * @return the children list writer
         */
        public ChildrenListWriter discardLast() {
            list.discardLast();
            return this;
        }

        /**
         * Is empty boolean.
         *
         * @return the boolean
         */
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }
}
