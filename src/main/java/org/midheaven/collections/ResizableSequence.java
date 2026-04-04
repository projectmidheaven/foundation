package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Defines the contract for resizable {@link Sequence}. This a {@link Sequence} where elements can be added and removed.
 * @param <T> type of the elements in the ResizableSequence
 */
public interface ResizableSequence<T> extends EditableSequence<T>  {

	/**
	 * Returns to Sequence.
	 * @return the result of toSequence
	 */
	default ResizableSequence<T> toSequence() {
		return this;
	}

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
	 * Performs add All At.
	 * @param index the index value
	 * @param c the c value
	 * @return the result of addAllAt
	 */
	boolean addAllAt(int index, Iterable<? extends T> c);

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
	 * Performs replaceAll.
	 * @param operator the operator value
	 */
	void replaceAll(UnaryOperator<T> operator);

	/**
	 * Performs sort.
	 * @param c the c value
	 */
	void sort(Comparator<? super T> c);

	/**
	 * Performs clear.
	 */
	void clear();

	/**
	 * Performs remove At.
	 * @param index the index value
	 * @return the result of removeAt
	 */
	Maybe<T> removeAt(int index);

	/**
	 * Performs add At.
	 * @param index the index value
	 * @param element the element value
	 */
	void addAt(int index, T element);

	/**
	 * Performs remove If.
	 * @param filter the filter value
	 * @return the result of removeIf
	 */
	boolean removeIf(Predicate<? super T> filter);

	/**
	 * Performs indexOf.
	 * @param o the o value
	 * @return the result of indexOf
	 */
	Int indexOf(Object o);

	/**
	 * Returns last Index Of.
	 * @param o the o value
	 * @return the result of lastIndexOf
	 */
	Int lastIndexOf(Object o);

	/**
	 * Performs subSequence.
	 * @param fromIndex the fromIndex value
	 * @param toIndex the toIndex value
	 * @return the result of subSequence
	 */
	ResizableSequence<T> subSequence(int fromIndex, int toIndex);

	/**
	 * Performs add First.
	 * @param e the e value
	 */
	void addFirst(T e);

	/**
	 * Performs add Last.
	 * @param e the e value
	 */
	void addLast(T e);

	/**
	 * Performs reversed.
	 * @return the result of reversed
	 */
	ResizableSequence<T> reversed();
	
}
