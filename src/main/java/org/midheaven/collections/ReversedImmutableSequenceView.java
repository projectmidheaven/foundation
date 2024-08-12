package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class ReversedImmutableSequenceView<T> implements Sequence<T> {

	Sequence<T> original;

	public ReversedImmutableSequenceView(Sequence<T> original) {
		this.original = original;
	}

	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.fromIterator(this.iterator(), original.count());
	}

	protected Int reverseIndex(Int index){
		return this.count().minus(index).minus(1);
	}

	@Override
	public Maybe<T> getAt(Int index) {
		return original.getAt(reverseIndex(index));
	}

	@Override
	public Int indexOf(Object o) {
		return lastIndexOf(o);
	}

	@Override
	public Int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public Maybe<T> first() {
		return original.last();
	}

	@Override
	public Maybe<T> last() {
		return original.first();
	}

	@Override
	public boolean contains(T any) {
		return original.contains(any);
	}

	@Override
	public Int count() {
		return original.count();
	}

	@Override
	public boolean isEmpty() {
		return original.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return original.reverseIterator();
	}

	@Override
	public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public Sequence<T> reversed() {
		return original;
	}

	@Override
	public Iterator<T> reverseIterator() {
		return original.iterator();
	}

	@Override
	public List<T> asCollection() {
		return Collections.unmodifiableList(original.asCollection().reversed());
	}

}
