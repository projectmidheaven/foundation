package org.midheaven.collections;

import java.util.Arrays;
import java.util.HashSet;

public class ResizableDistinctAssortmentBuilder {

    public <T> ResizableDistinctAssortment<T> empty() {
        return new ResizableSetWrapper<>(new HashSet<>());
    }

    @SafeVarargs
    public final <T> ResizableDistinctAssortment<T> of(T... values) {
        return from(Arrays.asList(values));
    }

    public <T> ResizableDistinctAssortment<T> from(Iterable<T> origin) {
        if (!origin.iterator().hasNext() ) {
            return empty();
        }

        return AssortmentSupport.<T, HashSet<T> , ResizableDistinctAssortment<T>>from(
                origin,
                HashSet::new,
                ResizableSetWrapper::new
        );
    }
}
