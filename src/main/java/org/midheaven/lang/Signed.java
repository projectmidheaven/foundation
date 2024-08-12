package org.midheaven.lang;

public interface Signed<T> {

    int sign();
    T negate();

    default boolean isPositive(){
        return sign() > 0;
    }

    default boolean isNegative(){
        return sign() < 0;
    }

    @NotNull default T abs() {
        return this.sign() < 0 ? this.negate() : (T)this;
    }

}
