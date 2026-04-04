package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

final class TransformDistinctView<O, T> extends AbstractDistinctAssortment<T>{

    final DistinctAssortment<O> original;
    final Function<O, T> transformation;

    TransformDistinctView(DistinctAssortment<O> original, Function<O, T> transformation){
        this.original = original;
        this.transformation = transformation;
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
        return Enumerator.fromIterator(this.iterator(), this.count());
    }

    public <R> DistinctAssortment<R> map(Function<T, R> transform) {
        return new TransformDistinctView<>(original, transformation.andThen(transform));
    }

    @Override
    public Maybe<T> first() {
        return original.first().map(transformation::apply);
    }
    
    @Override
    public Iterator<T> iterator() {
        return new TransformIterator<>(original.iterator(), transformation);
    }
    
    
    @Override
    public boolean contains(T candidate) {
        return original.anyMatch(o -> transformation.apply(o).equals(candidate));
    }
    
    @Override
    public Set<T> toCollection() {
        return original.toCollection().stream().map(transformation).collect(Collectors.toUnmodifiableSet());
    }

}

