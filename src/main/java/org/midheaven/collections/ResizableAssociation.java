package org.midheaven.collections;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ResizableAssociation<K, V> extends EditableAssociation<K,V>{

    void putValue(K key, V value);

    void clear();

    /**
     * Removes the key and the associated value.
     * If the key or the value are not present {@code Optional.empty} is returned.
     * @param key
     * @return
     */
    Optional<V> removeKey(K key);

    /***
     * If the value is found return it. If not, compute a value from the key.
     * The computed value is permanently associated with the key.
     * The next call will find the value associated with the key.
     * @param key
     * @param computation
     * @return
     */
    V computeValueIfAbsent(K key, Function<K,V> computation);


    void unionWith(Association<K, V> other, BiFunction<V, V, V> valueSelector);

    void intersectWith(Association<K, V> other, BiFunction<V, V, V> valueSelector);
}
