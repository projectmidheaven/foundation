package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

final class RepeatedSequence<T> implements Sequence<T> {

    private final Int count;
    private final T value;

    public RepeatedSequence(T value, Int count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(this.iterator(), count());
    }

    @Override
    public Int count() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Maybe<T> getAt(int index) {
        if (index >=0 && count.isGreaterThan(index)){
            return Maybe.of(value);
        }
        return Maybe.none();
    }

    @Override
    public Maybe<T> getAt(Int index) {
        if (index.isGreaterThanOrEqualTo(0) && index.isLessThan(count)){
            return Maybe.of(value);
        }
        return Maybe.none();
    }


    @Override
    public Int indexOf(Object o) {
        return value.equals(o) ? Int.ZERO : Int.MINUS_ONE;
    }

    @Override
    public Int lastIndexOf(Object o) {
        return value.equals(o) ? count.minus(1)  : Int.MINUS_ONE;
    }

    @Override
    public Maybe<T> first() {
        return Maybe.of(value);
    }

    @Override
    public Maybe<T> last() {
        return Maybe.of(value);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new RepeatedSequence<>(value, Int.of(toIndex - fromIndex));
    }

    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return new RepeatedSequence<>(value, toIndex.minus(fromIndex));
    }

    @Override
    public Sequence<T> reversed() {
        return this;
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return iterator();
    }

    @Override
    public ListIterator<T> iterator() {
        return new ListIterator<T>() {
            IntAccumulator index = new IntAccumulator(Int.MINUS_ONE);
            @Override
            public boolean hasNext() {
                return index.isLessThan(count);
            }

            @Override
            public T next() {
                index.increment();
                return value;
            }

            @Override
            public boolean hasPrevious() {
                return index.isPositive();
            }

            @Override
            public T previous() {
                index.decrement();
                return value;
            }

            @Override
            public int nextIndex() {
                return index.get().plus(1).toInt();
            }

            @Override
            public int previousIndex() {
                return index.get().minus(1).toInt();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T t) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public List<T> asCollection() {
        return Collections.nCopies(count.toInt(), value);
    }
}
