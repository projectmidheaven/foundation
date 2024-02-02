package org.midheaven.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SequenceBuilder {

	private Long size;

	public SequenceBuilder withSize(long size){
		this.size = size;
		return this;
	}

	public EditableSequenceBuilder editable() {
		return new EditableSequenceBuilder(size);
	}

	public ResizableSequenceBuilder resizable() {
		return new ResizableSequenceBuilder(size);
	}

	public <U> Sequence<U> empty(){
		return ImmutableEmptySequence.instance();
	}

	@SuppressWarnings("unchecked")
	public <U> Sequence<U> of(U ... values){
		return new ImmutableSequenceArrayWrapper<>(values);
	}

	public <U> Sequence<U> repeat(U value){
		// no size means 0 size not editable
		if (size == null) {
			return ImmutableEmptySequence.instance();
		}
		return new RepeatedSequence<>(value, size);
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
