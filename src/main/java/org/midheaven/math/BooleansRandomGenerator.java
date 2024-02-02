package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class BooleansRandomGenerator extends DelegatingRandomGenerator<Boolean> implements RandomGenerator<Boolean>{

    BooleansRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    public Boolean next() {
        return base().nextBoolean();
    }
}
