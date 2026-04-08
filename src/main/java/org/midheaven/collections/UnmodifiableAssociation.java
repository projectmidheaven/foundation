package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Arithmetic;
import org.midheaven.math.ArithmeticalEnumerable;
import org.midheaven.math.Int;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

final class UnmodifiableAssociation<K, V> implements Association<K, V> {
    
    private final Association<K, V> original;
    
    UnmodifiableAssociation(Association<K, V> original) {
        this.original = original;
    }
    
    @Override
    public boolean containsKey(Object key) {
        return original.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return original.containsValue(value);
    }
    
    @Override
    public Association<K, V> union(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        return original.union(other, valueSelector);
    }
    
    @Override
    public Association<K, V> intersection(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        return original.intersection(other, valueSelector);
    }
    
    @Override
    public V computeValueIfAbsent(K key, Function<K, V> computation) {
        return original.computeValueIfAbsent(key, computation);
    }
    
    @Override
    public DistinctAssortment<K> keys() {
        return original.keys();
    }
    
    @Override
    public Assortment<V> values() {
        return original.values();
    }
    
    @Override
    public Maybe<V> getValue(Object key) {
        return original.getValue(key);
    }
    
    @Override
    public Set<Entry<K, V>> toCollection() {
        return original.toCollection();
    }
    
    @Override
    public boolean contains(Entry<K, V> candidate) {
        return original.contains(candidate);
    }
    
    @Override
    public boolean containsAll(Iterable<? extends Entry<K, V>> all) {
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
    public Enumerator<Entry<K, V>> enumerator() {
        return original.enumerator();
    }
    
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return original.iterator();
    }
    
    @Override
    public Maybe<Entry<K, V>> first() {
        return original.first();
    }
    
    @Override
    public boolean any() {
        return original.any();
    }
    
    @Override
    public Sequence<Entry<K, V>> toSequence() {
        return original.toSequence();
    }
    
    @Override
    public <S extends EditableSequence<Entry<K, V>>> S toSequence(Supplier<S> supplier) throws InfiniteEnumerableException {
        return original.toSequence(supplier);
    }
    
    @Override
    public DistinctAssortment<Entry<K, V>> toDistinctAssortment() throws InfiniteEnumerableException {
        return original.toDistinctAssortment();
    }
    
    @Override
    public Object[] toArray() throws InfiniteEnumerableException {
        return original.toArray();
    }
    
    @Override
    public Entry<K, V>[] toArray(Entry<K, V>[] templateArray) throws InfiniteEnumerableException {
        return original.toArray(templateArray);
    }
    
    @Override
    public Entry<K, V>[] toArray(IntFunction<Entry<K, V>[]> generator) throws InfiniteEnumerableException {
        return original.toArray(generator);
    }
    
    @Override
    public boolean anyMatch(Predicate<Entry<K, V>> predicate) {
        return original.anyMatch(predicate);
    }
    
    @Override
    public boolean allMatch(Predicate<Entry<K, V>> predicate) {
        return original.allMatch(predicate);
    }
    
    @Override
    public <A> A reduce(A seed, BiFunction<A, Entry<K, V>, A> accumulator) {
        return original.reduce(seed, accumulator);
    }
    
    @Override
    public Maybe<Entry<K, V>> reduce(BiFunction<Entry<K, V>, Entry<K, V>, Entry<K, V>> accumulator) {
        return original.reduce(accumulator);
    }
    
    @Override
    public Maybe<Entry<K, V>> minimum(Comparator<Entry<K, V>> comparator) {
        return original.minimum(comparator);
    }
    
    @Override
    public Maybe<Entry<K, V>> maximum(Comparator<Entry<K, V>> comparator) {
        return original.maximum(comparator);
    }
    
    @Override
    public <S> ArithmeticalEnumerable<Entry<K, V>, S> with(Arithmetic<Entry<K, V>, S> arithmetic) {
        return original.with(arithmetic);
    }
    
    @Override
    public <E extends Enumerable<Entry<K, V>>> E as(EnumerableExtension<Entry<K, V>, E> extension) {
        return original.as(extension);
    }
    
    @Override
    public Enumerable<Entry<K, V>> concat(Enumerable<Entry<K, V>> other) {
        return original.concat(other);
    }
    
    @Override
    public Enumerable<Entry<K, V>> filter(Predicate<Entry<K, V>> predicate) {
        return original.filter(predicate);
    }
    
    @Override
    public <R> Enumerable<R> map(Function<Entry<K, V>, R> transform) {
        return original.map(transform);
    }
    
    @Override
    public <R> Enumerable<R> flatMap(Function<Entry<K, V>, Enumerable<R>> transform) {
        return original.flatMap(transform);
    }
    
    @Override
    public Enumerable<Entry<K, V>> peek(Consumer<Entry<K, V>> consumer) {
        return original.peek(consumer);
    }
    
    @Override
    public <S, R> Enumerable<R> zip(Enumerable<S> other, BiFunction<Entry<K, V>, S, R> transform) {
        return original.zip(other, transform);
    }
    
    @Override
    public Enumerable<Entry<K, V>> limit(long maxCount) {
        return original.limit(maxCount);
    }
    
    @Override
    public Enumerable<Entry<K, V>> skip(long minCount) {
        return original.skip(minCount);
    }
    
    @Override
    public Enumerable<Entry<K, V>> distinct() {
        return original.distinct();
    }
    
    @Override
    public Enumerable<Entry<K, V>> sorted(Comparator<Entry<K, V>> comparator) {
        return original.sorted(comparator);
    }
    
    @Override
    public <R, A> R collect(Collector<? super Entry<K, V>, A, R> collector) {
        return original.collect(collector);
    }
    
    @Override
    public <R> Enumerable<R> cast(Class<R> type) {
        return original.cast(type);
    }
    
    @Override
    public <R> Enumerable<R> ofType(Class<R> type) {
        return original.ofType(type);
    }
    
    @Override
    public void forEach(Consumer<? super Entry<K, V>> action) {
        original.forEach(action);
    }
    
    @Override
    public Spliterator<Entry<K, V>> spliterator() {
        return original.spliterator();
    }
}
