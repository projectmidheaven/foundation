package org.midheaven.collections;

import org.midheaven.lang.HashCode;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

class ImmutableSubsequenceView<T> implements Sequence<T> {

	final Sequence<T> original;
	final int fromIndex;
	final int toIndex;

	ImmutableSubsequenceView(Sequence<T> original, int fromIndex, int toIndex){
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
	public Optional<T> getAt(int index) {
		var newIndex = fromIndex + index;
		if (newIndex > toIndex) {
			return Optional.empty();
		}
		return original.getAt(newIndex);
	}

	@Override
	public int indexOf(Object o) {
		var index = original.indexOf(o);
		if (index < fromIndex || index > toIndex) {
			return index;
		}
		return index - fromIndex;
	}

	@Override
	public int lastIndexOf(Object o) {
		var index = original.lastIndexOf(o);
		if (index < fromIndex || index > toIndex) {
			return -1;
		}
		return index - fromIndex;
	}

	@Override
	public Optional<T> first() {
		return getAt(0);
	}

	@Override
	public Optional<T> last() {
		return getAt(toIndex - 1);
	}


	@Override
	public Enumerator<T> enumerator() {
		return new IteratorEnumeratorAdapter<>(this.iterator(), size());
	}

	@Override
	public ListIterator<T> reverseIterator() {
		return new SubSequenceIterator<>(original,toIndex , fromIndex);
	}

	@Override
	public boolean contains(Object any) {
		var index = indexOf(any);
		return index >= fromIndex && index <= toIndex;
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

	@Override
	public long count() {
		return size();
	}

	@Override
	public int size() {
		return toIndex - fromIndex + 1;
	}

	@Override
	public boolean isEmpty() {
		return count() == 0;
	}

	@Override
	public ListIterator<T> iterator() {
		return new SubSequenceIterator<>(original, fromIndex, toIndex);
	}


	@Override
	public Sequence<T> subSequence(int fromIndex, int toIndex) {
		return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
	}

	@Override
	public Sequence<T> reversed() {
		return new ReversedImmutableSequenceView<>(this);
	}

	@Override
	public List<T> asCollection() {
		return original.asCollection().subList(fromIndex, toIndex);
	}

}
