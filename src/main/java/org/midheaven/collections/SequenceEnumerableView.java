package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;

public class SequenceEnumerableView<T> extends AbstractEnumerableView<T> implements Sequence<T>{
    
    SequenceEnumerableView(Enumerable<T> original){
        super(original);
    }
    
    @Override
    public Maybe<T> getAt(Int index) {
        return skip(index.toInt()).first();
    }
    
    @Override
    public Int indexOf(Object o) {
        long index = -1;
        for (var item : this){
            index++;
            if (item.equals(o)){
                break;
            }
        }
        return Int.of(index);
    }
    
    @Override
    public Iterator<T> reverseIterator() {
        // a copy is needed
        return original.toSequence().reverseIterator();
    }
    

}
