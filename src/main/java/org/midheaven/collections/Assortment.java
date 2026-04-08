package org.midheaven.collections;

import org.midheaven.lang.Countable;
import org.midheaven.math.Int;

import java.util.Collection;

/***
 * Represent a finite aggregation of elements of a single type.
 * An assortment has no specific properties other than aggregating the elements.
 * There is no operations to obtain the element individually.
 *
 * @param <T> type of element in the Assortment
 */
public interface Assortment<T> extends Countable, Enumerable<T> {

	/**
	 * Tests if the given values is contained in this assortment.
	 * Comparison using {@link Object#equals(Object)} is performed.
	 *
	 * @param candidate the candidate value to test
	 * @return {@code true} if the value is contained, {@code false} otherwise
	 */
	boolean contains(T candidate);
	
	/**
	 * Tests if all the given values are contained in this assortment.
	 * Comparison using {@link Object#equals(Object)} is performed.
	 *
	 * @param all the candidate values to test
	 * @return {@code true} if all the values are contained, {@code false} otherwise
	 */
	default boolean containsAll(Iterable<? extends T> all){
		for (var item : all){
			if (!contains(item)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns an immutable Collection with the items in this Assortment for compatibility with JDK collections.
	 * Further alteration to the assortment do not affect the returned collection
	 */
	Collection<T> toCollection();
	
    /**
     * The number of elements in the {@code Assortment}
     * This overrides {@link Enumerable#count } and an exception will never occur, since {@code Assortment}s always have a finite number of elements.
     * @return the number of elements in the {@code Assortment}
     */
    @Override
    Int count();
    
	/**
	 * Checks whether the {@code Assortment} has no elements
	 * @return {@code true} if it has no elements, {@code false} otherwise
	 */
	@Override
	boolean isEmpty();
	
}
