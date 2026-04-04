package org.midheaven.lang;

/**
 * Represents Integer Flag.
 */
public class IntegerFlag<E extends FlagElement<E>> implements Flag<E> {

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

    private final int bits;

    IntegerFlag(int value) {
        this.bits = value;
    }

    /**
     * Returns to Int.
     * @return the result of toInt
     */
    int toInt(){
        return bits;
    }

    /**
     * Checks whether is Set.
     * @param candidate the candidate value
     * @return the result of isSet
     */
    public boolean isSet(FlagElement<E> candidate){
        return (this.bits & 1 << candidate.flagPosition()) > 0;
    }

    /**
     * Performs set.
     * @param element the element value
     * @return the result of set
     */
    public IntegerFlag<E> set(FlagElement<E> element){
        if (element.flagPosition() < 0){
            throw new IllegalArgumentException("FlagElement must have non negative position");
        }
        return new IntegerFlag<>( this.bits | 1 << element.flagPosition());
    }

    /**
     * Performs clear.
     * @param element the element value
     * @return the result of clear
     */
    public IntegerFlag<E> clear(FlagElement<E> element){
        return new IntegerFlag<>(bits & ~(1 << element.flagPosition()));
    }

    /**
     * Performs flip.
     * @param element the element value
     * @return the result of flip
     */
    public IntegerFlag<E> flip(FlagElement<E> element){
        return new IntegerFlag<>(bits ^ (1 << element.flagPosition()));
    }

}
