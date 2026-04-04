package org.midheaven.collections;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Defines the contract for resizable {@link DistinctAssortment}
 * @param <T> type of the elements in the ResizableDistinctAssortment
 */
public interface ResizableDistinctAssortment<T> extends DistinctAssortment<T> {

	/**
	 * Performs add.
	 * @param e the e value
	 * @return the result of add
	 */
	boolean add(T e);

	/**
	 * Performs remove.
	 * @param o the o value
	 * @return the result of remove
	 */
	boolean remove(Object o);

	/**
	 * Performs add All.
	 * @param c the c value
	 * @return the result of addAll
	 */
	boolean addAll(Iterable<? extends T> c);

	/**
	 * Performs remove All.
	 * @param c the c value
	 * @return the result of removeAll
	 */
	boolean removeAll(Iterable<?> c);

	/**
	 * Performs retainAll.
	 * @param c the c value
	 * @return the result of retainAll
	 */
	boolean retainAll(Iterable<?> c);

	/**
	 * Performs clear.
	 */
	void clear();

	/**
	 * Performs remove If.
	 * @param filter the filter value
	 * @return the result of removeIf
	 */
	boolean removeIf(Predicate<? super T> filter);

	/**
	 * Returns to Collection.
	 * @return the result of toCollection
	 */
	Set<T> toCollection();
	
}
