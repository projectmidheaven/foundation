package org.midheaven.collections;

import java.util.Optional;

public interface EditableSequence<T> extends Sequence<T> {

	Optional<T> setAt(int index, T element);

	default Optional<T> setFirst(T element) {
		return setAt(0, element);
	}

	default Optional<T> setLast(T element) {
		return setAt(this.size() - 1, element);
	}

	EditableSequence<T> subSequence(int fromIndex, int toIndex);

	EditableSequence<T> reversed();

	default EditableSequence<T> toSequence() {
		return this;
	}


}
