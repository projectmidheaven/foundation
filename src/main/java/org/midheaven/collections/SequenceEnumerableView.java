package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;

class SequenceEnumerableView<T> extends AbstractEnumerableView<T> implements Sequence<T>{
    
    SequenceEnumerableView(Enumerable<T> original){
        super(original);
    }
    
    /**
     * Returns get At.
     * @param index the index value
     * @return the result of getAt
     */
    @Override
    /**
     * Returns get At.
     * @param index the index value
     * @return the result of getAt
     */
    public Maybe<T> getAt(Int index) {
        return skip(index.toInt()).first();
    }
    
    /**
     * Performs indexOf.
     * @param o the o value
     * @return the result of indexOf
     */
    @Override
    /**
     * Performs indexOf.
     * @param o the o value
     * @return the result of indexOf
     */
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
    
    /**
     * Performs reverse Iterator.
     * @return the result of reverseIterator
     */
    @Override
    /**
     * Performs reverse Iterator.
     * @return the result of reverseIterator
     */
    public Iterator<T> reverseIterator() {
        // a copy is needed
        return original.toSequence().reverseIterator();
    }
    
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public final boolean equals(Object other){
        return other instanceof Sequence that
                   && AssortmentSupport.equals(this, that);
    }
    
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    @Override
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode (){
        return this.count().hashCode();
    }
    
    /**
     * Returns to String.
     * @return the result of toString
     */
    @Override
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString (){
        return AssortmentSupport.toString(this, '[', ']');
    }
}
