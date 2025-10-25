package top.r3944realms.lib39.util.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The type Triple.
 *
 * @param <A> the type parameter
 * @param <B> the type parameter
 * @param <C> the type parameter
 */
@SuppressWarnings("unused")
public final class Triple<A, B, C> {
    /**
     * The First.
     */
    public A first;
    /**
     * The Second.
     */
    public B second;
    /**
     * The Third.
     */
    public C third;

    private Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Of @ not null triple.
     *
     * @param <A>    the type parameter
     * @param <B>    the type parameter
     * @param <C>    the type parameter
     * @param first  the first
     * @param second the second
     * @param third  the third
     * @return the @ not null triple
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static <A, B, C> @NotNull Triple<A, B, C> of(A first, B second, C third) {
        return new Triple<>(first, second, third);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) &&
                Objects.equals(second, triple.second) &&
                Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Triple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}