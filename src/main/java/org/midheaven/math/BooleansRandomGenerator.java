package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

public final class BooleansRandomGenerator extends DelegatingRandomGenerator<Boolean>{

    BooleansRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    public Boolean next() {
        return base().nextBoolean();
    }
}
