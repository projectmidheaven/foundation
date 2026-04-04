package org.midheaven.collections;

import org.midheaven.lang.Check;
import org.midheaven.math.Int;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Builder for Sequence instances.
 */
public class SequenceBuilder {

	private Long size;

	/**
	 * Performs withSize.
	 * @param size the size value
	 * @return the result of withSize
	 */
	public SequenceBuilder withSize(long size){
		this.size = size;
		return this;
	}

	/**
	 * Performs editable.
	 * @return the result of editable
	 */
	public EditableSequenceBuilder editable() {
		return new EditableSequenceBuilder(size);
	}

	/**
	 * Performs resizable.
	 * @return the result of resizable
	 */
	public ResizableSequenceBuilder resizable() {
		return new ResizableSequenceBuilder(size);
	}

	/**
	 * Returns an empty instance.
	 * @return the result of empty
	 */
	public <U> Sequence<U> empty(){
		return ImmutableEmptySequence.instance();
	}
	
	/**
	 * Creates an instance from the provided value.
	 * @param value the value value
	 * @return the result of of
	 */
	public <U> Sequence<U> of(U value){
		return new ImmutableSingleSequence<>(value);
	}
	
	/**
	 * Creates an instance from the provided value.
	 * @param a the a value
	 * @param b the b value
	 * @param others the others value
	 * @return the result of of
	 */
	@SuppressWarnings("unchecked")
	/**
	 * Creates an instance from the provided value.
	 * @param a the a value
	 * @param b the b value
	 * @param others the others value
	 * @return the result of of
	 */
	public <U> Sequence<U> of(U a, U b, U ... others){
		Check.argumentIsNotNull(others);
		return new ImmutableSequenceArrayWrapper<>(a, b, others);
	}
	
	/**
	 * Creates an instance from the provided value.
	 * @param values the values value
	 * @return the result of of
	 */
	public <U> Sequence<U> of(U[] values){
		if (values == null || values.length == 0){
			return empty();
		} else if (values.length == 1){
			return new ImmutableSingleSequence<>(values[0]);
		}
		return new ImmutableSequenceArrayWrapper<>(values);
	}
	
	
	/**
	 * Performs repeat.
	 * @param value the value value
	 * @return the result of repeat
	 */
	public <U> Sequence<U> repeat(U value){
		// no size means 0 size not editable
		if (size == null) {
			return ImmutableEmptySequence.instance();
		}
		return new RepeatedSequence<>(value, Int.of(size));
	}

	/***
	 * Returns a {@link Sequence} that does not inherit from {@link EditableSequence}
	 * @param origin
	 * @return
	 * @param <T>
	 */
	public <T> Sequence<T> immutable(EditableSequence<T> origin){
		if (origin == null || origin.isEmpty()){
			return empty();
		} else if (origin instanceof EditableSequenceListWrapper<T> editableSequenceListWrapper){
			return new ImmutableSequenceListWrapper<>(editableSequenceListWrapper.original);
		} else if (origin instanceof ArrayWrapper<T> array){
			return new ImmutableSequenceArrayWrapper<>(array.array);
		}
		return from(origin);
	}

	/***
	 * Create a {@link Sequence} with elements from the given iterable
	 * @param origin
	 * @return
	 * @param <T>
	 */
	public <T> Sequence<T> from(Iterable<T> origin){
		if (origin == null || !origin.iterator().hasNext() ) {
			return empty();
		} else if (origin instanceof ImmutableSequenceListWrapper<T> immutableSequenceListWrapper){
			return new ImmutableSequenceListWrapper<>(immutableSequenceListWrapper.original);
		} else if (origin instanceof ArrayWrapper<T> array){
			return new ImmutableSequenceArrayWrapper<>(array.array);
		} else if (origin instanceof Sequence<T> sequence && sequence.isEmpty()){
			return empty();
		}

		return AssortmentSupport.<T, List<T>, Sequence<T>>from(
				origin,
				c -> c.isEmpty() ? new LinkedList<>() : new ArrayList<>(c),
				ResizableSequenceListWrapper::new
		);

	}
}
