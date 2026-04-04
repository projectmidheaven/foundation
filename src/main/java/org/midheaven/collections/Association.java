package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * An {@link Assortment} of {@link Association.Entry<K,V>} that allows for retrieval of the values associated with the keys.
 * @param <K> the type of the Key
 * @param <V> the type of the Value
 */
public interface Association<K,V> extends Assortment<Association.Entry<K,V>> {

    interface Entry<K, V> {
        /**
         * Creates a {@link Association.Entry<K,V>} from the given key and value
         * @param key the key
         * @param value the value
         * @return the created {@link Association.Entry<K,V>}
         */
        static <K, V> Entry<K,V> entry(K key, V value) {
            return new org.midheaven.collections.Entry<>(key, value);
        }
        
        /**
         * Creates a {@link Association.Entry} from the given {@link Map.Entry}
         * @param entry the entry
         * @return the created {@link Association.Entry<K,V>}
         */
        static <K, V> Entry<K,V> from(Map.Entry<K,V> entry) {
            return new org.midheaven.collections.Entry<>(entry.getKey(), entry.getValue());
        }
        
        /**
         *
         * @return the entries' key
         */
        K key();
        
        /**
         *
         * @return the entries' value
         */
		@Nullable V value();
        
        /**
         * Creates a new {@link Association.Entry} with the same value as {@code this}, but mapped to te given key
         */
        <Q> @NotNullable Entry<Q, V> withKey(@Nullable Q newKey);
        <W> @NotNullable Entry<K, W> withValue(@Nullable W newValue);
    }

    /**.
     * @return the builder
     */
    static @NotNullable AssociationBuilder builder(){
        return AssociationBuilder.INSTANCE;
    }
    
    /**
     * Determines if this {@code Association} contains the given key
     * @param key the key to test
     * @return {@code true} if the key is contained in the {@code Association}, {@code false} otherwise.
     */
    boolean containsKey(Object key);

    /**
     * Determines if this {@code Association} contains the given key
     * @param value the value to test
     * @return {@code true} if the key is contained in the {@code Association}, {@code false} otherwise.
     */
    boolean containsValue(Object value);

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

    /**
     * Returns keys.
     * @return the result of keys
     */
    DistinctAssortment<K> keys();
    /**
     * Returns values.
     * @return the result of values
     */
    Assortment<V> values();

    /**
     * Returns get Value.
     * @param key the key value
     * @return the result of getValue
     */
    Maybe<V> getValue(Object key);
    
    /**
     * Returns to Collection.
     * @return the result of toCollection
     */
    default Set<Entry<K,V>> toCollection(){
        return collect(Collectors.toUnmodifiableSet());
    }
    
}

record Entry<K,V>(K key, V value) implements Association.Entry<K,V> {

    @Override
    public <Q> Association.Entry<Q, V> withKey(Q newKey) {
        return new Entry<>(newKey, value);
    }

    @Override
    public <W> Association.Entry<K, W> withValue(W newValue) {
        return new Entry<>(key, newValue);
    }
    
    @Override
    public String toString(){
        return key + "->" + value;
    }
}