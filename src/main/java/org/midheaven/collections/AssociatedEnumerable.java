package org.midheaven.collections;

import java.util.HashMap;
import java.util.function.Function;

/***
 * An {@link Enumerable} of {@link Association.Entry}s with additional operations
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public interface AssociatedEnumerable<K,V> extends Enumerable<Association.Entry<K,V>> {
    
    /**
     * Returns an Association with the keys and values in {@code this}
     * @return the result of toAssociation
     */
    default Association<K,V> toAssociation(){
        var enumerator = enumerator();

        if (enumerator.length() instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be aggregated into an Association");
        }

        var map = new HashMap<K,V>();

        while(enumerator.moveNext()){
            var item = enumerator.current();
            map.put(item.key(), item.value());
        };

        return new ImmutableAssociationMapWrapper<>(map);
    }
    
    /**
     * Maps {@link Association.Entry} to new {@link Association.Entry}
     * @param mapper the mapping function
     * @return a new {@link AssociatedEnumerable} of the new entries
     */
    default <Q,W> AssociatedEnumerable<Q,W> mapEntries(Function<Association.Entry<K, V>, Association.Entry<Q,W>> mapper){
        return new PipeAssociatedEnumerable<>(map(mapper));
    }
    
    /**
     * Maps existing keys to new keys , keeping the same values
     * @param mapper the mapping function
     * @return a new {@link AssociatedEnumerable} of the new entries with keys changed
     */
    default <Q> AssociatedEnumerable<Q,V> mapKey(Function<K, Q> mapper){
        return mapEntries( entry -> entry.withKey(mapper.apply(entry.key())));
    }
    
    /**
     * Maps existing keys to new values , keeping the same keys
     * @param mapper the mapping function
     * @return a new {@link AssociatedEnumerable} of the new entries with values changed
     */
    default <W> AssociatedEnumerable<K,W> mapValue(Function<V, W> mapper){
        return mapEntries( entry -> entry.withValue(mapper.apply(entry.value())));
    }
}
