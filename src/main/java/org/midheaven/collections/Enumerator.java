package org.midheaven.collections;

import org.midheaven.lang.Nullable;
import org.midheaven.math.Int;

import java.util.Iterator;

/**
 * Defines the contract for Enumerator. An Enumerator has the same purpose as an {@link Iterator}
 * but works differently, facilitating logics. The Enumerator merges the {@link Iterator#hasNext()} and {@link Iterator#next()}
 * methods in a single {@link Enumerator#moveNext}. If the operations is successful the element can be read with {@link Enumerator#moveNext}.
 * Otherwise, trying to read {@link Enumerator#moveNext} will throw a {@code IllegalStateException}.
 *
 * Also, the Enumerator has a {@link Enumerator#length()} attribute that can be infinite, finite or unknown.
 * @param <T> type of the elements in the Enumerator
 */
public interface Enumerator<T> {

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
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

    /**
     * Creates an Enumerator from an {@link Iterator} that can be infinite.
     * @param iterator the {@link Iterator}
     * @return the resulting Enumerator
     */
    static <T> Enumerator<T> fromIterator(Iterator<T> iterator) {
        return new IteratorEnumeratorAdapter<>(iterator);
    }
    
    /**
     * Creates an Enumerator from an {@link Iterator} that can be infinite, but limites it to a fixed size.
     * @param iterator the {@link Iterator}
     * @return the resulting Enumerator
     */
    static <T> Enumerator<T> fromIterator(Iterator<T> iterator, Int size) {
        return new IteratorEnumeratorAdapter<>(iterator, size);
    }

    /**
     * Creates an Enumerator with just one single value
     * @param value the value
     * @return the resulting Enumerator
     */
    static <T> Enumerator<T> single(@Nullable  T value) {
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
     * @return true if the move was successful, and {@link Enumerator#current()} is safe to call.
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
     * The {@link Length} of the {@link Enumerator}. An {@link Enumerator}s length can be infinite.
     * @return The {@link Length} of the {@link Enumerator}
     */
    Length length();

    /**
     * Returns an {@link Iterator} corresponding with this {@link Enumerator}
     * @return the resulting {@link Iterator}
     */
    default Iterator<T> toIterator(){
        return new EnumeratorIteratorAdapter<>(this);
    }
}
