package org.midheaven.lang;

public interface LongOrdered {

    int compareTo(long other);

    default boolean isLessThan(long other){
        return this.compareTo(other) < 0;
    }

    default boolean isLessThanOrEqualTo(long other){
        return this.compareTo(other) <= 0;
    }

    default boolean isGreaterThan(long other){
        return this.compareTo(other) > 0;
    }

    default boolean isGreaterThanOrEqualTo(long other){
        return this.compareTo(other) >= 0;
    }

    default boolean isEqualTo(long other){
        return this.compareTo(other) == 0;
    }

}
