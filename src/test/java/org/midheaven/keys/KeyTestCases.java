package org.midheaven.keys;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyTestCases {

    @Test
    public void equality(){
        var a = Key.of(SomeConcept.class, "23");
        var b = Key.of(SomeConcept.class, "23");
        var c = Key.of(SomeOtherConcept.class, "23");
        var d = Key.of(SomeDerivedConcept.class, "23");

        assertEquals(a,b);
        assertNotEquals(a,c);
        assertEquals(a,d);
    }

    @Test
    public void parsing(){
        assertThrows(KeyParsingException.class , () -> Key.parse(SomeConcept.class, null));

        var a = Key.parse(SomeConcept.class, "23::SomeConcept");
        var b = Key.parse(SomeConcept.class, "23::someconcept");
        var c = Key.parse(SomeOtherConcept.class, "23::SomeOtherConcept");
        var d = Key.parse(SomeDerivedConcept.class, "23::SomeConcept");

        assertEquals(a,b);
        assertNotEquals(a,c);
        assertEquals(a,d);
    }
}

interface SomeConcept extends Concept{}
interface SomeOtherConcept extends Concept{}
interface SomeDerivedConcept extends SomeConcept{}