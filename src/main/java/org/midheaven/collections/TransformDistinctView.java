package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

final class TransformDistinctView<O, T> implements DistinctAssortment<T>{

    final DistinctAssortment<O> original;
    final Function<O, T> transformation;

    TransformDistinctView(DistinctAssortment<O> original, Function<O, T> transformation){
        this.original = original;
        this.transformation = transformation;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof DistinctAssortment that
                && AssortmentSupport.equals(this , that);
    }

    @Override
    public int hashCode(){
        return HashCode.of(this.toCollection());
    }

    @Override
    public String toString( ){
        return this.toCollection().toString();
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
    public boolean contains(T other) {
        return original.anyMatch(o -> transformation.apply(o).equals(other));
    }
    
    @Override
    public Set<T> toCollection() {
        return original.toCollection().stream().map(transformation).collect(Collectors.toUnmodifiableSet());
    }

}

