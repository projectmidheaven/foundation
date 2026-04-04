package org.midheaven.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Builder for Editable Sequence instances.
 */
public class EditableSequenceBuilder {

    private Long size;

    EditableSequenceBuilder(Long size) {
        this.size = size;
    }

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public <U> EditableSequence<U> empty(){
        // no size means 0 size not editable
        if (size == null) {
            return Array.empty();
        }
        return (EditableSequence<U>)Array.newArray(Object.class, size.intValue());
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
	public <U> EditableSequence<U> of(U ... values){
		return Array.of(values);
	}

    /**
     * Performs repeat.
     * @param value the value value
     * @return the result of repeat
     */
    public <U> EditableSequence<U> repeat(U value){
        // no size means 0 size not editable
        if (size == null) {
            return Array.empty();
        }
        return Array.repeat(value, size.intValue());
    }

    /**
     * Creates an instance from the provided source.
     * @param origin the origin value
     * @return the result of from
     */
    public <T> EditableSequence<T> from(Iterable<T> origin){
        if (!origin.iterator().hasNext() ) {
            return empty();
        }

        return (EditableSequence<T>)AssortmentSupport.<T, List<T>, Sequence<T>>from(
                origin,
                c -> c.isEmpty() ? new LinkedList<>() : new ArrayList<>(c),
                EditableSequenceListWrapper::new
        );

    }
}
