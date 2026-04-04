package org.midheaven.collections;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;
import org.midheaven.math.Int;

import java.util.Objects;
import java.util.Set;


@ValueClass
final class SingleDistinctAssortment<T> extends AbstractDistinctAssortment<T>{
    
    private final T value;
    
    public SingleDistinctAssortment(@Nullable T value){
        this.value = value;
    }
    
    @Override
    public String toString(){
        return "{" + value + "}";
    }
    
    @Override
    public boolean contains(Object candidate) {
        return Objects.equals(value, candidate);
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
