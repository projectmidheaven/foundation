package org.midheaven.lang;

/**
 * Defines the contract for Ordered.
 * @param <T> the ordered type
 */
public interface Ordered<T> extends Comparable<T> {

    /**
     * Performs min.
     * @param a the a value
     * @param b the b value
     * @return the result of min
     */
    static <S extends Ordered<S>> S min(S a, S b) {
        if (a.isLessThanOrEqualTo(b)){
            return a;
        }
        return b;
    }

    /**
     * Performs min.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of min
     */
    static <S extends Ordered<S>> S min(S a, S b, S ... others) {
       var current = min(a,b);
       for ( var other : others){
           current = min(current, other);
       }
       return current;
    }

    /**
     * Performs max.
     * @param a the a value
     * @param b the b value
     * @return the result of max
     */
    static <S extends Ordered<S>> S max(S a, S b) {
        if (a.isGreaterThanOrEqualTo(b)){
            return a;
        }
        return b;
    }

    /**
     * Performs max.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of max
     */
    static <S extends Ordered<S>> S max(S a, S b, S ... others) {
        var current = max(a,b);
        for ( var other : others){
            current = max(current, other);
        }
        return current;
    }

    /**
     * Checks whether is Less Than.
     * @param other the other value
     * @return the result of isLessThan
     */
    default boolean isLessThan(T other){
        return this.compareTo(other) < 0;
    }

    /**
     * Checks whether is Less Than Or Equal To.
     * @param other the other value
     * @return the result of isLessThanOrEqualTo
     */
    default boolean isLessThanOrEqualTo(T other){
        return this.compareTo(other) <= 0;
    }

    /**
     * Checks whether is Greater Than.
     * @param other the other value
     * @return the result of isGreaterThan
     */
    default boolean isGreaterThan(T other){
        return this.compareTo(other) > 0;
    }

    /**
     * Checks whether is Greater Than Or Equal To.
     * @param other the other value
     * @return the result of isGreaterThanOrEqualTo
     */
    default boolean isGreaterThanOrEqualTo(T other){
        return this.compareTo(other) >= 0;
    }

    /**
     * Checks whether is Equal To.
     * @param other the other value
     * @return the result of isEqualTo
     */
    default boolean isEqualTo(T other){
        return this.compareTo(other) == 0;
    }

}
