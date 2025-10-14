package top.r3944realms.lib39.util.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Tuple {
    private final List<Object> elements;

    private Tuple(Object... elements) {
        this.elements = List.of(elements);
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Tuple of(Object... elements) {
        return new Tuple(elements);
    }

    public int size() {
        return elements.size();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.size());
        }
        return (T) elements.get(index);
    }

    public <T> T first() {
        return get(0);
    }

    public <T> T second() {
        return get(1);
    }

    public <T> T third() {
        return get(2);
    }

    public <T> T last() {
        return get(elements.size() - 1);
    }

    public List<Object> toList() {
        return new ArrayList<>(elements);
    }

    public Object[] toArray() {
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

    @Override
    public String toString() {
        return "Tuple" + elements;
    }

    public Iterator<Object> iterator() {
        return elements.iterator();
    }

    public java.util.stream.Stream<Object> stream() {
        return elements.stream();
    }
}
