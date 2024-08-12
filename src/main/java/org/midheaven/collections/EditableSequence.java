package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

public interface EditableSequence<T> extends Sequence<T> {

	default Maybe<T> setAt(int index, T element){
		return this.setAt(Int.of(index), element);
	}

	Maybe<T> setAt(Int index, T element);

	default Maybe<T> setFirst(T element) {
		return setAt(0, element);
	}

	default Maybe<T> setLast(T element) {
		return setAt(this.count().toInt() - 1, element);
	}

	EditableSequence<T> subSequence(int fromIndex, int toIndex);

	EditableSequence<T> reversed();

	default EditableSequence<T> toSequence() {
		return this;
	}


}
