package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.List;

class ReversedEditableSequenceView<T> extends ReversedImmutableSequenceView<T> implements EditableSequence<T>  {

	public ReversedEditableSequenceView(EditableSequence<T> original) {
		super(original);
	}
	
	@Override
	public Maybe<T> setAt(Int index, T element) {
		return ((EditableSequence<T>)original).setAt(reverseIndex(index), element);
	}

	@Override
	public Maybe<T> setFirst(T element) {
		return ((EditableSequence<T>)original).setLast(element);
	}

	@Override
	public Maybe<T> setLast(T element) {
		return ((EditableSequence<T>)original).setFirst(element);
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		return subSequence(Int.of(fromIndex), Int.of(toIndex));
	}

	@Override
	public EditableSequence<T> subSequence(Int fromIndex, Int toIndex) {
		return new EditableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public EditableSequence<T> reversed() {
		return (EditableSequence<T>)original;
	}

	@Override
	public List<T> asCollection() {
		return original.asCollection().reversed();
	}


}
