package org.midheaven.collections;

import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;


public interface Array<T> extends EditableSequence<T> {

	static <X> Array<X> empty(){
		return EmptyArray.emptyArray();
	}

	@SuppressWarnings("unchecked")
	static <X> Array<X> newArray(Class<X> type, int size){
		if (size < 0) {
			throw new IllegalArgumentException("Size must be zero ou positive");
		} else if (size == 0) {
			return EmptyArray.emptyArray();
		}
		return new ArrayWrapper<>((X[])java.lang.reflect.Array.newInstance(type, size));
	}
		
	@SuppressWarnings("unchecked")
	static <X> Array<X> of(X ... array){
		if (array.length == 0) {
			return EmptyArray.emptyArray();
		}
		return new ArrayWrapper<>(array);
	}

	static <X> Array<X> repeat(X value, int size) {
		if (size == 0) {
			return EmptyArray.emptyArray();
		} else if (value == null){
			throw new IllegalArgumentException("Value cannot be null");
		}
		var array = (X[])java.lang.reflect.Array.newInstance(value.getClass(), size);
		Arrays.fill(array, value);
		return new ArrayWrapper<>(array);
	}


}

class EmptyArray<T> extends ImmutableEmptySequence<T> implements Array<T> {

	@SuppressWarnings("rawtypes")
	private static final EmptyArray ME = new EmptyArray();

	@SuppressWarnings("unchecked")
	static <X> EmptyArray<X> emptyArray() {
		return ME;
	}

	@Override
	public Maybe<T> setAt(int index, T element) {
		throw new IndexOutOfBoundsException(index);
	}

	@Override
	public Maybe<T> setAt(Int index, T element) {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public Maybe<T> setFirst(T element) {
		throw new IndexOutOfBoundsException(0);
	}

	@Override
	public Maybe<T> setLast(T element) {
		throw new IndexOutOfBoundsException(0);
	}

	public Array<T> reversed(){
		return this;
	}

	@Override
	public Array<T> subSequence(int fromIndex, int toIndex) {
		return this;
	}

	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.empty();
	}

	@Override
	public Array<T> filter(Predicate<T> predicate) {
		return ME;
	}

	@Override
	public <R> Sequence<R> map(Function<T, R> transform) {
		return ME;
	}

	@Override
	public <R> Enumerable<R> flatMap(Function<T, Enumerable<R>> transform) {
		return ME;
	}

}


class ArrayWrapper<T>  extends ImmutableSequenceArrayWrapper<T> implements Array<T> {

	ArrayWrapper(T[] array){
		super(array); // non-empty array
	}

	@SuppressWarnings("unchecked")
	@Override
	public Maybe<T> setAt(int index, T element) {
		var previous = array[index];
		array[index] = element;
		return Maybe.of(previous);
	}

	@Override
	public Maybe<T> setAt(Int index, T element) {
		var p = index.toInt();
		var previous = array[p];
		array[p] = element;
		return Maybe.of(previous);
	}

	@Override
	public Maybe<T> setFirst(T element) {
		var previous = array[0];
		array[0] = element;
		return Maybe.of(previous);
	}

	@Override
	public Maybe<T> setLast(T element) {
		var previous = array[array.length - 1];
		array[array.length - 1] = element;
		return Maybe.of(previous);
	}

	@Override
	public ListIterator<T> iterator() {
		return Arrays.asList(this.array).listIterator();
	}

	@Override
	public ListIterator<T> reverseIterator() {
		return Arrays.asList(this.array).listIterator(this.array.length);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> asCollection() {
		return Arrays.asList(this.array);
	}

	@Override
	public EditableSequence<T> subSequence(int fromIndex, int toIndex) {
		if (fromIndex >=0 && fromIndex <= toIndex && toIndex >=0 && toIndex < array.length) {
			return new EditableSubsequenceView<>(this, Int.of(fromIndex), Int.of(toIndex));
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public EditableSequence<T> subSequence(Int fromIndex, Int toIndex) {
		if (fromIndex.isGreaterThanOrEqualTo(0) && fromIndex.isLessThanOrEqualTo(toIndex) && toIndex.isGreaterThanOrEqualTo(0) && toIndex.isLessThan(array.length)) {
			return new EditableSubsequenceView<>(this, fromIndex, toIndex);
		}
		throw new IndexOutOfBoundsException();
	}
	@Override
	public EditableSequence<T> reversed() {
		return new ReversedEditableSequenceView<>(this);
	}


}
