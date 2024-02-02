package org.midheaven.collections;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Function;

public interface Sequence<T> extends Assortment<T> {

	/**
	 * Returns a builder for Sequence. The default is an immutable sequence, use {@code editable()} and {@code resizable()}
	 * to build mutable versions.
	 * @return
	 */
	static SequenceBuilder builder(){
		return new SequenceBuilder();
	}

	@Override
	default <R> Sequence<R> map(Function<T, R> transform) {
		return new TransformSequenceView<>(this, transform);
	}

	default Sequence<T> toSequence() {
		return this;
	}

	Optional<T> getAt(int index);

	int indexOf(Object o);

	int lastIndexOf(Object o);

	@Override
	default boolean contains(T any) {
		return indexOf(any) >= 0;
	}


	Optional<T> first();

	Optional<T> last();

	Sequence<T> subSequence(int fromIndex, int toIndex);

	Sequence<T> reversed();

	ListIterator<T> reverseIterator();

	ListIterator<T> iterator();

	List<T> asCollection();
}
