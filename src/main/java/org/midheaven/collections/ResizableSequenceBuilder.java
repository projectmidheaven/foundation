package org.midheaven.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Builder for Resizable Sequence instances.
 */
public class ResizableSequenceBuilder{


	private Long size;

	ResizableSequenceBuilder(){}

	ResizableSequenceBuilder(Long size){
		this.size = size;
	}

	/**
	 * Returns an empty instance.
	 * @return the result of empty
	 */
	public <U> ResizableSequence<U> empty(){
		if (size == null){
			return new ResizableSequenceListWrapper<U>(new LinkedList<>());
		}
		return new ResizableSequenceListWrapper<U>(new ArrayList<>(size.intValue()));
	}

	/**
	 * Creates an instance from the provided value.
	 * @param values the values value
	 * @return the result of of
	 */
	@SuppressWarnings("unchecked")
	/**
	 * Creates an instance from the provided value.
	 * @param values the values value
	 * @return the result of of
	 */
	public <U> ResizableSequence<U> of(U ... values){
		return new ResizableSequenceListWrapper<U>(new ArrayList<>(Arrays.asList(values)));
	}

	/**
	 * Performs repeat.
	 * @param value the value value
	 * @return the result of repeat
	 */
	public <U> ResizableSequence<U> repeat(U value){
		// no size means 0 size
		if (size == null) {
			return empty();
		}
		return new ResizableSequenceListWrapper<U>(new ArrayList<>(Collections.nCopies(size.intValue(), value)));
	}

	/**
	 * Creates an instance from the provided source.
	 * @param origin the origin value
	 * @return the result of from
	 */
	public <T> ResizableSequence<T> from(Iterable<T> origin){
		if (!origin.iterator().hasNext() ) {
			return empty();
		}

		return AssortmentSupport.<T, List<T>, ResizableSequence<T>>from(
				origin,
				c -> c.isEmpty() ? new LinkedList<>() : new ArrayList<>(c),
				ResizableSequenceListWrapper::new
		);

	}
}
