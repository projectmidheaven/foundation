package org.midheaven.collections;

import java.util.HashMap;
import java.util.function.Function;

/***
 * An {@link Enumerable} of {@link Association.Entry}s with additional operations
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public interface AssociatedEnumerable<K,V> extends Enumerable<Association.Entry<K,V>> {

    default Association<K,V> toAssociation(){
        var enumerator = enumerator();

        if (enumerator.length() instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be aggregated into an Association");
        }

        var map = new HashMap<K,V>();

        while(enumerator.tryNext(it -> map.put(it.key(), it.value())));

        return new ImmutableAssociationMapWrapper<>(map);
    }

    default <Q,W> AssociatedEnumerable<Q,W> mapEntries(Function<Association.Entry<K, V>, Association.Entry<Q,W>> transform){
        return new PipeAssociatedEnumerable<>(map(entry -> transform.apply(entry)));
    }

    default <Q> AssociatedEnumerable<Q,V> mapKey(Function<K, Q> transform){
        return mapEntries( entry -> entry.withKey(transform.apply(entry.key())));
    }

    default <W> AssociatedEnumerable<K,W> mapValue(Function<V, W> transform){
        return mapEntries( entry -> entry.withValue(transform.apply(entry.value())));
    }
}
