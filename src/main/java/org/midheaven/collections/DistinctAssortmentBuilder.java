package org.midheaven.collections;

import java.util.*;

public class DistinctAssortmentBuilder{

    public ResizableDistinctAssortmentBuilder resizable() {
        return new ResizableDistinctAssortmentBuilder();
    }

    public <U> DistinctAssortment<U> empty(){
        return EmptyDistinctAssortment.instance();
    }

    @SuppressWarnings("unchecked")
    public <U> DistinctAssortment<U> of(U ... values){
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
