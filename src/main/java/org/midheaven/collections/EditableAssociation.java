package org.midheaven.collections;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An {@link Association} that permits changing the values for the present keys, but does not allow
 * to add more keys or change the ones already associated.
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public interface EditableAssociation<K, V> extends Association<K,V>{

    /**
     * Changes the value associated with the key. If the key is not present, nothing happens.
     * @param key
     * @param value
     * @return {@code true} if the key is present, {@code false} otherwise.
     */
    boolean setValue(K key, V value);

    V computeValue(K key, BiFunction<K, V, V> computation);
}