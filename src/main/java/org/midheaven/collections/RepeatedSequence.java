package org.midheaven.collections;

import org.midheaven.lang.Check;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.IntFunction;

final class RepeatedSequence<T> extends AbstractSequence<T> {

    private final Int count;
    private final T element;

    public RepeatedSequence(T element, Int count) {
        this.element = element;
        this.count = count;
    }

    @Override
    public boolean contains(T value) {
        return Objects.equals(this.element, value);
    }
    
    @Override
    public boolean containsAll(Iterable<? extends T> all){
         if (all instanceof RepeatedSequence<?> other) {
             return Objects.equals(this.element, other.element);
         } else if (all instanceof ReadOnlySingleSequence<?> other) {
             return Objects.equals(this.element, other.element);
         } else if (all instanceof SingleDistinctAssortment<?> other) {
             return Objects.equals(this.element, other.element);
         }
         
         return super.containsAll(all);
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
            return Maybe.of(element);
        }
        return Maybe.none();
    }

    @Override
    public Maybe<T> getAt(Int index) {
        if (index.isGreaterThanOrEqualTo(0) && index.isLessThan(count)){
            return Maybe.of(element);
        }
        return Maybe.none();
    }


    @Override
    public Int indexOf(Object o) {
        return element.equals(o) ? Int.ZERO : Int.NEGATIVE_ONE;
    }

    @Override
    public Int lastIndexOf(Object o) {
        return element.equals(o) ? count.minus(1)  : Int.NEGATIVE_ONE;
    }

    @Override
    public Maybe<T> first() {
        return Maybe.of(element);
    }

    @Override
    public Maybe<T> last() {
        return Maybe.of(element);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new RepeatedSequence<>(element, Int.of(toIndex - fromIndex));
    }

    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return new RepeatedSequence<>(element, toIndex.minus(fromIndex));
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
            final IntAccumulator index = new IntAccumulator(Int.NEGATIVE_ONE);
            @Override
            public boolean hasNext() {
                return index.isLessThan(count);
            }

            @Override
            public T next() {
                index.increment();
                return element;
            }

            @Override
            public boolean hasPrevious() {
                return index.isPositive();
            }

            @Override
            public T previous() {
                index.decrement();
                return element;
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
    public List<T> toCollection() {
        return Collections.nCopies(count.toInt(), element);
    }
    
    @Override
    public Object[] toArray() {
        var array = new Object[count.toInt()];
        Arrays.fill(array, element);
        return array;
    }
    
    @Override
    public T[] toArray(T[] candidate) {
        Check.argumentIsNotNull(candidate, "candidate");
        T[] result = EnumerableSupport.correctLengthArray(candidate, count::toInt);
        
        Arrays.fill(result, element);
        return result;
    }
    
    @Override
    public T[] toArray(IntFunction<T[]> generator) {
        Check.argumentIsNotNull(generator, "generator");
        T[] result = generator.apply(count.toInt());
        
        Arrays.fill(result, element);
        return result;
    }
}
