package top.r3944realms.lib39.util.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * The type Tuple.
 */
@SuppressWarnings("unused")
public final class Tuple {
    private final List<Object> elements;

    private Tuple(Object... elements) {
        this.elements = List.of(elements);
    }

    /**
     * Of tuple.
     *
     * @param elements the elements
     * @return the tuple
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Tuple of(Object... elements) {
        return new Tuple(elements);
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return elements.size();
    }

    /**
     * Get t.
     *
     * @param <T>   the type parameter
     * @param index the index
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.size());
        }
        return (T) elements.get(index);
    }

    /**
     * First t.
     *
     * @param <T> the type parameter
     * @return the t
     */
    public <T> T first() {
        return get(0);
    }

    /**
     * Second t.
     *
     * @param <T> the type parameter
     * @return the t
     */
    public <T> T second() {
        return get(1);
    }

    /**
     * Third t.
     *
     * @param <T> the type parameter
     * @return the t
     */
    public <T> T third() {
        return get(2);
    }

    /**
     * Last t.
     *
     * @param <T> the type parameter
     * @return the t
     */
    public <T> T last() {
        return get(elements.size() - 1);
    }

    /**
     * To list list.
     *
     * @return the list
     */
    @Contract(value = " -> new", pure = true)
    public @NotNull List<Object> toList() {
        return new ArrayList<>(elements);
    }

    /**
     * To array object [ ].
     *
     * @return the object [ ]
     */
    @Contract(pure = true)
    public Object @NotNull [] toArray() {
        return elements.toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Objects.equals(elements, tuple.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Tuple" + elements;
    }

    /**
     * Iterator iterator.
     *
     * @return the iterator
     */
    public @NotNull Iterator<Object> iterator() {
        return elements.iterator();
    }

    /**
     * Stream java . util . stream . stream.
     *
     * @return the java . util . stream . stream
     */
    public java.util.stream.Stream<Object> stream() {
        return elements.stream();
    }
}
