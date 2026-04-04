package org.midheaven.lang;

/**
 * Represents a value that has a mathematical sign and can produce its additive inverse.
 *
 * @param <T> the concrete signed type
 */
public interface Signed<T> {

    /**
     * Returns the sign of this value.
     *
     * @return a negative value if this value is negative, {@code 0} if it is zero,
     * or a positive value if it is positive
     */
    int sign();

    /**
     * Returns the additive inverse of this value.
     *
     * @return the negated value
     */
    T negate();
    
    /**
     * Tests if this value is zero.
     *
     * @return {@code true} if {@link #sign()} returns {@code 0}; {@code false} otherwise
     */
    default boolean isZero(){
        return sign() == 0;
    }
    
    /**
     * Tests if this value is positive.
     *
     * @return {@code true} if {@link #sign()} is greater than {@code 0}; {@code false} otherwise
     */
    default boolean isPositive(){
        return sign() > 0;
    }

    /**
     * Tests if this value is negative.
     *
     * @return {@code true} if {@link #sign()} is less than {@code 0}; {@code false} otherwise
     */
    default boolean isNegative(){
        return sign() < 0;
    }

    /**
     * Returns the absolute value of this instance.
     *
     * @return {@link #negate()} when this value is negative; otherwise this instance
     */
    @Nullable
    /**
     * Performs abs.
     * @return the result of abs
     */
    default T abs() {
        return this.sign() < 0 ? this.negate() : (T)this;
    }

}
