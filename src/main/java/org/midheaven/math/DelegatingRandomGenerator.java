package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

public abstract class DelegatingRandomGenerator<T> implements RandomGenerator<T> {

    private final Supplier<Random> base;

    DelegatingRandomGenerator(Supplier<Random> base){
        this.base = base;
    }

    protected final Random base(){
        return base.get();
    }



}
