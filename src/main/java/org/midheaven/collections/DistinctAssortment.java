package org.midheaven.collections;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Defines the contract for and {@link Assortment} where elements are distinct by {@link Object#equals(Object)}.
 * @param <T> type of element in the DistinctAssortment
 */
public interface DistinctAssortment<T> extends Assortment<T> {

    /**
     * Creates a new builder.
     * @return the result of builder
     */
    static DistinctAssortmentBuilder builder(){
        return DistinctAssortmentBuilder.INSTANCE;
    }

    /**
     * Returns to Collection.
     * @return the result of toCollection
     */
    default Set<T> toCollection(){
        return collect(Collectors.toUnmodifiableSet());
    }
    
    /**
     * Performs map.
     * @param transform the transform value
     * @return the result of map
     */
    @Override
    /**
     * Performs map.
     * @param transform the transform value
     * @return the result of map
     */
    default <R> DistinctAssortment<R> map(Function<T, R> transform) {
        return new TransformDistinctView<>(this, transform);
    }
    
    /**
     * Performs filter.
     * @param predicate the predicate value
     * @return the result of filter
     */
    @Override
    /**
     * Performs filter.
     * @param predicate the predicate value
     * @return the result of filter
     */
    default DistinctAssortment<T> filter(Predicate<T> predicate){
        return new FilterDistinctAssortmentView<>(this, predicate);
    }
    
    /**
     * Performs sorted.
     * @param comparator the comparator value
     * @return the result of sorted
     */
    default DistinctAssortment<T> sorted(Comparator<T> comparator){
        return new DistinctAssortmentEnumerableView<>(new SortPipe<>(comparator).applyTo(this));
    }
    
    /**
     * Performs cast.
     * @param type the type value
     * @return the result of cast
     */
    default <R> DistinctAssortment<R> cast(Class<R> type){
        return this.map(type::cast);
    }
    
    /**
     * Returns of Type.
     * @param type the type value
     * @return the result of ofType
     */
    default <R> DistinctAssortment<R> ofType(Class<R> type){
        return this.filter(type::isInstance).map(type::cast);
    }
}
