package org.midheaven.collections;

import org.midheaven.lang.ValueClass;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Defines the contract for Traversable.
 */
public interface Traversable<T> extends Iterable<T>, AutoCloseable{

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    static <S> Traversable<S> empty(){
        return new Traversable<>() {
            @Override
            public void close() {
                //no-op
            }
            
            @Override
            public Iterator<S> iterator() {
                return Collections.emptyIterator();
            }
            
            @Override
            public Traversable<S> concat(Traversable<S> other){
                return other;
            }
            
        };
    }
    
    /**
     * Creates an instance from the provided source.
     * @param iterable the iterable value
     * @return the result of from
     */
    static <S> Traversable<S> from(Iterable<S> iterable){
        return from(Enumerable.from(iterable));
    }
    
    /**
     * Creates an instance from the provided source.
     * @param first the first value
     * @param others the others value
     * @return the result of from
     */
    @SafeVarargs
    /**
     * Creates an instance from the provided source.
     * @param first the first value
     * @param others the others value
     * @return the result of from
     */
    static <S> Traversable<S> from(S first, S ... others){
        if (others.length == 0){
            return new EnumerableTraversable<>(Enumerable.single(first));
        }
        return new EnumerableTraversable<>(Enumerable.single(first).concat(new ArrayWrapper<>(others)));
    }
    
    /**
     * Creates an instance from the provided source.
     * @param enumerable the enumerable value
     * @return the result of from
     */
    static <S> Traversable<S> from(Enumerable<S> enumerable){
        return new EnumerableTraversable<>(enumerable);
    }

    /**
     * Creates an instance from the provided source.
     * @param stream the stream value
     * @return the result of from
     */
    static <S> Traversable<S> from(Stream<S> stream){
        return new StreamTraversable<>(stream);
    }

    /**
     * Performs map.
     * @param mapper the mapper value
     * @return the result of map
     */
    default <R> Traversable<R> map(Function<T, R> mapper){
        return new MappedTraversable<>(this, mapper);
    }
    
    /**
     * Performs flatMap.
     * @param mapper the mapper value
     * @return the result of flatMap
     */
    default <R> Traversable<R> flatMap(Function<T, Traversable<R>> mapper){
        return new FlatMappedTraversable<>(this, mapper);
    }

    /**
     * Performs concat.
     * @param other the other value
     * @return the result of concat
     */
    default Traversable<T> concat(Traversable<T> other){
        return new ConcatTraversable<>(this, other);
    }

    /**
     * Performs zip.
     * @param other the other value
     * @param zipper the zipper value
     * @return the result of zip
     */
    default <O, R> Traversable<R> zip(Traversable<O> other, BiFunction<T, O, R> zipper){
        return new ZipTraversable<>(this, other, zipper);
    }
    
    /**
     * Performs collect.
     * @param collector the collector value
     * @return the result of collect
     */
    default <R,A> R collect(Collector<T, A, R> collector){
        A aa = collector.supplier().get();
        var f = collector.accumulator();
        for (T t : this) {
            f.accept(aa, t);
        }
        return collector.finisher().apply(aa);
    }
    
    /**
     * Performs close.
     */
    @Override
    /**
     * Performs close.
     */
    void close();
}

@ValueClass
final class MappedTraversable<T, O> implements Traversable<T> {

    private final Function<O, T> mapper;
    private final Traversable<O> original;

    MappedTraversable(Traversable<O> original, Function<O,T> mapper){
        this.original = original;
        this.mapper = mapper;
    }

    @Override
    public <R> Traversable<R> map(Function<T, R> mapper) {
        return new MappedTraversable<>(this.original, this.mapper.andThen(mapper));
    }

    @Override
    public void close() {
        original.close();
    }

    @Override
    public Iterator<T> iterator() {
        return new TransformIterator<>(this.original.iterator(), mapper);
    }
}

final class EnumerableTraversable<T> implements Traversable<T> {

    final Iterator<T> iterator;
    private boolean closed = false;
    private boolean consumed = false;

    EnumerableTraversable(Enumerable<T> enumerable) {
        this.iterator = enumerable.iterator();
    }
    
    @Override
    public void close() {
        closed = true;
    }

    @Override
    public Iterator<T> iterator() {
        if (closed){
            throw new IllegalStateException("Traversable was already closed");
        }
        if (consumed) {
            throw new IllegalStateException("Traversable was already been consumed");
        }
        consumed = true;
        return iterator;
    }
}

@ValueClass
final class StreamTraversable<T> implements Traversable<T> {

    private final Stream<T> stream;

    StreamTraversable(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public <R> Traversable<R> map(Function<T, R> mapper) {
        return new StreamTraversable<>(stream.map(mapper));
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }
}
