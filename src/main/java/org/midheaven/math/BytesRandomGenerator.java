package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Bytes Random values.
 */
public final class BytesRandomGenerator extends DelegatingRandomGenerator<Byte> implements RandomGenerator<Byte>{


    BytesRandomGenerator(Supplier<Random> base) {
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
    public Byte next() {
        return (byte)base().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    /**
     * Returns next Bytes.
     * @param bytes the bytes value
     * @return the result of nextBytes
     */
    public byte[] nextBytes(byte[] bytes) {
        base().nextBytes(bytes);
        return bytes;
    }
}
