package org.midheaven.collections;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/***
 * An {@link Assortment} of {@link Association.Entry<K,V>} that allows for retrieval of the values associated with the keys.
 * @param <K>
 * @param <V>
 */
public interface Association<K,V> extends Assortment<Association.Entry<K,V>> {

    interface Entry<K, V> {
        static <K, V> Entry<K,V> entry(K key, V value) {
            return new org.midheaven.collections.Entry<>(key, value);
        }
        static <K, V> Entry<K,V> from(Map.Entry<K,V> entry) {
            return new org.midheaven.collections.Entry<>(entry.getKey(), entry.getValue());
        }

        K key();
		V value();

        <Q> Entry<Q, V> withKey(Q otherKey);
        <W> Entry<K, W> withValue(W otherValue);
    }

    static AssociationBuilder builder(){
        return new AssociationBuilder();
    }

    /**
     * Determines if this {@code Association} contains the given key
     * @param key the key to test
     * @return {@code true} if the key is contained in the {@code Association}, {@code false} otherwise.
     */
    boolean containsKey(K key);

    /**
     * Determines if this {@code Association} contains the given key
     * @param key the key to test
     * @return {@code true} if the key is contained in the {@code Association}, {@code false} otherwise.
     */
    boolean containsValue(V value);

    /**
     * Creates the union of this association with another. If the same key is found in both
     * the function {@code valueSelector} is called to determine the result of the union.
     *
     * @param other the other association to union with.
     * @param valueSelector an operation to select the result of the union of two values
     * @return a new {@code Association} with the union of the keys of the original {@code Association}s
     */
    Association<K,V> union(Association<K,V> other, BiFunction<V , V, V> valueSelector);

    /**
     * Creates the intersection of this association with another. When the same key is found in both
     * the function {@code valueSelector} is called to determine the result of the union.
     *
     * @param other the other association to intersect with.
     * @param valueSelector an operation to select the result of the union of two values
     * @return a new {@code Association} with the common keys in the original {@code Association}s
     */
    Association<K,V> intersection(Association<K,V> other, BiFunction<V , V, V> valueSelector);

    /***
     * If the value is found return it. If not, compute a value from the key.
     * The computed value is not permanently associated with the key.
     * @param key
     * @param computation
     * @return
     */
    V computeValueIfAbsent(K key, Function<K,V> computation);

    DistinctAssortment<K> keys();
    Assortment<V> values();

    Optional<V> getValue(K key);
}

record Entry<K,V>(K key, V value) implements Association.Entry<K,V> {

    @Override
    public <Q> Association.Entry<Q, V> withKey(Q otherKey) {
        return new Entry<>(otherKey, value);
    }

    @Override
    public <W> Association.Entry<K, W> withValue(W otherValue) {
        return new Entry<>(key, otherValue);
    }
}