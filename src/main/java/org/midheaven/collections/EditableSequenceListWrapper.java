package org.midheaven.collections;

import java.util.*;

class EditableSequenceListWrapper<T>  extends ImmutableSequenceListWrapper<T> implements EditableSequence<T> {

	EditableSequenceListWrapper(List<T> original){
		super (original);
	}

	@Override
	public Optional<T> setAt(int index, T element) {
		return Optional.ofNullable(original.set(index, element));
	}


	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		return new EditableSequenceListWrapper<T>(original.subList(fromIndex, toIndex));
	}

	public EditableSequence<T> reversed() {
		return new EditableSequenceListWrapper<T>(original.reversed());
	}

	@Override
	public List<T> asCollection() {
		return original;
	}
}
