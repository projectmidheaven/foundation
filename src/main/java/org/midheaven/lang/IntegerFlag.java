package org.midheaven.lang;

public class IntegerFlag<E extends FlagElement<E>> implements Flag<E> {

    static <F extends FlagElement<F>> IntegerFlag<F> none(Class<F> type){
        return new IntegerFlag<>(0);
    }

    static <F extends FlagElement<F>> IntegerFlag<F> with(F element){
        return new IntegerFlag<F>(0).set(element);
    }

    static <F extends FlagElement<F>> IntegerFlag<F> fromInt(int value){
        return new IntegerFlag<>(value);
    }

    private final int bits;

    IntegerFlag(int value) {
        this.bits = value;
    }

    int toInt(){
        return bits;
    }

    public boolean isSet(FlagElement<E> candidate){
        return (this.bits & 1 << candidate.flagPosition()) > 0;
    }

    public IntegerFlag<E> set(FlagElement<E> element){
        if (element.flagPosition() < 0){
            throw new IllegalArgumentException("FlagElement must have non negative position");
        }
        return new IntegerFlag<>( this.bits | 1 << element.flagPosition());
    }

    public IntegerFlag<E> clear(FlagElement<E> element){
        return new IntegerFlag<>(bits & ~(1 << element.flagPosition()));
    }

    public IntegerFlag<E> flip(FlagElement<E> element){
        return new IntegerFlag<>(bits ^ (1 << element.flagPosition()));
    }

}