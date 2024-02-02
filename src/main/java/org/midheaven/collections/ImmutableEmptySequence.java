package org.midheaven.collections;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

class ImmutableEmptySequence<T> implements Sequence<T> {


	@SuppressWarnings("rawtypes")
	private static final ImmutableEmptySequence ME = new ImmutableEmptySequence();
	
	@SuppressWarnings("unchecked")
	public static <X> ImmutableEmptySequence<X> instance() {
		return ME;
	}

	@Override
	public Optional<T> getAt(int index) {
		return Optional.empty();
	}

	@Override
	public int indexOf(Object o) {
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return -1;
	}

	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.empty();
	}

	@Override
	public Optional<T> first() {
		return Optional.empty();
	}

	@Override
	public Optional<T> last() {
		return Optional.empty();
	}

	@Override
	public Sequence<T> subSequence(int fromIndex, int toIndex) {
		return this;
	}

	@Override
	public Sequence<T> reversed() {
		return this;
	}

	@Override
	public boolean contains(T any) {
		return false;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public ListIterator<T> iterator() {
		return Collections.emptyListIterator();
	}

	@Override
	public ListIterator<T> reverseIterator() {
		return Collections.emptyListIterator();
	}

	@Override
	public boolean containsAll(Iterable<? extends T> all) {
		return false;
	}

	@Override
	public List<T> asCollection() {
		return Collections.emptyList();
	}

	@Override
	public boolean equals(Object other){
		return other instanceof Sequence<?> sequence
				&& sequence.isEmpty();
	}

	@Override
	public int hashCode( ){
		return 0;
	}

	@Override
	public String toString( ){
		return "[]";
	}
}
