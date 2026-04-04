package org.midheaven.lang;

/**
 * Defines the contract for Flag Element.
 * @param <E> the corresponding FlagElement
 */
public interface FlagElement<E extends FlagElement<E>> {


    /**
     * Performs flagPosition.
     * @return the result of flagPosition
     */
    int flagPosition();
}
