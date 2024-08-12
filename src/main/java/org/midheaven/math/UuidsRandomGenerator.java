package org.midheaven.math;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public final class UuidsRandomGenerator extends DelegatingRandomGenerator<UUID> implements RandomGenerator<UUID>{

    UuidsRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    public UUID next() {

        byte[] data = new byte[16];
        base().nextBytes(data);
        data[6]  &= 0x0f;  /* clear version        */
        data[6]  |= 0x40;  /* set to version 4     */
        data[8]  &= 0x3f;  /* clear variant        */
        data[8]  |= (byte) 0x80;  /* set to IETF variant  */

        long msb = 0;
        long lsb = 0;

        for (int i=0; i<8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }

        for (int i=8; i<16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }

        return new UUID(msb, lsb);
    }
}
