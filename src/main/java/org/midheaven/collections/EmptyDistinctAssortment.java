package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Collections;
import java.util.Set;


class EmptyDistinctAssortment<T> extends AbstractDistinctAssortment<T> {

    private static final EmptyDistinctAssortment ME = new EmptyDistinctAssortment();
    
    public static <S> DistinctAssortment<S> instance(){
        return ME;
    }


    @Override
    public boolean containsAll(Iterable<? extends T> all){
        return false;
    }
    
    @Override
    public boolean contains(T candidate) {
        return false;
    }


    @Override
    public Set<T> toCollection() {
        return Collections.emptySet();
    }
    
    @Override
    public Int count() {
        return Int.ZERO;
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.empty();
    }

    
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int hashCode (){
        return 0;
    }
    
    @Override
    public String toString (){
        return "{}";
    }
}
