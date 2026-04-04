package org.midheaven.lang;

/**
 * Defines the contract for Flag.
 */
public interface Flag<E extends FlagElement<E>> {

    /**
     * Checks whether is Set.
     * @param candidate the candidate value
     * @return the result of isSet
     */
    boolean isSet(FlagElement<E> candidate);

    /**
     * Performs set.
     * @param element the element value
     * @return the result of set
     */
    Flag<E> set(FlagElement<E> element);

    /**
     * Performs clear.
     * @param element the element value
     * @return the result of clear
     */
    Flag<E> clear(FlagElement<E> element);

    /**
     * Performs flip.
     * @param element the element value
     * @return the result of flip
     */
    Flag<E> flip(FlagElement<E> element);

}
