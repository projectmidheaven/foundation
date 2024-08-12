package org.midheaven.math;

import org.junit.jupiter.api.Test;
import org.midheaven.keys.Concept;
import org.midheaven.keys.Key;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomTestCases {

    @Test
    public void generateRandomValues(){
        var a = RandomGeneratorProvider.secure().doubles().next();

        assertNotNull(a);
        assertTrue(a >= 0 && a <=1 );

        var b = RandomGeneratorProvider.standard().doubles().next();

        assertNotNull(b);
        assertTrue(b >= 0 && b <=1 );

        var u = RandomGeneratorProvider.standard().doubles().between(0d,3d).next();

        assertNotNull(u);
        assertTrue(u >= 0 && u <=3 );

        var i = RandomGeneratorProvider.standard().integers().betweenIncluding(0,3).next();

        assertNotNull(i);
        assertTrue(i >= 0 && i <=3 );

        RandomGenerator<Key<Concept>> d = RandomGeneratorProvider.standard().generate(r -> Key.of(Concept.class, r.bigIntegers().next().toString()));
        RandomGenerator<Key<Concept>> m = RandomGeneratorProvider.standard().bigIntegers().generateNext(r -> Key.of(Concept.class, r.next().toString()));

        var list = RandomGeneratorProvider.standard().doubles().stream().limit(10).toList();

        assertEquals(10, list.size());

        for (var r : list){
            assertNotNull(r);
            assertTrue(r >= 0 && r <=1 );
        }

    }

    @Test
    public void randomRationalsGeneration(){
        var a = RandomGeneratorProvider.secure().rationals().next();

        assertNotNull(a);
        assertTrue(a.isGreaterThanOrEqualTo(0) && a.isLessThanOrEqualTo(1) );

        var b = RandomGeneratorProvider.standard().rationals().next();

        assertNotNull(b);
        assertTrue(b.isGreaterThanOrEqualTo(0) && b.isLessThanOrEqualTo(1) );

        var u = RandomGeneratorProvider.standard().rationals().between(Rational.ZERO, Rational.of(3)).next();

        assertNotNull(u);
        assertTrue(u.isGreaterThanOrEqualTo(0) && u.isLessThanOrEqualTo(3));

        var list = RandomGeneratorProvider.standard().rationals().stream().limit(10).toList();

        assertEquals(10, list.size());

        for (var r : list){
            assertNotNull(r);
            assertTrue(r.isGreaterThanOrEqualTo(0) && r.isLessThanOrEqualTo(1) );
        }
    }

    @Test
    public void randomStringsGeneration(){
        RandomGenerator<String> a = RandomGeneratorProvider.secure().strings().withLengthBetween(3, 6).alphanumeric();
        RandomGenerator<String> b = RandomGeneratorProvider.secure().strings().withLengthUpTo(8).numeric();
        RandomGenerator<String> c = RandomGeneratorProvider.secure().strings().withLength(8).alphabetic();
        var list =RandomGeneratorProvider.standard().strings().withLength(8).alphanumeric().stream().limit(10).toList();

        assertEquals(10, list.size());
    }

    @Test
    public void randomUuidGeneration(){

        var list = RandomGeneratorProvider.seedable(13).uuids().stream().limit(2).toList();

        assertEquals(2, list.size());
        assertEquals(UUID.fromString("5862d4ba-5828-4054-ae31-d2717d634405"), list.get(0));
        assertEquals(UUID.fromString("47f1200d-7c21-40c3-8a00-57b81a104429"), list.get(1));

        var uuid = UUID.randomUUID();

        assertEquals(uuid.variant(), list.get(0).variant());
        assertEquals(uuid.variant(), list.get(1).variant());

        var keysList = RandomGeneratorProvider.seedable(13).uuids()
                .generateNext(r -> Key.of(TestConcept.class, r.next().toString()))
                .stream()
                .limit(2)
                .toList();

        assertEquals(2, keysList.size());
        assertEquals("5862d4ba-5828-4054-ae31-d2717d634405", keysList.get(0).stringValue());
        assertEquals("47f1200d-7c21-40c3-8a00-57b81a104429", keysList.get(1).stringValue());

    }
}

interface TestConcept extends Concept{}