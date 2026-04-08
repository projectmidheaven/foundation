package org.midheaven.collections;

import org.midheaven.lang.Check;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.IntFunction;

class ReadOnlyArrayList<T> extends AbstractSequence<T> {
    
    final T[] array;
    
    ReadOnlyArrayList(T a, T b, T... others) {
        var values = Arrays.copyOf(others, others.length + 2);
        System.arraycopy(others, 0, values, 2, others.length);
        values[0] = a;
        values[1] = b;
        this.array = values; // non-empty array
    }
    
    ReadOnlyArrayList(T[] array) {
        this.array = array; // non-empty array
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<T> toCollection() {
        var list = new ArrayList<T>(this.array.length);
        for (var element : this.array) {
            list.add(element);
        }
        return Collections.unmodifiableList(list);
    }
    
    @Override
    public final Object[] toArray() {
        return Arrays.copyOf(this.array, this.array.length);
    }
    
    @Override
    public final T[] toArray(T[] candidate) {
        Check.argumentIsNotNull(candidate, "candidate");
        T[] result = EnumerableSupport.correctLengthArray(candidate, () ->  this.array.length );
      
        System.arraycopy(this.array, 0, result, 0, this.array.length);
        return result;
    }
    
    @Override
    public final T[] toArray(IntFunction<T[]> generator) {
        Check.argumentIsNotNull(generator, "generator");
        T[] result = generator.apply(array.length);
        
        System.arraycopy(this.array, 0, result, 0, this.array.length);
        return result;
    }
    
    @Override
    public Maybe<T> getAt(int index) {
        try {
            return Maybe.of(array[index]);
        } catch (IndexOutOfBoundsException e) {
            return Maybe.none();
        }
    }
    
    @Override
    public Maybe<T> getAt(Int index) {
        try {
            return Maybe.of(array[index.toInt()]);
        } catch (ArithmeticException | IndexOutOfBoundsException e) {
            return Maybe.none();
        }
    }
    
    @Override
    public Int indexOf(Object candidate) {
        for (var index = 0; index < array.length; index++) {
            var element = array[index];
            if (Objects.equals(element, candidate)) {
                return Int.of(index);
            }
        }
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public Int lastIndexOf(Object candidate) {
        for (var index = array.length - 1; index >= 0; index--) {
            var element = array[index];
            if (element == null) {
                if (candidate == null) {
                    return Int.of(index);
                }
            } else if (element.equals(candidate)) {
                return Int.of(index);
            }
        }
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public Maybe<T> first() {
        return Maybe.of(array[0]);
    }
    
    @Override
    public Maybe<T> last() {
        return Maybe.of(array[array.length - 1]);
    }
    
    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex <= toIndex && toIndex >= 0 && toIndex < array.length) {
            return new ReadOnlySubsequenceView<>(this, Int.of(fromIndex), Int.of(toIndex));
        }
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        if (fromIndex.isGreaterThanOrEqualTo(0) && fromIndex.isLessThanOrEqualTo(toIndex) && toIndex.isGreaterThanOrEqualTo(0) && toIndex.isLessThan(array.length)) {
            return new ReadOnlySubsequenceView<>(this, fromIndex, toIndex);
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
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(this.iterator(), Int.of(array.length));
    }
    
    @Override
    public Int count() {
        return Int.of(array.length);
    }
    
    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }
}
