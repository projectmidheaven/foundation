package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.List;

final class EditableSubsequenceView<T> extends ImmutableSubsequenceView<T> implements EditableSequence<T> {

	EditableSubsequenceView(EditableSequence<T> original, Int fromIndex, Int toIndex){
		super(original, fromIndex, toIndex);
	}
	
	@Override
	public Maybe<T> setAt(Int index, T element) {
		return ((EditableSequence<T>) original).setAt(index.plus(fromIndex), element);
	}

	@Override
	public EditableSequence<T> subSequence(Int fromIndex, Int toIndex) {
		return new EditableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		return subSequence(Int.of(fromIndex), Int.of(toIndex));
	}

	@Override
	public List<T> asCollection() {
		return original.asCollection().subList(fromIndex.toInt(), toIndex.toInt());
	}

	@Override
	public EditableSequence<T> reversed() {
		return new ReversedEditableSequenceView<>(this);
	}
}
