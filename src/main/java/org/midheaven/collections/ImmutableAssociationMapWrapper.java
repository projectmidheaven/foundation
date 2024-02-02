package org.midheaven.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class ImmutableAssociationMapWrapper<K, V> implements Association<K,V> {

    protected final Map<K, V> original;

    ImmutableAssociationMapWrapper(Map<K,V> original){
        this.original = original;
    }


    @Override
    public Association<K, V> union(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        Map<K,V> union = new HashMap<>(original);

        for (var entry : other){
           union.put(entry.key(), valueSelector.apply(union.get(entry.key()), entry.value()));
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
    public boolean containsKey(K key) {
        return original.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
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
    public Optional<V> getValue(K key) {
        return Optional.ofNullable(this.original.get(key));
    }

    @Override
    public Enumerator<Entry<K, V>> enumerator() {
        return new IteratorEnumeratorAdapter<>(this.iterator(), this.size());
    }

    @Override
    public boolean contains(Entry<K, V> any) {
        if (!this.original.containsKey(any.key())){
            return false;
        }
        var value = any.value();
        return value == null
              	? this.original.get(any.key()) == null
                : value.equals(this.original.get(any.key()));
    }

    @Override
    public Collection<Entry<K, V>> asCollection() {
        return original.entrySet().stream().map(it -> Entry.from(it)).toList();
    }

    @Override
    public long count() {
        return original.size();
    }

    @Override
    public int size() {
        return original.size();
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
