package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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
	@Override
	default Sequence<T> filter(Predicate<T> predicate){
		return new FilterSequenceView<>(this, predicate);
	}

	default Sequence<T> toSequence() {
		return this;
	}

	default Maybe<T> getAt(int index){
		return getAt(Int.of(index));
	}

	Maybe<T> getAt(Int index);

	Int indexOf(Object o);

	Int lastIndexOf(Object o);

	@Override
	default boolean contains(T any) {
		return indexOf(any).isGreaterThanOrEqualTo(0);
	}

	Maybe<T> first();

	Maybe<T> last();

	default Sequence<T> subSequence(int fromIndex, int toIndex){
		return subSequence(Int.of(fromIndex), Int.of(toIndex));
	}

	Sequence<T> subSequence(Int fromIndex, Int toIndex);

	Sequence<T> reversed();

	Iterator<T> reverseIterator();

	Iterator<T> iterator();

	List<T> asCollection();
}
