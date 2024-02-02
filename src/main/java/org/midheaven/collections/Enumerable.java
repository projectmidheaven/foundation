package org.midheaven.collections;

import org.midheaven.math.Arithmetic;
import org.midheaven.math.ArithmeticalEnumerable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.*;

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

    Enumerator<T> enumerator();

    @Override
    default Iterator<T> iterator() {
        return this.enumerator().toIterator();
    }

    default Optional<T> first() {
        var enumerator = enumerator();

        if (enumerator.length() instanceof Length.Finite finite && finite.count() == 0){
            return Optional.empty();
        }
        var object = new Object[1];

        while(object[0] == null && enumerator.tryNext(it -> object[0] = it));

        return Optional.ofNullable((T)object[0]);
    }

    default long count() {
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Finite finite){
            return finite.count();
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be counted");
        }

        AtomicLong counter = new AtomicLong(0);

        while(enumerator.tryNext(it -> counter.incrementAndGet()));

        return counter.get();
    }

    default boolean isEmpty(){
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Finite finite){
            return finite.count() == 0;
        }

        return !enumerator().tryNext(it -> {});
    }

    default boolean any(){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite){
            return finite.count() > 0;
        }

        return enumerator.tryNext(it -> {});
    }

    default Sequence<T> toSequence() {
        var enumerator = enumerator();
        var length = enumerator.length();
        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }

        List<T> list;
        if (length instanceof Length.Finite finite){
            if (finite.count() == 0){
                return ImmutableEmptySequence.instance();
            }
            list = new ArrayList<T>((int)finite.count());
        } else {
            list = new LinkedList<>();
        }

        while(enumerator.tryNext(it -> list.add(it)));

        return new ImmutableSequenceListWrapper<>(list);
    }

    default <S extends EditableSequence<T>> S toSequence(Supplier<S> supplier) {
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }

        var sequence = supplier.get();

        if (length instanceof Length.Finite finite && finite.count() == 0){
            return sequence;
        }

        if (sequence instanceof ResizableSequence resizableSequence){
            while(enumerator.tryNext(it -> resizableSequence.add(it)));
        } else {
            AtomicInteger index = new AtomicInteger(0);
            while(enumerator.tryNext(it -> sequence.setAt(index.getAndIncrement(), it)));
        }

        return sequence;
    }

    default boolean anyMatch(Predicate<T> predicate){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count() == 0){
            return false;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be match for all elements");
        }
        var flag = new AtomicBoolean(false);

        while(!flag.get() && enumerator.tryNext(it -> flag.set(predicate.test(it))));

        return flag.get();
    }

    default boolean allMatch(Predicate<T> predicate){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count() == 0){
            return true;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be match for all elements");
        }
        var flag = new AtomicBoolean(true);

        while(flag.get() && enumerator.tryNext(it -> flag.set(predicate.test(it))));

        return flag.get();
    }

    default <A> A reduce (A seed, BiFunction<A,T,A> accumulator){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Finite finite && finite.count() == 0){
            return seed;
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be reduced");
        }
        var object = new Object[]{seed};

        while(enumerator.tryNext(it -> object[0] = accumulator.apply((A)object[0], it)));

        return (A)object[0];
    }

    default Optional<T> reduce (BiFunction<T,T,T> accumulator){
        var enumerator = enumerator();
        var length = enumerator.length();

        if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be reduced");
        }

        var object = new Object[]{null};
        if (enumerator.tryNext(it -> object[0] = it)){
            while(enumerator.tryNext(it -> object[0] = accumulator.apply((T)object[0], it))){};
        }

        return Optional.ofNullable((T)object[0]);
    }

    default Optional<T> minimum(Comparator<T> comparator){
        return reduce((a,b) -> comparator.compare(a,b) <= 0 ? a : b);
    }

    default Optional<T> maximum(Comparator<T> comparator){
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

}
