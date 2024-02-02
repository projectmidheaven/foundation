package org.midheaven.lang;

public interface Ordered<T> extends Comparable<T> {

    default boolean isLessThan(T other){
        return this.compareTo(other) < 0;
    }

    default boolean isLessThanOrEqualTo(T other){
        return this.compareTo(other) <= 0;
    }

    default boolean isGreaterThan(T other){
        return this.compareTo(other) > 0;
    }

    default boolean isGreaterThanOrEqualTo(T other){
        return this.compareTo(other) >= 0;
    }

    default boolean isEqualTo(T other){
        return this.compareTo(other) == 0;
    }

}
