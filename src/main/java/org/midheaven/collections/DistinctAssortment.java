package org.midheaven.collections;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface DistinctAssortment<T> extends Assortment<T> {

    static DistinctAssortmentBuilder builder(){
        return new DistinctAssortmentBuilder();
    }

    default Set<T> toCollection(){
        return collect(Collectors.toUnmodifiableSet());
    }
    
    @Override
    default <R> DistinctAssortment<R> map(Function<T, R> transform) {
        return new TransformDistinctView<>(this, transform);
    }
    
    @Override
    default DistinctAssortment<T> filter(Predicate<T> predicate){
        return new FilterDistinctAssortmentView<>(this, predicate);
    }
    
    default DistinctAssortment<T> sorted(Comparator<T> comparator){
        return new DistinctAssortmentEnumerableView<>(new SortPipe<>(comparator).applyTo(this));
    }
    
    default <R> DistinctAssortment<R> cast(Class<R> type){
        return this.map(type::cast);
    }
    
    default <R> DistinctAssortment<R> ofType(Class<R> type){
        return this.filter(type::isInstance).map(type::cast);
    }
}
