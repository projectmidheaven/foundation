package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;
import java.util.List;

class ImmutableSubsequenceView<T> implements Sequence<T> {

	final Sequence<T> original;
	final Int fromIndex;
	final Int toIndex;

	ImmutableSubsequenceView(Sequence<T> original, Int fromIndex, Int toIndex){
		this.original = original;
		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public boolean equals(Object other){
		return other instanceof Sequence sequence
				&& SequencesSupport.equals(this , sequence);
	}

	@Override
	public int hashCode( ){
		return HashCode.of(asCollection());
	}

	@Override
	public String toString( ){
		return asCollection().toString();
	}

	@Override
	public Maybe<T> getAt(Int index) {
		var newIndex = index.plus(fromIndex);
		if (newIndex.isGreaterThan(toIndex)) {
			return Maybe.none();
		}
		return original.getAt(newIndex);
	}

	@Override
	public Int indexOf(Object o) {
		var index = original.indexOf(o);
		if (index.isLessThan(fromIndex) || index.isGreaterThan(toIndex)) {
			return index;
		}
		return index.minus(fromIndex);
	}

	@Override
	public Int lastIndexOf(Object o) {
		var index = original.lastIndexOf(o);
		if (index.isLessThan(fromIndex) || index.isGreaterThan(toIndex)) {
			return Int.MINUS_ONE;
		}
		return index.minus(fromIndex);
	}

	@Override
	public Maybe<T> first() {
		return getAt(0);
	}

	@Override
	public Maybe<T> last() {
		return getAt(toIndex.minus(1));
	}


	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.fromIterator(this.iterator(), this.count());
	}

	@Override
	public Iterator<T> reverseIterator() {
		return new SubSequenceIterator<>(original,toIndex , fromIndex);
	}

	@Override
	public boolean contains(Object any) {
		var index = indexOf(any);
		return index.isGreaterThanOrEqualTo(fromIndex) &&  index.isLessThanOrEqualTo(toIndex);
	}

	@Override
	public boolean containsAll(Iterable<? extends T> all) {
		for (var item : all){
			if (!contains(item)){
				return false;
			}
		}
		return true;
	}

	public Int count() {
		return toIndex.minus(fromIndex).plus(1);
	}

	@Override
	public boolean isEmpty() {
		return this.count().isZero();
	}

	@Override
	public Iterator<T> iterator() {
		return new SubSequenceIterator<>(original, fromIndex, toIndex);
	}

	@Override
	public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public Sequence<T> reversed() {
		return new ReversedImmutableSequenceView<>(this);
	}

	@Override
	public List<T> asCollection() {
		return original.asCollection().subList(fromIndex.toInt(), toIndex.toInt());
	}

}
