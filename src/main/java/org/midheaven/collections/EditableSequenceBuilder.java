package org.midheaven.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EditableSequenceBuilder {

    private Long size;

    EditableSequenceBuilder(Long size) {
        this.size = size;
    }

    public <U> EditableSequence<U> empty(){
        // no size means 0 size not editable
        if (size == null) {
            return Array.empty();
        }
        return (EditableSequence<U>)Array.newArray(Object.class, size.intValue());
    }

    @SuppressWarnings("unchecked")
	public <U> EditableSequence<U> of(U ... values){
		return Array.of(values);
	}

    public <U> EditableSequence<U> repeat(U value){
        // no size means 0 size not editable
        if (size == null) {
            return Array.empty();
        }
        return Array.repeat(value, size.intValue());
    }

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
