package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

/**
 * Defines the contract for and editable {@link Sequence}.
 * Editable {@link Sequence}s are {@link Sequence}s with fixed length that allow
 * for replacement of the element at each index, nut not adding new elements
 * A classic example is {@link Array}.
 * @param <T> type of element in the EditableSequence
 */
public interface EditableSequence<T> extends Sequence<T> {

	/**
	 * Replaces the element at the given index, with the given element
	 * @param index the index to replace
	 * @param element the new element
	 * @return the value previous present at the index
	 */
	default Maybe<T> setAt(int index, T element){
		return this.setAt(Int.of(index), element);
	}
	
	/**
	 * Replaces the element at the given index, with the given element
	 * @param index the index to replace
	 * @param element the new element
	 * @return the value previous present at the index
	 */
	Maybe<T> setAt(Int index, T element);
	
	/**
	 * Replaces the element at index 0, with the given element
	 * @param element the new element
	 * @return the value previous present at the index
	 */
	default Maybe<T> setFirst(T element) {
		return setAt(0, element);
	}
	
	/**
	 * Replaces the element at the last index, with the given element
	 * @param element the new element
	 * @return the value previous present at the index
	 */
	default Maybe<T> setLast(T element) {
		return setAt(this.count().toInt() - 1, element);
	}

	/**
	 * Creates a view of this Sequence between the given indexes
	 * @param fromIndex the start index
	 * @param toIndex the end index
	 * @return the resulting sub subSequence
	 */
	EditableSequence<T> subSequence(int fromIndex, int toIndex);

	/**
	 * Creates a Sequence view that is the reverse of this one.
	 * @return the resulting reverse subSequence
	 */
	EditableSequence<T> reversed();
	
	/**
	 *{@inheritDoc}
	 */
	default EditableSequence<T> toSequence() {
		return this;
	}
}
