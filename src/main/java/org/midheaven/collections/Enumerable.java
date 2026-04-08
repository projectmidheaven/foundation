package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Arithmetic;
import org.midheaven.math.ArithmeticalEnumerable;
import org.midheaven.math.Int;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * An {@code Iterable} with fluent stream like transformation methods
 * @param <T> type of element in the Enumerable
 */
public interface Enumerable<T> extends Iterable<T> {

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    static <X> Enumerable<X> empty() {
        return new EmptyEnumerable<>();
    }

    /**
     * Performs generate.
     * @param supplier the supplier value
     * @return the result of generate
     */
    static <X> Enumerable<X> generate(Supplier<X> supplier) {
        return new GeneratorEnumerable<>(new GenerationPipe<>(supplier));
    }

    /**
     * Performs iterate.
     * @param seed the seed value
     * @param successor the successor value
     * @return the result of iterate
     */
    static <X> Enumerable<X> iterate(X seed , Function<X, X> successor) {
        return new GeneratorEnumerable<>(new IteratePipe<>(seed, successor));
    }

    /**
     * Creates an instance from the provided source.
     * @param iterable the iterable value
     * @return the result of from
     */
    static <X> Enumerable<X> from(Iterable<X> iterable) {
        return new ImmutableIterableWrapper<>(iterable);
    }

    /**
     * Performs single.
     * @param value the value value
     * @return the result of single
     */
    static <X> Enumerable<X> single(X value) {
        return () -> Enumerator.single(value);
    }

    /**
     * Creates an instance from the provided source.
     * @param optional the optional value
     * @return the result of from
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    /**
     * Creates an instance from the provided source.
     * @param optional the optional value
     * @return the result of from
     */
    static <X> Enumerable<X> from(Optional<X> optional) {
        return optional.map(Enumerable::single).orElseGet(Enumerable::empty);
    }

    /**
     * Returns an enumerator over the elements.
     * @return the result of enumerator
     */
    Enumerator<T> enumerator();

    /**
     * Returns an iterator over the elements.
     * @return the result of iterator
     */
    @Override
    /**
     * Returns an iterator over the elements.
     * @return the result of iterator
     */
    default Iterator<T> iterator() {
        return this.enumerator().toIterator();
    }

    /**
     * Performs first.
     * @return the result of first
     */
    default Maybe<T> first() {
        var enumerator = enumerator();

        if (enumerator.length() instanceof Length.Finite finite && finite.count().isZero()){
            return Maybe.none();
        }

        if (enumerator.moveNext()){
            return Maybe.of(enumerator.current());
        }

        return Maybe.none();
    }
    
    /**
     * Best effort to count the elements.
     * If the Enumerable is Infinite, an {link IllegalStateException} will be thrown
     * @return the number of elements in the Enumerable
     */
    default Int count() {
        var enumerator = enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be counted");
        
        if (length instanceof Length.Finite finite){
            return finite.count();
        }

        return this.map(it -> Int.ONE).with(Int.arithmetic()).sum();
    }

    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    default boolean isEmpty(){
        return !enumerator().moveNext();
    }

    /**
     * Performs any.
     * @return the result of any
     */
    default boolean any(){;
        return enumerator().moveNext();
    }

    /**
     * Returns a {@link Sequence} with the elements in this {@link Enumerable}.
     * The {@link Enumerable} must be finite or an exception will be thrown
     * @return the result of toSequence
     * @throws IllegalStateException if the {@link Enumerable} is infinite
     */
    default Sequence<T> toSequence() throws InfiniteEnumerableException {
        var enumerator = enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be copied into a sequence");
        
        List<T> list;
        if (length instanceof Length.Finite finite){
            if (finite.count().isZero()){
                return EmptySequence.instance();
            }
            list = new ArrayList<T>((int)finite.count().toLong());
        } else {
            list = new LinkedList<>();
        }

        while(enumerator.moveNext()){
            list.add(enumerator.current());
        }

        return new ReadOnlyListSequence<>(list);
    }
    
    
    /**
     * Returns to Sequence.
     * @param supplier the supplier value
     * @return the result of toSequence
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default <S extends EditableSequence<T>> S toSequence(Supplier<S> supplier) throws InfiniteEnumerableException {
        var enumerator = enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be copied into a sequence");
        
        var sequence = supplier.get();
        
        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return sequence;
        }
        
        if (sequence instanceof ResizableSequence resizableSequence){
            while(enumerator.moveNext()){
                resizableSequence.add(enumerator.current());
            }
        } else {
            var index = 0;
            while(enumerator.moveNext()){
                sequence.setAt(index, enumerator.current());
                index++;
            }
        }
        
        return sequence;
    }
    
    /**
     * Returns to Distinct Assortment.
     * @return the result of toDistinctAssortment
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default DistinctAssortment<T> toDistinctAssortment() throws InfiniteEnumerableException{
        var enumerator = enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be copied into a distinct assortment");
        
        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return EmptyDistinctAssortment.instance();
        }
        
        return AssortmentSupport.<T, HashSet<T>, DistinctAssortment<T>>from(
            this,
            HashSet::new,
            ResizableSetAssortment::new
        );
    }

    /**
     * Creates an array on {@link Object} and copies all elements in this to that array.
     * This operation is only possible if the {@code Enumerable} is not infinite and its length is less than
     * {@code Integer.MAX_VALUE}.
     *
     * @return an array containing all elements in {@code this}
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default Object[] toArray() throws InfiniteEnumerableException{
       return EnumerableSupport.toArray(this, () -> EnumerableSupport.resolveArrayLength(this));
    }
    
    /**
     * Returns a typed array containing all elements in @{code this}.
     * This operation is only possible if the {@code Enumerable} is not infinite and its length is less than
     * {@code Integer.MAX_VALUE}.
     *
     * If the given array is of the same size, or larger, as the enumerable, it is filled with the values in @{code this} and returned.
     * Otherwise, a new array of the correct size if create from it, with the values in @{code this} copied and returned.
     *
     * @param templateArray the template array
     * @return an array containing all elements in @{code this}
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default T[] toArray(T[] templateArray) throws InfiniteEnumerableException{
        return EnumerableSupport.toArray(this,  () -> EnumerableSupport.resolveArrayLength(this), templateArray);
    }
    
    
    /**
     * Returns a typed array containing all elements in @{code this}.
     * The given generator is called to produce an array of the correct size.
     * Then, it is filled with the values in @{code this} and returned.
     *
     * @param generator the template array
     * @return an array containing all elements in @{code this}
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default T[] toArray(IntFunction<T[]> generator) throws InfiniteEnumerableException {
        return EnumerableSupport.toArray(this,  () -> EnumerableSupport.resolveArrayLength(this), generator);
    }
    
    /**
     * Performs anyMatch.
     * @param predicate the predicate value
     * @return the result of anyMatch
     */
    default boolean anyMatch(Predicate<T> predicate){
        var enumerator = enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be match for all elements");
        
        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return false;
        }

        while(enumerator.moveNext()){
            if (predicate.test(enumerator.current())){
                return true;
            }
        }

        return false;
    }

    /**
     * Performs allMatch.
     * @param predicate the predicate value
     * @return the result of allMatch
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default boolean allMatch(Predicate<T> predicate) throws InfiniteEnumerableException{
        var enumerator = enumerator();
       
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be match for all elements");
        
        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return true;
        }

        while(enumerator.moveNext()){
            if (!predicate.test(enumerator.current())){
                return false;
            }
        }

        return true;
    }

    /**
     * Reduce the {@link Enumerable} to a single value by operating between a seed value and each element.
     *
     * @param seed the seed value
     * @param accumulator the accumulator value
     * @return the result of reduce
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default <A> A reduce (A seed, BiFunction<A,T,A> accumulator) throws InfiniteEnumerableException {
        var enumerator = this.enumerator();
        var length = EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be reduced");
        
        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return seed;
        }
        
        var previous = seed;
        while (enumerator.moveNext()){
            previous = accumulator.apply(previous, enumerator.current());
        }
        return previous;
    }

    /**
     * Reduce the {@link Enumerable} to a single value by operating between each element.
     *
     * @param accumulator the accumulator value
     * @return the result of reduce
     */
    default Maybe<T> reduce (BiFunction<T,T,T> accumulator) throws InfiniteEnumerableException {
        var enumerator = enumerator();
        EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be reduced");
        
        if (enumerator.moveNext()){
            var previous = enumerator.current();
            while (enumerator.moveNext()){
                previous = accumulator.apply(previous, enumerator.current());
            }
            return Maybe.of(previous);
        }

        return Maybe.none();
    }

    /**
     * Performs minimum.
     * @param comparator the comparator value
     * @return the result of minimum
     */
    default Maybe<T> minimum(Comparator<T> comparator){
        return reduce((a,b) -> comparator.compare(a,b) <= 0 ? a : b);
    }

    /**
     * Performs maximum.
     * @param comparator the comparator value
     * @return the result of maximum
     */
    default Maybe<T> maximum(Comparator<T> comparator){
        return reduce((a,b) -> comparator.compare(a,b) >= 0 ? a : b);
    }

    /**
     * Performs with.
     * @param arithmetic the arithmetic value
     * @return the result of with
     */
    default <S> ArithmeticalEnumerable<T, S> with(Arithmetic<T, S> arithmetic){
        return (ArithmeticalEnumerable<T, S>) ((EnumerableExtension) previous -> new ArithmeticalEnumerable<T, S>(arithmetic, previous)).extend(this);
    }

    /**
     * Performs as.
     * @param extension the extension value
     * @return the result of as
     */
    default <E extends Enumerable<T>> E as(EnumerableExtension<T, E> extension){
        return extension.extend(this);
    }
    
    /**
     * Performs concat.
     * @param other the other value
     * @return the result of concat
     */
    default Enumerable<T> concat(Enumerable<T> other) {
        return new ComposedEnumerable<>(this, other);
    }
    
    /**
     * Performs filter.
     * @param predicate the predicate value
     * @return the result of filter
     */
    default Enumerable<T> filter(Predicate<T> predicate){
        return new FilterPipe<>(predicate).applyTo(this);
    }

    /**
     * Performs map.
     * @param transform the transform value
     * @return the result of map
     */
    default <R> Enumerable<R> map(Function<T, R> transform) {
        return new MapPipe<>(transform).applyTo(this);
    }

    /**
     * Performs flatMap.
     * @param transform the transform value
     * @return the result of flatMap
     */
    default  <R> Enumerable<R> flatMap(Function<T, Enumerable<R>> transform){
        return new FlatMapPipe<>(transform).applyTo(this);
    }

    /**
     * Performs peek.
     * @param consumer the consumer value
     * @return the result of peek
     */
    default Enumerable<T> peek(Consumer<T> consumer){
        return map(it -> {
            consumer.accept(it);
            return it;
        });
    }

    /**
     * Performs zip.
     * @param other the other value
     * @param transform the transform value
     * @return the result of zip
     */
    default <S, R> Enumerable<R> zip(Enumerable<S> other, BiFunction<T, S, R> transform){
        return new ZipPipe<>(other, transform).applyTo(this);
    }

    /**
     * Performs limit.
     * @param maxCount the maxCount value
     * @return the result of limit
     */
    default Enumerable<T> limit(long maxCount){
        return new LimitingPipe<T>(maxCount).applyTo(this);
    };
    
    /**
     * Performs skip.
     * @param minCount the minCount value
     * @return the result of skip
     */
    default Enumerable<T> skip(long minCount){
        return new SkipPipe<T>(minCount).applyTo(this);
    };

    /**
     * Performs distinct.
     * @return the result of distinct
     */
    default Enumerable<T> distinct(){
        return new DistintPipe<T>().applyTo(this);
    }

    /**
     * Performs sorted.
     * @param comparator the comparator value
     * @return the result of sorted
     */
    default Enumerable<T> sorted(Comparator<T> comparator){
        return new SortPipe<>(comparator).applyTo(this);
    }

    /**
     * Returns associate.
     * @param keySelector the keySelector value
     * @return the result of associate
     */
    default <K> AssociatedEnumerable<K,T> associate(Function<T, K> keySelector){
        return new PipeAssociatedEnumerable<K, T>(map(it -> Association.Entry.entry(keySelector.apply(it), it)));
    }

    /**
     * Creates an association that groups element in this {@link Enumerable} according to a given key and value selectors.
     *
     * @param keySelector the keySelector value
     * @param valueSelector the valueSelector value
     * @return the result of associate
     */
    default <K,V> AssociatedEnumerable<K,V> associate(Function<T, K> keySelector, Function<T, V> valueSelector){
        return new PipeAssociatedEnumerable<>(map( it -> Association.Entry.entry(keySelector.apply(it), valueSelector.apply(it))));
    }

    /**
     * Creates an association that groups element in this {@link Enumerable} according to a given group selector.
     *
     * @param groupSelector the groupSelector value
     * @return the result of groupBy
     */
    default <K> AssociatedEnumerable<K, Enumerable<T>> groupBy(Function<T, K> groupSelector){
        return new GroupingPipe<>(groupSelector).applyTo(this);
    }

    /**
     * Applies a {@link Collector} to the {@link Enumerable}
     *
     * @param collector the collector value
     * @return the result of collect
     * @throws InfiniteEnumerableException if the {@link Enumerable} is infinite
     */
    default <R,A> R collect(Collector<? super T, A, R> collector) throws InfiniteEnumerableException{
        var enumerator = enumerator();
        EnumerableSupport.resolveNonInfiniteLength(enumerator, "Infinite enumerable cannot be collected");
        A aa = collector.supplier().get();
        var f = collector.accumulator();
        
        while (enumerator.moveNext()){
            f.accept(aa,enumerator.current());
        }
    
        return collector.finisher().apply(aa);
    }
    
    /**
     * Transforms each item from type T to type R.
     * An exception will throw if the item is not of type R
     * @param type
     * @return
     * @param <R>
     */
    default <R> Enumerable<R> cast(Class<R> type){
        return this.map(type::cast);
    }
    
    /**
     * Transforms each item from type T to type R. Only items of type R will be preserved.
     * No exception will be thrown
     *
     * @param type
     * @return
     * @param <R>
     */
    default <R> Enumerable<R> ofType(Class<R> type){
        return this.filter(type::isInstance).map(type::cast);
    }

}
