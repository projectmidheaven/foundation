package org.midheaven.lang;

/**
 * Defines the contract for Flag Element.
 */
public interface FlagElement<E extends FlagElement<E>> {


    /**
     * Performs flagPosition.
     * @return the result of flagPosition
     */
    int flagPosition();
}
