package org.midheaven.collections;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

final class RepeatedSequence<T> implements Sequence<T> {

    private final long count;
    private final T value;

    public RepeatedSequence(T value, long count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public Enumerator<T> enumerator() {
        return new IteratorEnumeratorAdapter<>(this.iterator(), count);
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public int size() {
        return (int) count;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Optional<T> getAt(int index) {
        if (index >=0 && index< count){
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

    @Override
    public int indexOf(Object o) {
        return value.equals(o) ? 0 : -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return value.equals(o) ? (int)count - 1 : -1;
    }

    @Override
    public Optional<T> first() {
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<T> last() {
        return Optional.ofNullable(value);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new RepeatedSequence<>(value, toIndex - fromIndex);
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
            int index = -1;
            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public T next() {
                index++;
                return value;
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public T previous() {
                index--;
                return value;
            }

            @Override
            public int nextIndex() {
                return index + 1;
            }

            @Override
            public int previousIndex() {
                return index - 1;
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
        return Collections.nCopies((int) count, value);
    }
}
