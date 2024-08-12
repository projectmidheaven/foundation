package org.midheaven.lang;

public interface Ordered<T> extends Comparable<T> {

    static <S extends Ordered<S>> S min(S a, S b) {
        if (a.isLessThanOrEqualTo(b)){
            return a;
        }
        return b;
    }

    static <S extends Ordered<S>> S min(S a, S b, S ... others) {
       var current = min(a,b);
       for ( var other : others){
           current = min(current, other);
       }
       return current;
    }

    static <S extends Ordered<S>> S max(S a, S b) {
        if (a.isGreaterThanOrEqualTo(b)){
            return a;
        }
        return b;
    }

    static <S extends Ordered<S>> S max(S a, S b, S ... others) {
        var current = max(a,b);
        for ( var other : others){
            current = max(current, other);
        }
        return current;
    }

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
