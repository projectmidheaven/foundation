package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Arithmetic;
import org.midheaven.math.ArithmeticalEnumerable;
import org.midheaven.math.Int;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

final class UnmodifiableSequence<T> extends AbstractSequence<T> {
    
    private final Sequence<T> original;
    
    public UnmodifiableSequence(Sequence<T> original) {
        this.original = original;
    }
    
    @Override
    public boolean containsAll(Iterable<? extends T> all) {
        return original.containsAll(all);
    }
    
    @Override
    public Int count() {
        return original.count();
    }
    
    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }
    
    @Override
    public Enumerator<T> enumerator() {
        return original.enumerator();
    }
    
    @Override
    public boolean any() {
        return original.any();
    }
    
    @Override
    public <S extends EditableSequence<T>> S toSequence(Supplier<S> supplier) throws InfiniteEnumerableException {
        return original.toSequence(supplier);
    }
    
    @Override
    public DistinctAssortment<T> toDistinctAssortment() throws InfiniteEnumerableException {
        return original.toDistinctAssortment();
    }
    
    @Override
    public Object[] toArray() throws InfiniteEnumerableException {
        return original.toArray();
    }
    
    @Override
    public T[] toArray(T[] templateArray) throws InfiniteEnumerableException {
        return original.toArray(templateArray);
    }
    
    @Override
    public T[] toArray(IntFunction<T[]> generator) throws InfiniteEnumerableException {
        return original.toArray(generator);
    }
    
    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return original.anyMatch(predicate);
    }
    
    @Override
    public boolean allMatch(Predicate<T> predicate) {
        return original.allMatch(predicate);
    }
    
    @Override
    public <A> A reduce(A seed, BiFunction<A, T, A> accumulator) {
        return original.reduce(seed, accumulator);
    }
    
    @Override
    public Maybe<T> reduce(BiFunction<T, T, T> accumulator) {
        return original.reduce(accumulator);
    }
    
    @Override
    public Maybe<T> minimum(Comparator<T> comparator) {
        return original.minimum(comparator);
    }
    
    @Override
    public Maybe<T> maximum(Comparator<T> comparator) {
        return original.maximum(comparator);
    }
    
    @Override
    public <S> ArithmeticalEnumerable<T, S> with(Arithmetic<T, S> arithmetic) {
        return original.with(arithmetic);
    }
    
    @Override
    public <E extends Enumerable<T>> E as(EnumerableExtension<T, E> extension) {
        return original.as(extension);
    }
    
    @Override
    public Enumerable<T> concat(Enumerable<T> other) {
        return original.concat(other);
    }
    
    @Override
    public <R> Enumerable<R> flatMap(Function<T, Enumerable<R>> transform) {
        return original.flatMap(transform);
    }
    
    @Override
    public Enumerable<T> peek(Consumer<T> consumer) {
        return original.peek(consumer);
    }
    
    @Override
    public <S, R> Enumerable<R> zip(Enumerable<S> other, BiFunction<T, S, R> transform) {
        return original.zip(other, transform);
    }
    
    @Override
    public Enumerable<T> limit(long maxCount) {
        return original.limit(maxCount);
    }
    
    @Override
    public Enumerable<T> skip(long minCount) {
        return original.skip(minCount);
    }
    
    @Override
    public Enumerable<T> distinct() {
        return original.distinct();
    }
    
    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return original.collect(collector);
    }
    
    @Override
    public void forEach(Consumer<? super T> action) {
        original.forEach(action);
    }
    
    @Override
    public Spliterator<T> spliterator() {
        return original.spliterator();
    }
    
    @Override
    public Sequence<T> toSequence() {
        return original.toSequence();
    }
    
    @Override
    public Maybe<T> getAt(int index) {
        return original.getAt(index);
    }
    
    @Override
    public Maybe<T> getAt(Int index) {
        return original.getAt(index);
    }
    
    @Override
    public Int indexOf(Object o) {
        return original.indexOf(o);
    }
    
    @Override
    public Int lastIndexOf(Object o) {
        return original.lastIndexOf(o);
    }
    
    @Override
    public boolean contains(T candidate) {
        return original.contains(candidate);
    }
    
    @Override
    public Maybe<T> first() {
        return original.first();
    }
    
    @Override
    public Maybe<T> last() {
        return original.last();
    }
    
    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return original.subSequence(fromIndex, toIndex);
    }
    
    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return original.subSequence(fromIndex, toIndex);
    }
    
    @Override
    public Sequence<T> reversed() {
        return original.reversed();
    }
    
    @Override
    public Iterator<T> reverseIterator() {
        return original.reverseIterator();
    }
    
    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }
    
    @Override
    public List<T> toCollection() {
        return original.toCollection();
    }
    
    @Override
    public Sequence<T> sorted(Comparator<T> comparator) {
        return original.sorted(comparator);
    }
    
    @Override
    public <R> Sequence<R> map(Function<T, R> transform) {
        return original.map(transform);
    }
    
    @Override
    public Sequence<T> filter(Predicate<T> predicate) {
        return original.filter(predicate);
    }
    
    @Override
    public <R> Sequence<R> cast(Class<R> type) {
        return original.cast(type);
    }
    
    @Override
    public <R> Sequence<R> ofType(Class<R> type) {
        return original.ofType(type);
    }
    

}
