package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("rawtypes")
class ImmutableEmptyAssociation<K,V> implements Association<K,V>{
    
    private static final ImmutableEmptyAssociation INSTANCE = new ImmutableEmptyAssociation();
    
    @SuppressWarnings("unchecked")
    static <X,Y> ImmutableEmptyAssociation<X,Y> instance(){
        return INSTANCE;
    }
    
    private ImmutableEmptyAssociation(){}
    
    @Override
    public boolean containsKey(Object key) {
        return false;
    }
    
    @Override
    public boolean containsValue(Object value) {
        return false;
    }
    
    @Override
    public Association<K, V> union(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        return other;
    }
    
    @Override
    public Association<K, V> intersection(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        return this;
    }
    
    @Override
    public V computeValueIfAbsent(K key, Function<K, V> computation) {
        return computation.apply(key);
    }
    
    @Override
    public DistinctAssortment<K> keys() {
        return DistinctAssortment.builder().empty();
    }
    
    @Override
    public Assortment<V> values() {
        return DistinctAssortment.builder().empty();
    }
    
    @Override
    public Maybe<V> getValue(Object key) {
        return Maybe.none();
    }
    
    @Override
    public boolean contains(Entry<K, V> candidate) {
        return false;
    }
    
    @Override
    public Enumerator<Entry<K, V>> enumerator() {
        return Enumerator.empty();
    }
    
    @Override
    public Int count() {
        return Int.ZERO;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
}
