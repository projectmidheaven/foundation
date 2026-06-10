package org.midheaven.lang;

/**
 * Defines the contract for an object that acts as a flag
 * @param <E> the corresponding FlagElement
 */
public interface Flag<E extends FlagElement<E>> {
    
    /**
     * Checks whether the given {@code FlagElement} is true
     * @param candidate the candidate value
     * @return {@code true} if it is Set
     */
    boolean isSet(FlagElement<E> candidate);
    
    /**
     * Sets the given {@code FlagElement} value
     * @param element the element value
     * @return the result of set
     */
    Flag<E> set(FlagElement<E> element);
    
    /**
     * Sets the given {@code FlagElement} according to given value.
     * If value is {@code true} calling this is equivalent to call {@link #set(FlagElement)},
     * otherwise is equivalent to call {@link #clear(FlagElement)}
     *
     * @param element the element value
     * @return the result of set
     */
    default Flag<E> set(FlagElement<E> element, boolean value){
        return value ? set(element) : clear(element);
    }
    
    
    /**
     * Clears the given {@code FlagElement} value
     * @param element the element value
     * @return the result of set
     */
    Flag<E> clear(FlagElement<E> element);
    
    /**
     * Inverts the given {@code FlagElement} value. Set is changed to clear, and clear is changed to set
     * @param element the element value
     * @return the result of flip
     */
    Flag<E> flip(FlagElement<E> element);
    
}
