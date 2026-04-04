package org.midheaven.collections;

import org.midheaven.lang.Maybe;

import java.util.ArrayList;
import java.util.List;


final class ComposedEnumerable<T> implements Enumerable<T> {
    
    private final List<Enumerable<T>> enumerables = new ArrayList<>(2);
    
    ComposedEnumerable(Enumerable<T> a, Enumerable<T> b) {
        enumerables.add(a);
        enumerables.add(b);
    }
    
    ComposedEnumerable(ComposedEnumerable<T> other, Enumerable<T> additional) {
        enumerables.addAll(other.enumerables);
        enumerables.add(additional);
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return new ComposedEnumerator<>(enumerables);
    }
    
    @Override
    public Maybe<T> first() {
        return enumerables.getFirst().first();
    }
    
    @Override
    public boolean isEmpty() {
       for (var item : enumerables){
           if (!item.isEmpty()){
               return false;
           }
       }
       return true;
    }
    
    @Override
    public boolean any() {
        for (var item : enumerables){
            if (item.isEmpty()){
                return true;
            }
        }
        return false;
    }
    

}

class ComposedEnumerator<T> implements   Enumerator<T> {
    
    private final List<Enumerator<T>> enumerables;
    private int activeIndex = 0;
    
    public ComposedEnumerator(List<Enumerable<T>> enumerables) {
        this.enumerables = enumerables.stream().map(Enumerable::enumerator).toList();
    }
    
    @Override
    public boolean moveNext() {
        
        while (activeIndex < enumerables.size()){
            if (enumerables.get(activeIndex).moveNext()){
                return true;
            } else {
                activeIndex++;
            }
        }
        
        return false;
        
    }
    
    @Override
    public T current() {
        return enumerables.get(activeIndex).current();
    }
    
    @Override
    public Length length() {
        return Length.Unknown.INSTANCE;
    }
}
