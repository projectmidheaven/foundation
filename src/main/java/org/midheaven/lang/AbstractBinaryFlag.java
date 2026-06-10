package org.midheaven.lang;

/**
 * Defines the contract for an object that acts as a flag based on the bits that are set on a value
 * @param <E> the corresponding FlagElement
 */
public abstract class AbstractBinaryFlag<E extends FlagElement<E>, ME extends AbstractBinaryFlag<E,ME>> implements Flag<E>{
    
    protected final int bits;
    
    protected AbstractBinaryFlag(int bits) {
        this.bits = bits;
    }
    
    public boolean isSet(FlagElement<E> candidate){
        return (this.bits & 1 << candidate.flagPosition()) > 0;
    }
    
    public ME set(FlagElement<E> element){
        if (element.flagPosition() < 0){
            throw new IllegalArgumentException("FlagElement must have non negative position");
        }
        return newInstance( this.bits | 1 << element.flagPosition());
    }
    
    public ME set(FlagElement<E> element, boolean value){
        return value ? set(element) : clear(element);
    }
    
    protected abstract ME newInstance(int value);

    public ME clear(FlagElement<E> element){
        return newInstance(bits & ~(1 << element.flagPosition()));
    }

  
    public ME flip(FlagElement<E> element){
        return newInstance(bits ^ (1 << element.flagPosition()));
    }
    
    
    public ME and(ME other) {
        return newInstance(this.bits & other.bits);
    }
}
