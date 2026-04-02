package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Set;


record SingleDistinctAssortment<T>(T value) implements DistinctAssortment<T>{
    
    @Override
    public boolean contains(Object other) {
        return value.equals(other);
    }
    
    @Override
    public Set<T> toCollection() {
        return Set.of(value);
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.single(value);
    }
    
    @Override
    public Int count() {
        return Int.ONE;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
}
