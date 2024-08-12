package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
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
    public Maybe<V> getValue(K key) {
        return Maybe.of(this.original.get(key));
    }

    @Override
    public Enumerator<Entry<K, V>> enumerator() {
        return Enumerator.fromIterator(this.iterator(), this.count());
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

    @Override
    public boolean equals(Object other){
        if(other instanceof Association association){
            if (this.original.size() != association.count().toInt()){
                return false;
            }
            for (var entry : this.original.entrySet()) {
                if (!association.containsKey(entry.getKey()) || !Objects.equals(association.getValue(entry.getKey()).orNull(), entry.getValue())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("{");
        for (var entry : this){
            builder.append(entry.key()).append("->").append(entry.value()).append(",");
        }
        builder.deleteCharAt(builder.length() -1);
        builder.append("}");

        return builder.toString();
    }
}
