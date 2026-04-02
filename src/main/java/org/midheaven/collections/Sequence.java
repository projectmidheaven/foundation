package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Sequence<T> extends Assortment<T> {
 
	/**
	 * Returns a builder for Sequence. The default is an immutable sequence, use {@code editable()} and {@code resizable()}
	 * to build mutable versions.
	 */
	static SequenceBuilder builder(){
		return new SequenceBuilder();
	}
	
	default Sequence<T> toSequence() {
		return this;
	}

	default Maybe<T> getAt(int index){
		return getAt(Int.of(index));
	}
	
	default Maybe<T> getAt(Int index) {
		var iterator =  iterator();
		IntAccumulator position = new IntAccumulator(Int.NEGATIVE_ONE);
		T found = null;
		while (iterator.hasNext() && position.isLessThan(index)) {
			found = iterator.next();
			position.increment();
		}
		return Maybe.of(found);
	}
	
	Int indexOf(Object o);
	
	default Int lastIndexOf(Object o) {
		return reversed().indexOf(o);
	}
	
	@Override
	default boolean contains(T any) {
		return indexOf(any).isGreaterThanOrEqualTo(0);
	}
	
	default Maybe<T> first() {
		var iterator =  iterator();
		if (iterator.hasNext()) {
			return Maybe.of(iterator.next());
		}
		return Maybe.none();
	}
	
	default Maybe<T> last() {
		var reverseIterator = reverseIterator();
		if (reverseIterator.hasNext()) {
			return Maybe.of(reverseIterator.next());
		}
		return Maybe.none();
	}
	
	default Sequence<T> subSequence(int fromIndex, int toIndex){
		return subSequence(Int.of(fromIndex), Int.of(toIndex));
	}

	default Sequence<T> subSequence(Int fromIndex, Int toIndex){
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}
	
	default Sequence<T> reversed() {
		return new ReversedImmutableSequenceView<>(this);
	}
	
	default Iterator<T> reverseIterator() {
		return reversed().enumerator().toIterator();
	}
	
	default Iterator<T> iterator() {
		return this.enumerator().toIterator();
	}

	default List<T> toCollection(){
		return this.collect(Collectors.toUnmodifiableList());
	}
	
	default Sequence<T> sorted(Comparator<T> comparator){
		return new SequenceEnumerableView<>(new SortPipe<>(comparator).applyTo(this));
	}

	@Override
	default <R> Sequence<R> map(Function<T, R> transform) {
		return new TransformSequenceView<>(this, transform);
	}
	@Override
	default Sequence<T> filter(Predicate<T> predicate){
		return new FilterSequenceView<>(this, predicate);
	}
	
	default <R> Sequence<R> cast(Class<R> type){
		return this.map(type::cast);
	}
	
	default <R> Sequence<R> ofType(Class<R> type){
		return this.filter(type::isInstance).map(type::cast);
	}
}
