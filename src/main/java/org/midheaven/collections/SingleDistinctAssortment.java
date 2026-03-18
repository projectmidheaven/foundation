package org.midheaven.collections;

import org.midheaven.lang.ValueClass;
import org.midheaven.math.Int;

import java.util.Set;

@ValueClass
 record SingleDistinctAssortment<T>(T value) implements DistinctAssortment<T>{
    
    @Override
    public boolean contains(Object other) {
        return value.equals(other);
    }
    
    @Override
    public Set<T> asCollection() {
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
