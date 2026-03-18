package org.midheaven.collections;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;

import java.util.Arrays;
import java.util.HashSet;

public class DistinctAssortmentBuilder {

    public ResizableDistinctAssortmentBuilder resizable() {
        return new ResizableDistinctAssortmentBuilder();
    }

    public <U> DistinctAssortment<U> empty(){
        return EmptyDistinctAssortment.instance();
    }

    public <U> DistinctAssortment<U> of(@NotNullable U singleValue){
        Check.argumentIsNotNull(singleValue);
        return new SingleDistinctAssortment<>(singleValue);
    }

    @SafeVarargs
    public final <U> DistinctAssortment<U> of(@NotNullable U... values){
        Check.argumentIsNotNull(values);
        if (values.length == 0){
            return empty();
        } else if (values.length == 1){
            return of(values[0]);
        }
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
