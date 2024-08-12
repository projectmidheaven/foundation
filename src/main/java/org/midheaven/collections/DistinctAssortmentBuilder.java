package org.midheaven.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class DistinctAssortmentBuilder {

    public ResizableDistinctAssortmentBuilder resizable() {
        return new ResizableDistinctAssortmentBuilder();
    }

    public <U> DistinctAssortment<U> empty(){
        return EmptyDistinctAssortment.instance();
    }

    public <U> DistinctAssortment<U> of(U singleValue){
        return from(Collections.singletonList(singleValue));
    }

    @SafeVarargs
    public final <U> DistinctAssortment<U> of(U... values){
        return from(Arrays.asList(values));
    }

    public <T> DistinctAssortment<T> from(Iterable<T> origin){
        if (!origin.iterator().hasNext() ) {
            return empty();
        }

        return AssortmentSupport.<T, HashSet<T> , DistinctAssortment<T>>from(
                origin,
                HashSet::new,
                ResizableSetWrapper::new
        );
    }
}
