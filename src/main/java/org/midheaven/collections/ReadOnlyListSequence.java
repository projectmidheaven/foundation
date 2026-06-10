package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;

class ReadOnlyListSequence<T> extends AbstractSequence<T> {

    protected final List original;

    ReadOnlyListSequence(List<? extends T> original){
        this.original = original;
    }
    
    @Override
    public ListIterator<T> iterator() {
        return original.listIterator();
    }
    
    public final void forEach(Consumer<? super T> action) {
        original.forEach(action);
    }
    
    public final boolean isEmpty() {
        return original.isEmpty();
    }
    
    public final boolean contains(Object o) {
        return original.contains(o);
    }
    
    public final boolean add(T e) {
        return original.add(e);
    }
    
    public Object[] toArray(){
        return EnumerableSupport.toArray(this, original::size);
    }
    
    public T[] toArray(T[] templateArray){
        return EnumerableSupport.toArray(this, original::size, templateArray);
    }
    
    public T[] toArray(IntFunction<T[]> generator){
        return EnumerableSupport.toArray(this, original::size, generator);
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(iterator(), count());
    }
    
    @Override
    public final Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return subSequence(fromIndex.toInt(), toIndex.toInt());
    }
    
    public Int count() {
        return Int.of(original.size());
    }
    
    
    public final Spliterator<T> spliterator() {
        return original.spliterator();
    }

    public final Int indexOf(Object o) {
        return Int.of(original.indexOf(o));
    }

    public final Int lastIndexOf(Object o) {
        return Int.of(original.lastIndexOf(o));
    }

    @Override
    public final Maybe<T> getAt(int index) {
        try {
            return Maybe.of((T) original.get(index));
        } catch (IndexOutOfBoundsException e){
            return Maybe.none();
        }
    }

    @Override
    public final Maybe<T> getAt(Int index) {
        try {
            return Maybe.of((T)original.get(index.toInt()));
        } catch (ArithmeticException | IndexOutOfBoundsException e){
            return Maybe.none();
        }
    }

    @Override
    public final Maybe<T> first() {
        return Maybe.of((T)original.getFirst());
    }

    @Override
    public final Maybe<T> last() {
        return Maybe.of((T)original.getLast());
    }

  
    @Override
    public final ListIterator<T> reverseIterator() {
        return original.listIterator(original.size());
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new ReadOnlyListSequence<>(original.subList(fromIndex, toIndex));
    }

}
