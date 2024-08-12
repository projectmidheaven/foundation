package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.List;

class EditableSequenceListWrapper<T>  extends ImmutableSequenceListWrapper<T> implements EditableSequence<T> {

	EditableSequenceListWrapper(List<T> original){
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
