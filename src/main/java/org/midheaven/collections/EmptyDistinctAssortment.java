package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Collections;
import java.util.Set;

/**
 * Represents Empty Distinct Assortment.
 */
public class EmptyDistinctAssortment<T> extends AbstractDistinctAssortment<T> {

    private static final EmptyDistinctAssortment ME = new EmptyDistinctAssortment();

    /**
     * Performs instance.
     * @return the result of instance
     */
    public static <S> DistinctAssortment<S> instance(){
        return ME;
    }

    /**
     * Checks whether contains All.
     * @param all the all value
     * @return the result of containsAll
     */
    @Override
    /**
     * Checks whether contains All.
     * @param all the all value
     * @return the result of containsAll
     */
    public boolean containsAll(Iterable<? extends T> all){
        return false;
    }

    /**
     * Performs contains.
     * @param candidate the any value
     * @return the result of contains
     */
    @Override
    /**
     * Performs contains.
     * @param any the any value
     * @return the result of contains
     */
    public boolean contains(T candidate) {
        return false;
    }

    /**
     * Returns to Collection.
     * @return the result of toCollection
     */
    @Override
    /**
     * Returns to Collection.
     * @return the result of toCollection
     */
    public Set<T> toCollection() {
        return Collections.emptySet();
    }

    /**
     * Performs count.
     * @return the result of count
     */
    @Override
    /**
     * Performs count.
     * @return the result of count
     */
    public Int count() {
        return Int.ZERO;
    }

    /**
     * Returns an enumerator over the elements.
     * @return the result of enumerator
     */
    @Override
    /**
     * Returns an enumerator over the elements.
     * @return the result of enumerator
     */
    public Enumerator<T> enumerator() {
        return Enumerator.empty();
    }

    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    @Override
    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    public boolean isEmpty() {
        return true;
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
        return 0;
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
        return "{}";
    }
}
