package org.midheaven.collections;

import org.midheaven.lang.Maybe;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface ResizableAssociation<K, V> extends EditableAssociation<K,V>{

    void putValue(K key, V value);

    void clear();

    /**
     * Removes the key and the associated value.
     * If the key or the value are not present {@code Maybe.none} is returned.
     * @param key
     * @return
     */
    Maybe<V> removeKey(K key);

    /***
     * If the value is found return it. If not, compute a value from the key.
     * The computed value is permanently associated with the key.
     * The next call will find the value associated with the key.
     * @param key
     * @param computation
     * @return
     */
    V computeValueIfAbsent(K key, Function<K,V> computation);

    /***
     * Adds all entries in {@code other} to {@code this}. If the key is already present the
     * valueSelector method is called with the original value and the new, to decide which remains.
     * @param other the other Association
     * @param valueSelector the function to select between values
     */
    default void unionWith(Association<K, V> other, BiFunction<V, V, V> valueSelector){
        for (var entry : other){
            this.putValue(entry.key(),
                    this.getValue(entry.key()).map(o -> valueSelector.apply(o, entry.value())).orElse(entry.value())
            );
        }
    }

    /***
     * Removes all entries and retains only the ones common to {@code other} and {@code this}. A
     * valueSelector method is called with the original value and the new, to decide which remains.
     * @param other the other Association
     * @param valueSelector the function to select between values
     */
    default void intersectWith(Association<K, V> other, BiFunction<V, V, V> valueSelector){
        var iterator = this.iterator();
        while (iterator.hasNext()){
            var key = iterator.next().key();
            if (!other.containsKey(key)){
                iterator.remove();
            }
        }
    }
}
