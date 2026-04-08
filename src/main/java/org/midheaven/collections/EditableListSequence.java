package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.List;

/**
 * Wrapper for Editable Sequence List.
 */
class EditableListSequence<T>  extends ReadOnlyListSequence<T> implements EditableSequence<T> {

	EditableListSequence(List<T> original){
		super (original);
	}

	@Override
	public Maybe<T> setAt(Int index, T element) {
		if (index.isNegative() || index.isGreaterThan(this.count().minus(1))){
			throw new IndexOutOfBoundsException();
		}
		return Maybe.of(original.set(index.toInt(), element));
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		return new EditableListSequence<T>(original.subList(fromIndex, toIndex));
	}

	public EditableSequence<T> reversed() {
		return new EditableListSequence<T>(original.reversed());
	}
	
}
