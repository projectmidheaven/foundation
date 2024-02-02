package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

public final class BytesRandomGenerator extends DelegatingRandomGenerator<Byte> implements RandomGenerator<Byte>{


    BytesRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    public Byte next() {
        return (byte)base().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public byte[] nextBytes(byte[] bytes) {
        base().nextBytes(bytes);
        return bytes;
    }
}
