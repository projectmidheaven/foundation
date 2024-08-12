package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Arithmetic;
import org.midheaven.math.ArithmeticalEnumerable;
import org.midheaven.math.Int;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

public interface Enumerable<T> extends Iterable<T> {

    static <X> Enumerable<X> empty() {
        return new EmptyEnumerable<>();
    }

    static <X> Enumerable<X> generate(Supplier<X> supplier) {
        return new GeneratorEnumerable<>(new GenerationPipe<>(supplier));
    }

    static <X> Enumerable<X> iterate(X seed , Function<X, X> successor) {
        return new GeneratorEnumerable<>(new IteratePipe<>(seed, successor));
    }

    static <X> Enumerable<X> from(Iterable<X> iterable) {
        return new ImmutableIterableWrapper<>(iterable);
    }

    static <X> Enumerable<X> single(X value) {
        return () -> Enumerator.single(value);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <X> Enumerable<X> from(Optional<X> optional) {
        return optional.map(Enumerable::single).orElseGet(Enumerable::empty);
    }

    Enumerator<T> enumerator();

    @Override
    default Iterator<T> iterator() {
        return this.enumerator().toIterator();
    }

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

    default Int count() {
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Finite finite){
            return finite.count();
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be counted");
        }

        return this.map(it -> Int.ONE).with(Int.arithmetic()).sum();
    }

    default boolean isEmpty(){
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Finite finite){
            return finite.count().isZero();
        }

        return !enumerator().moveNext();
    }

    default boolean any(){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite){
            return finite.count().isPositive();
        }

        return enumerator().moveNext();
    }

    default Sequence<T> toSequence() {
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }

        List<T> list;
        if (length instanceof Length.Finite finite){
            if (finite.count().isZero()){
                return ImmutableEmptySequence.instance();
            }
            list = new ArrayList<T>((int)finite.count().toLong());
        } else {
            list = new LinkedList<>();
        }

        while(enumerator.moveNext()){
            list.add(enumerator.current());
        }

        return new ImmutableSequenceListWrapper<>(list);
    }

    default <S extends EditableSequence<T>> S toSequence(Supplier<S> supplier) {
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }

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

    default boolean anyMatch(Predicate<T> predicate){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return false;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be match for all elements");
        }

        while(enumerator.moveNext()){
            if (predicate.test(enumerator.current())){
                return true;
            }
        }

        return false;
    }

    default boolean allMatch(Predicate<T> predicate){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return true;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be match for all elements");
        }

        while(enumerator.moveNext()){
            if (!predicate.test(enumerator.current())){
                return false;
            }
        }

        return true;
    }

    default <A> A reduce (A seed, BiFunction<A,T,A> accumulator){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count().isZero()){
            return seed;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be reduced");
        }

        var previous = seed;
        while (enumerator.moveNext()){
            previous = accumulator.apply(previous, enumerator.current());
        }
        return previous;
    }

    default Maybe<T> reduce (BiFunction<T,T,T> accumulator){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be reduced");
        }

        if (enumerator.moveNext()){
            var previous = enumerator.current();
            while (enumerator.moveNext()){
                previous = accumulator.apply(previous, enumerator.current());
            }
            return Maybe.of(previous);
        }

        return Maybe.none();
    }

    default Maybe<T> minimum(Comparator<T> comparator){
        return reduce((a,b) -> comparator.compare(a,b) <= 0 ? a : b);
    }

    default Maybe<T> maximum(Comparator<T> comparator){
        return reduce((a,b) -> comparator.compare(a,b) >= 0 ? a : b);
    }

    default <S> ArithmeticalEnumerable<T, S> with(Arithmetic<T, S> arithmetic){
        return (ArithmeticalEnumerable<T, S>) ((EnumerableExtension) previous -> new ArithmeticalEnumerable<T, S>(arithmetic, previous)).extend(this);
    }

    default <E extends Enumerable<T>> E as(EnumerableExtension<T, E> extension){
        return extension.extend(this);
    }

    default Enumerable<T> filter(Predicate<T> predicate){
        return new FilterPipe<>(predicate).applyTo(this);
    }

    default <R> Enumerable<R> map(Function<T, R> transform) {
        return new MapPipe<>(transform).applyTo(this);
    }

    default  <R> Enumerable<R> flatMap(Function<T, Enumerable<R>> transform){
        return new FlatMapPipe<>(transform).applyTo(this);
    }

    default Enumerable<T> peek(Consumer<T> consumer){
        return map(it -> {
            consumer.accept(it);
            return it;
        });
    }

    default <S, R> Enumerable<R> zip(Enumerable<S> other, BiFunction<T, S, R> transform){
        return new ZipPipe<>(other, transform).applyTo(this);
    }

    default Enumerable<T> limit(long maxCount){
        return new LimitingPipe<T>(maxCount).applyTo(this);
    };

    default Enumerable<T> distinct(){
        return new DistintPipe<T>().applyTo(this);
    }

    default Enumerable<T> sorted(Comparator<T> comparator){
        return new SortPipe<>(comparator).applyTo(this);
    }

    default <K> AssociatedEnumerable<K,T> associate(Function<T, K> keySelector){
        return new PipeAssociatedEnumerable<K, T>(map(it -> Association.Entry.entry(keySelector.apply(it), it)));
    }

    default <K,V> AssociatedEnumerable<K,V> associate(Function<T, K> keySelector, Function<T, V> valueSelector){
        return new PipeAssociatedEnumerable<>(map( it -> Association.Entry.entry(keySelector.apply(it), valueSelector.apply(it))));
    }

    default <K> AssociatedEnumerable<K, Enumerable<T>> groupBy(Function<T, K> groupSelector){
        return new GroupingPipe<>(groupSelector).applyTo(this);
    }

    default <R,A> R collect(Collector<T, A, R> collector){
        A aa = collector.supplier().get();
        var f = collector.accumulator();
        for (var item : this){
            f.accept(aa,item);
        }
        return collector.finisher().apply(aa);
    }

}
