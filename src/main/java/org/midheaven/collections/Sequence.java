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

/**
 * Defines the contract for Sequence.
 */
public interface Sequence<T> extends Assortment<T> {
 
	/**
	 * Returns a builder for Sequence. The default is an immutable sequence, use {@code editable()} and {@code resizable()}
	 * to build mutable versions.
	 */
	static SequenceBuilder builder(){
		return new SequenceBuilder();
	}
	
	/**
	 *{@inheritDoc}
	 */
	default Sequence<T> toSequence() {
		return this;
	}
	
	/**
	 * Returns get At.
	 * @param index the index value
	 * @return the result of getAt
	 */
	default Maybe<T> getAt(int index){
		return getAt(Int.of(index));
	}
	
	/**
	 * Returns get At.
	 * @param index the index value
	 * @return the result of getAt
	 */
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
	
	/**
	 * Performs indexOf.
	 * @param o the o value
	 * @return the result of indexOf
	 */
	Int indexOf(Object o);
	
	/**
	 * Returns last Index Of.
	 * @param o the o value
	 * @return the result of lastIndexOf
	 */
	default Int lastIndexOf(Object o) {
		return reversed().indexOf(o);
	}
	
	/**
	 * Performs contains.
	 * @param candidate the any value
	 * @return the result of contains
	 */
	@Override
	/**
	 * Performs contains.
	 * @param any the any value
	 * @return the result of contains
	 */
	default boolean contains(T candidate) {
		return indexOf(candidate).isGreaterThanOrEqualTo(0);
	}
	
	/**
	 * Performs first.
	 * @return the result of first
	 */
	default Maybe<T> first() {
		var iterator =  iterator();
		if (iterator.hasNext()) {
			return Maybe.of(iterator.next());
		}
		return Maybe.none();
	}
	
	/**
	 * Performs last.
	 * @return the result of last
	 */
	default Maybe<T> last() {
		var reverseIterator = reverseIterator();
		if (reverseIterator.hasNext()) {
			return Maybe.of(reverseIterator.next());
		}
		return Maybe.none();
	}
	
	/**
	 * Performs subSequence.
	 * @param fromIndex the fromIndex value
	 * @param toIndex the toIndex value
	 * @return the result of subSequence
	 */
	default Sequence<T> subSequence(int fromIndex, int toIndex){
		return subSequence(Int.of(fromIndex), Int.of(toIndex));
	}

	/**
	 * Performs subSequence.
	 * @param fromIndex the fromIndex value
	 * @param toIndex the toIndex value
	 * @return the result of subSequence
	 */
	default Sequence<T> subSequence(Int fromIndex, Int toIndex){
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}
	
	/**
	 * Performs reversed.
	 * @return the result of reversed
	 */
	default Sequence<T> reversed() {
		return new ReversedImmutableSequenceView<>(this);
	}
	
	/**
	 * Performs reverse Iterator.
	 * @return the result of reverseIterator
	 */
	default Iterator<T> reverseIterator() {
		return reversed().enumerator().toIterator();
	}
	
	/**
	 * Returns an iterator over the elements.
	 * @return the result of iterator
	 */
	default Iterator<T> iterator() {
		return this.enumerator().toIterator();
	}

	/**
	 * Returns to Collection.
	 * @return the result of toCollection
	 */
	default List<T> toCollection(){
		return this.collect(Collectors.toUnmodifiableList());
	}
	
	/**
	 * Performs sorted.
	 * @param comparator the comparator value
	 * @return the result of sorted
	 */
	default Sequence<T> sorted(Comparator<T> comparator){
		return new SequenceEnumerableView<>(new SortPipe<>(comparator).applyTo(this));
	}

	/**
	 * Performs map.
	 * @param transform the transform value
	 * @return the result of map
	 */
	@Override
	/**
	 * Performs map.
	 * @param transform the transform value
	 * @return the result of map
	 */
	default <R> Sequence<R> map(Function<T, R> transform) {
		return new TransformSequenceView<>(this, transform);
	}
	/**
	 * Performs filter.
	 * @param predicate the predicate value
	 * @return the result of filter
	 */
	@Override
	/**
	 * Performs filter.
	 * @param predicate the predicate value
	 * @return the result of filter
	 */
	default Sequence<T> filter(Predicate<T> predicate){
		return new FilterSequenceView<>(this, predicate);
	}
	
	/**
	 * Performs cast.
	 * @param type the type value
	 * @return the result of cast
	 */
	default <R> Sequence<R> cast(Class<R> type){
		return this.map(type::cast);
	}
	
	/**
	 * Returns of Type.
	 * @param type the type value
	 * @return the result of ofType
	 */
	default <R> Sequence<R> ofType(Class<R> type){
		return this.filter(type::isInstance).map(type::cast);
	}
}
