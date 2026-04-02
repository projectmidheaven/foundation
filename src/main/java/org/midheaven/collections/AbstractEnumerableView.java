package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;

abstract class AbstractEnumerableView<T> implements Enumerable<T> {
    
    protected Enumerable<T> original;
    
    AbstractEnumerableView(Enumerable<T> original){
        this.original = original;
    }
    
    @Override
    public Maybe<T> first() {
        return original.first();
    }
    @Override
    public Enumerator<T> enumerator() {
        return original.enumerator();
    }
    
    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }
    
    @Override
    public Int count() {
        return original.count();
    }
    
    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }
}
