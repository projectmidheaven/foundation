package org.midheaven.collections;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

class ReversedImmutableSequenceView<T> implements Sequence<T> {

	Sequence<T> original;

	public ReversedImmutableSequenceView(Sequence<T> original) {
		this.original = original;
	}

	@Override
	public Enumerator<T> enumerator() {
		return new IteratorEnumeratorAdapter<>(this.iterator(), original.count());
	}

	protected int reverseIndex(int index){
		return this.size() - index - 1;
	}
	@Override
	public Optional<T> getAt(int index) {
		return original.getAt(reverseIndex(index));
	}

	@Override
	public int indexOf(Object o) {
		return lastIndexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public Optional<T> first() {
		return original.last();
	}

	@Override
	public Optional<T> last() {
		return original.first();
	}

	@Override
	public boolean contains(T any) {
		return original.contains(any);
	}



	@Override
	public long count() {
		return original.count();
	}

	@Override
	public int size() {
		return original.size();
	}

	@Override
	public boolean isEmpty() {
		return original.isEmpty();
	}

	@Override
	public ListIterator<T> iterator() {
		return original.reverseIterator();
	}

	@Override
	public Sequence<T> subSequence(int fromIndex, int toIndex) {
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public Sequence<T> reversed() {
		return original;
	}

	@Override
	public ListIterator<T> reverseIterator() {
		return original.iterator();
	}

	@Override
	public List<T> asCollection() {
		return Collections.unmodifiableList(original.asCollection().reversed());
	}

}
