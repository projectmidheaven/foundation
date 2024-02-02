package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlagTestCases {

    @Test
    public void setBitCorrectly(){
        var flag = IntegerFlag.none(TestFlags.class);

        assertEquals(0, flag.toInt());

        assertEquals(1, flag.set(TestFlags.A).toInt());
        assertEquals(2, flag.set(TestFlags.B).toInt());
        assertEquals(4, flag.set(TestFlags.C).toInt());
        assertEquals(8, flag.set(TestFlags.D).toInt());
        assertEquals(9, flag.set(TestFlags.D).set(TestFlags.A).toInt());
        assertEquals(0, flag.set(TestFlags.D).flip(TestFlags.D).toInt());

        assertTrue(flag.set(TestFlags.C).isSet(TestFlags.C));
        assertFalse(flag.set(TestFlags.C).isSet(TestFlags.A));

        assertTrue(IntegerFlag.with(TestFlags.C).isSet(TestFlags.C));
        assertFalse(IntegerFlag.with(TestFlags.C).isSet(TestFlags.A));
    }
}

enum TestFlags implements FlagElement<TestFlags> {

    A,
    B,
    C,
    D;

    @Override
    public int flagPosition() {
        return this.ordinal();
    }
}
