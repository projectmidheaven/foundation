package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

class ImmutableEmptySequence<T> implements Sequence<T> {


	@SuppressWarnings("rawtypes")
	private static final ImmutableEmptySequence ME = new ImmutableEmptySequence();
	
	@SuppressWarnings("unchecked")
	public static <X> ImmutableEmptySequence<X> instance() {
		return ME;
	}

	@Override
	public Maybe<T> getAt(int index) {
		return Maybe.none();
	}

	@Override
	public Maybe<T> getAt(Int index) {
		return Maybe.none();
	}

	@Override
	public Int indexOf(Object o) {
		return Int.MINUS_ONE;
	}

	@Override
	public Int lastIndexOf(Object o) {
		return Int.MINUS_ONE;
	}

	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.empty();
	}

	@Override
	public Maybe<T> first() {
		return Maybe.none();
	}

	@Override
	public Maybe<T> last() {
		return Maybe.none();
	}

	@Override
	public Sequence<T> subSequence(int fromIndex, int toIndex) {
		return this;
	}

	@Override
	public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
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
	public Int count() {
		return Int.ZERO;
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
