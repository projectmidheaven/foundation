package org.midheaven.collections;

import org.midheaven.lang.Check;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;
import org.midheaven.math.Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * An {@link EditableSequence} that behaves like an array.
 */
public interface Array<T> extends EditableSequence<T> {

	/**
	 * Returns an empty instance.
	 * @return an empty instance.
	 */
	static <X> Array<X> empty(){
		return EmptyArray.emptyArray();
	}

	/**
	 * Creates a new {@link Array} of the given type and size.
	 * All positions are set to {@code null}. Primitive types are not allowed.
	 *
	 * @param type the type of the {@link Array}
	 * @param size the size of the {@link Array}
	 * @return the created array
	 */
	@SuppressWarnings("unchecked")
	static <X> @NotNullable Array<X> newArray(@NotNullable Class<X> type, int size){
		Check.argumentIsNotNull(type, "type");
		if (type.isPrimitive()) {
			throw new IllegalArgumentException("Type cannot be a primitive");
		} else if (size < 0) {
			throw new IllegalArgumentException("Size must be zero ou positive");
		} else if (size == 0) {
			return EmptyArray.emptyArray();
		}
		return new ArrayWrapper<>((X[])java.lang.reflect.Array.newInstance(type, size));
	}
	
	/**
	 * Creates a new {@link Array} with the given values
	 *
	 * @param values the values of the array
	 * @return the created array
	 */
	@SuppressWarnings("unchecked")
	static <X> @NotNullable Array<X> of(X ... values){
		if (values == null || values.length == 0) {
			return EmptyArray.emptyArray();
		}
		return new ArrayWrapper<>(values);
	}
	
	 /**
	 * Creates a new {@link Array} of the given size where all values are the same as the given non-null value
	 *
	 * @param value the given value
	 * @param size the size of the array
	 * @return the created array
	 */
	static <X> @NotNullable Array<X> repeat(@Nullable X value, int size) {
		if (size == 0) {
			return EmptyArray.emptyArray();
		}
		var array = value == null
		 ? (X[])java.lang.reflect.Array.newInstance(Object.class, size)
		 : (X[])java.lang.reflect.Array.newInstance(value.getClass(), size);
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
	public List<T> toCollection() {
		var list = new ArrayList<T>(this.array.length);
        list.addAll(Arrays.asList(array));
		return Collections.unmodifiableList(list);
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
