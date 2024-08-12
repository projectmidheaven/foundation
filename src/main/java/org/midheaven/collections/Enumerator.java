package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Iterator;

public interface Enumerator<T> {

    static <T> Enumerator<T> empty() {
        return new Enumerator<>(){

            @Override
            public boolean moveNext() {
                return false;
            }

            @Override
            public T current(){
                throw new IllegalStateException();
            }

            @Override
            public Length length() {
                return Length.finite(Int.ZERO);
            }
        };
    }

    static <T> Enumerator<T> fromIterator(Iterator<T> iterator) {
        return new IteratorEnumeratorAdapter<>(iterator);
    }

    static <T> Enumerator<T> fromIterator(Iterator<T> iterator, Int size) {
        return new IteratorEnumeratorAdapter<>(iterator, size);
    }

    static <T> Enumerator<T> single(T value) {
        return new Enumerator<T>() {
            boolean moved = false;

            @Override
            public boolean moveNext() {
                if (!moved){
                    moved = true;
                    return true;
                }
                return false;
            }

            @Override
            public T current() {
                return value;
            }

            @Override
            public Length length() {
                return Length.finite(Int.ONE);
            }
        };
    }

    /**
     * Move the enumerator to the next item.
     * All enumerator are create positioned before the first item.
     * @return true if the move was successful.
     */
    boolean moveNext();

    /**
     * The selected item. If this method is called before calling moveNext, or no current element exists,
     * an {@link IllegalStateException} is thrown.
     * @return the current element
     * @throws IllegalStateException if called before calling {@code moveNext}
     */
    T current();

    /**
     * The {@link Length} of the {@link Enumerator}
     * @return The {@link Length} of the {@link Enumerator}
     */
    Length length();

    default Iterator<T> toIterator(){
        return new EnumeratorIteratorAdapter<>(this);
    }
}
