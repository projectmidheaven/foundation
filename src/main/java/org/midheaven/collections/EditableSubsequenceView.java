package org.midheaven.collections;

import java.util.List;
import java.util.Optional;

final class EditableSubsequenceView<T> extends ImmutableSubsequenceView<T> implements EditableSequence<T> {

	EditableSubsequenceView(EditableSequence<T> original, int fromIndex, int toIndex){
		super(original, fromIndex, toIndex);
	}
	
	@Override
	public Optional<T> setAt(int index, T element) {
		return ((EditableSequence<T>) original).setAt(fromIndex + index, element);
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		return new EditableSubsequenceView<>(this, fromIndex, toIndex);
	}


	@Override
	public List<T> asCollection() {
		return original.asCollection().subList(fromIndex, toIndex);
	}

	@Override
	public EditableSequence<T> reversed() {
		return new ReversedEditableSequenceView<>(this);
	}
}
