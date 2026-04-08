package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.function.IntFunction;

class ReadOnlySingleSequence<T> extends AbstractSequence<T>{
    
    final T element;
    
    ReadOnlySingleSequence(T element){
        this.element = element;
    }
    
    @Override
    public Int indexOf(Object o) {
        if (element.equals(o)){
            return Int.ZERO;
        }
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.single(element);
    }
    
    @Override
    public Int count() {
        return Int.ONE;
    }
    
    protected int size(){
        return 1;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    

    @Override
    public String toString (){
        return "[" + element + "]";
    }
    
    public Object[] toArray(){
        return new Object[]{element};
    }
    
    public T[] toArray(T[] templateArray){
        var array = EnumerableSupport.correctLengthArray(templateArray, () -> 1);
        array[0] = element;
        return array;
    }
    
    public T[] toArray(IntFunction<T[]> generator){
        var array = generator.apply(1);
        array[0] = element;
        return array;
    }
}
