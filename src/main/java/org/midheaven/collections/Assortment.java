package org.midheaven.collections;

import org.midheaven.lang.Countable;

import java.util.Collection;

/***
 * Represent an aggregation of elements of a single type.
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
	 * @return
	 */
	Collection<T> asCollection();

	@Override
	long count();

	@Override
	boolean isEmpty();
}
