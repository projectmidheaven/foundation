package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Booleans Random values.
 */
public final class BooleansRandomGenerator extends DelegatingRandomGenerator<Boolean>{

    BooleansRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    /**
     * Performs next.
     * @return the result of next
     */
    @Override
    /**
     * Performs next.
     * @return the result of next
     */
    public Boolean next() {
        return base().nextBoolean();
    }
}
