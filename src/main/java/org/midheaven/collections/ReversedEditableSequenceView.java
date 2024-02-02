package org.midheaven.collections;

import java.util.List;
import java.util.Optional;

class ReversedEditableSequenceView<T> extends ReversedImmutableSequenceView<T> implements EditableSequence<T>  {

	public ReversedEditableSequenceView(EditableSequence<T> original) {
		super(original);
	}
	
	@Override
	public Optional<T> setAt(int index, T element) {
		return ((EditableSequence<T>)original).setAt(reverseIndex(index), element);
	}

	@Override
	public Optional<T> setFirst(T element) {
		return ((EditableSequence<T>)original).setLast(element);
	}

	@Override
	public Optional<T> setLast(T element) {
		return ((EditableSequence<T>)original).setFirst(element);
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
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
