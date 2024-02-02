package org.midheaven.collections;

import org.midheaven.lang.HashCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

class ImmutableSequenceArrayWrapper<T> implements Sequence<T> {

    final T[] array;

    ImmutableSequenceArrayWrapper(T[] array){
        this.array = array; // non-empty array
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Sequence sequence
               && SequencesSupport.equals(this , sequence);
    }

    @Override
    public int hashCode( ){
        return HashCode.of(array);
    }

    @Override
    public String toString( ){
        return Arrays.toString(array);
    }

    @Override
    public Optional<T> getAt(int index) {
        try {
            return Optional.ofNullable(array[index]);
        } catch (IndexOutOfBoundsException e){
            return Optional.empty();
        }
    }

    @Override
    public int indexOf(Object candidate) {
        for (var index = 0; index < array.length; index++){
            var element = array[index];
            if (element == null) {
                if(candidate == null) {
                    return index;
                }
            } else if(element.equals(candidate)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object candidate) {
        for (var index = array.length - 1; index >=0 ; index--){
            var element = array[index];
            if (element == null) {
                if(candidate == null) {
                    return index;
                }
            } else if(element.equals(candidate)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public Optional<T> first() {
        return Optional.ofNullable(array[0]);
    }

    @Override
    public Optional<T> last() {
        return Optional.ofNullable(array[array.length - 1]);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        if (fromIndex >=0 && fromIndex <= toIndex && toIndex >=0 && toIndex < array.length) {
            return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public Sequence<T> reversed() {
        return new ReversedImmutableSequenceView<>(this);
    }

    @Override
    public ListIterator<T> iterator() {
        return Collections.unmodifiableList(Arrays.asList(this.array)).listIterator();
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return Collections.unmodifiableList(Arrays.asList(this.array)).listIterator(this.array.length);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> asCollection() {
        return Collections.unmodifiableList(Arrays.asList(this.array));
    }

    @Override
    public Enumerator<T> enumerator() {
        return new IteratorEnumeratorAdapter<>(this.iterator(), array.length);
    }

    @Override
    public long count() {
        return array.length;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }
}
