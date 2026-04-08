package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


class ReadOnlyCollectionAssortment<T> implements Assortment<T>{

    private final Collection<T> original;

    ReadOnlyCollectionAssortment(Collection<T> original){
        this.original = original;
    }
    
    @Override
    public boolean contains(T candidate) {
        return original.contains(candidate);
    }
    
    @Override
    public Collection<T> toCollection() {
        return Collections.unmodifiableCollection(original);
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
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(original.iterator(), count());
    }
    
    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }
    
}
