package org.midheaven.collections;

import org.midheaven.math.Int;

class ImmutableSingleSequence<T> extends AbstractSequence<T>{
    
    private final T item;
    
    ImmutableSingleSequence(T item){
        this.item = item;
    }
    
    @Override
    public Int indexOf(Object o) {
        if (item.equals(o)){
            return Int.ZERO;
        }
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.single(item);
    }
    
    @Override
    public Int count() {
        return Int.ONE;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public int hashCode (){
        return 1;
    }
    
    @Override
    public String toString (){
        return "[" + item + "]";
    }
}
