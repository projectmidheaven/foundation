package org.midheaven.lang;

/**
 * Defines the contract for Long Ordered.
 */
public interface LongOrdered {

    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    int compareTo(long other);

    /**
     * Checks whether is Less Than.
     * @param other the other value
     * @return the result of isLessThan
     */
    default boolean isLessThan(long other){
        return this.compareTo(other) < 0;
    }

    /**
     * Checks whether is Less Than Or Equal To.
     * @param other the other value
     * @return the result of isLessThanOrEqualTo
     */
    default boolean isLessThanOrEqualTo(long other){
        return this.compareTo(other) <= 0;
    }

    /**
     * Checks whether is Greater Than.
     * @param other the other value
     * @return the result of isGreaterThan
     */
    default boolean isGreaterThan(long other){
        return this.compareTo(other) > 0;
    }

    /**
     * Checks whether is Greater Than Or Equal To.
     * @param other the other value
     * @return the result of isGreaterThanOrEqualTo
     */
    default boolean isGreaterThanOrEqualTo(long other){
        return this.compareTo(other) >= 0;
    }

    /**
     * Checks whether is Equal To.
     * @param other the other value
     * @return the result of isEqualTo
     */
    default boolean isEqualTo(long other){
        return this.compareTo(other) == 0;
    }

}
