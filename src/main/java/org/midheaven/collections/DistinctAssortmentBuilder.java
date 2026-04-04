package org.midheaven.collections;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Builder for Distinct Assortment instances.
 */
public class DistinctAssortmentBuilder {
    
    static final DistinctAssortmentBuilder INSTANCE = new DistinctAssortmentBuilder();
    
    private DistinctAssortmentBuilder(){}
    /**
     * Performs resizable.
     * @return the result of resizable
     */
    public ResizableDistinctAssortmentBuilder resizable() {
        return new ResizableDistinctAssortmentBuilder();
    }

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public <U> DistinctAssortment<U> empty(){
        return EmptyDistinctAssortment.instance();
    }
    
    
    /**
     * Creates an instance from the provided value.
     * @param singleValue the singleValue value
     * @return the result of of
     */
    public <U> DistinctAssortment<U> of(U singleValue){
        return new SingleDistinctAssortment<>(singleValue);
    }
    
    /**
     * Creates an instance from the provided value.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of of
     */
    public <U> DistinctAssortment<U> of(U a, U b, U ... others ){
        var origin = new HashSet<U>();
        origin.add(a);
        origin.add(b);
        
        if (others != null){
            origin.addAll(Arrays.asList(others));
        }
        
        return from(origin);
    }
    
    /**
     * Creates an instance from the provided value.
     * @param values the values value
     * @return the result of of
     */
    public final <U> DistinctAssortment<U> of(U[] values){
        if (values == null || values.length == 0){
            return empty();
        } else if (values.length == 1){
            return of(values[0]);
        }
        return from(Arrays.asList(values));
    }

    /**
     * Creates an instance from the provided source.
     * @param origin the origin value
     * @return the result of from
     */
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
