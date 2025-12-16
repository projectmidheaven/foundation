package org.midheaven.collections;

import org.midheaven.lang.Countable;
import org.midheaven.math.Int;

import java.util.Collection;

/***
 * Represent a finite aggregation of elements of a single type.
 * An assortment has no specific properties other than aggregating the elements.
 * There is no operations to obtain the element individually.
 *
 * @param <T>
 */
public interface Assortment<T> extends Countable, Enumerable<T> {

	boolean contains(T any);

	default boolean containsAll(Iterable<? extends T> all){
		for (var item : all){
			if (!contains(item)){
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Returns a view of this Assortment for compatibility with JDK collections.
	 */
	Collection<T> asCollection();
    
    /**
     * The number of elements in the {@code Assortment}
     * This override {@link Enumerable::count } and an exception will never occur, since {@code Assortment}s have a finite number of elements.
     * @return the number of elements in the {@code Assortment}
     */
    @Override
    Int count();
    
	@Override
	boolean isEmpty();

}
