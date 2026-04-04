package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

class ImmutableAssociationMapWrapper<K, V> extends AbstractAssociation<K,V> {

    protected final Map<K, V> original;

    ImmutableAssociationMapWrapper(Map<K,V> original){
        this.original = original;
    }


    @Override
    public Association<K, V> union(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        Map<K,V> union = new HashMap<>(original);

        for (var entry : other){
           if (!union.containsKey(entry.key())){
               union.put(entry.key(), entry.value());
           } else {
               union.put(entry.key(), valueSelector.apply(union.get(entry.key()), entry.value()));
           }
        }

        return new ImmutableAssociationMapWrapper<>(union);
    }

    @Override
    public Association<K, V> intersection(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        Map<K,V> union = new HashMap<>();

        for (var entry : other){
            if (original.containsKey(entry.key())){
                union.put(entry.key(), valueSelector.apply(original.get(entry.key()), entry.value()));
            }
        }

        return new ImmutableAssociationMapWrapper<>(union);
    }

    @Override
    public boolean containsKey(Object key) {
        return original.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return original.containsValue(value);
    }

    @Override
    public V computeValueIfAbsent(K key, Function<K, V> computation) {
        return this.getValue(key).orElseGet(() -> computation.apply(key));
    }

    @Override
    public DistinctAssortment<K> keys() {
        return new ResizableSetWrapper<>(this.original.keySet());
    }

    @Override
    public Assortment<V> values() {
        return new ImmutableCollectionWrapper<>(this.original.values());
    }

    @Override
    public Maybe<V> getValue(Object key) {
        return Maybe.of(this.original.get(key));
    }

    @Override
    public Enumerator<Entry<K, V>> enumerator() {
        return Enumerator.fromIterator(this.iterator(), this.count());
    }

    @Override
    public boolean contains(Entry<K, V> candidate) {
        if (!this.original.containsKey(candidate.key())){
            return false;
        }
        var value = candidate.value();
        return value == null
              	? this.original.get(candidate.key()) == null
                : value.equals(this.original.get(candidate.key()));
    }

    @Override
    public Set<Entry<K, V>> toCollection() {
        return original.entrySet().stream().map(Entry::from).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Int count() {
        return Int.of(original.size());
    }


    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            Iterator<Map.Entry<K, V>> iterator = original.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                return Entry.from(iterator.next());
            }
        };
    }
}
