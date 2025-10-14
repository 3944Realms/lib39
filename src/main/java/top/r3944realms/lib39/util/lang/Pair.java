package top.r3944realms.lib39.util.lang;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Pair<F, S> {
    public F first;
    public S second;

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Contract("null, _ -> fail; !null, null -> fail; !null, !null -> new")
    public static <F, S> @NotNull Pair<F, S> of(F first, S second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Pair.of requires non-null argument");
        }
        return new Pair<>(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Pair<?, ?> rhs)) {
            return false;
        }
        return first.equals(rhs.first) && second.equals(rhs.second);
    }
    @Override
    public int hashCode() {
        return first.hashCode() * 37 + second.hashCode();
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
