package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Delegating Random values.
 */
public abstract class DelegatingRandomGenerator<T> implements RandomGenerator<T> {

    private final Supplier<Random> base;

    DelegatingRandomGenerator(Supplier<Random> base){
        this.base = base;
    }

    /**
     * Performs base.
     * @return the result of base
     */
    protected final Random base(){
        return base.get();
    }



}
