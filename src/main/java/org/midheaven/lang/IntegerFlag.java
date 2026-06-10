package org.midheaven.lang;

/**
 * Represents Integer Flag.
 * @param <E> the corresponding FlagElement
 */
public class IntegerFlag<E extends FlagElement<E>> extends AbstractBinaryFlag<E, IntegerFlag<E>> {

    /**
     * Performs none.
     * @param type the type value
     * @return the result of none
     */
    static <F extends FlagElement<F>> IntegerFlag<F> none(Class<F> type){
        return new IntegerFlag<>(0);
    }

    /**
     * Performs with.
     * @param element the element value
     * @return the result of with
     */
    static <F extends FlagElement<F>> IntegerFlag<F> with(F element){
        return new IntegerFlag<F>(0).set(element);
    }

    /**
     * Returns from Int.
     * @param value the value value
     * @return the result of fromInt
     */
    static <F extends FlagElement<F>> IntegerFlag<F> fromInt(int value){
        return new IntegerFlag<>(value);
    }
    
    IntegerFlag(int value) {
        super(value);
    }
    
    @Override
    protected IntegerFlag<E> newInstance(int value) {
        return new IntegerFlag<>(value);
    }
    
    /**
     * Returns to Int.
     * @return the result of toInt
     */
    int toInt(){
        return bits;
    }
    
    
}
