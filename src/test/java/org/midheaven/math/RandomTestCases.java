package org.midheaven.math;

import org.junit.jupiter.api.Test;
import org.midheaven.keys.Concept;
import org.midheaven.keys.Key;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomTestCases {

    @Test
    public void generateRandomValues(){
        var a = RandomGenerator.secure().doubles().next();
        var b = RandomGenerator.standard().doubles().next();
        var u = RandomGenerator.standard().doubles().between(0d,3d).next();
        var i = RandomGenerator.standard().integers().betweenIncluding(0,3).next();

        RandomGenerator<Key<Concept>> d = RandomGenerator.standard().generate(r -> Key.of(Concept.class, r.bigIntegers().next().toString()));
        RandomGenerator<Key<Concept>> m = RandomGenerator.standard().bigIntegers().generateNext(r -> Key.of(Concept.class, r.next().toString()));

        var list = RandomGenerator.standard().doubles().stream().limit(10).toList();

        assertEquals(10, list.size());
    }

    @Test
    public void randomStringsGeneration(){
        RandomGenerator<String> a = RandomGenerator.secure().strings().withLengthBetween(3, 6).alphanumeric();
        RandomGenerator<String> b = RandomGenerator.secure().strings().withLengthUpTo(8).numeric();
        RandomGenerator<String> c = RandomGenerator.secure().strings().withLength(8).alphabetic();
        var list =RandomGenerator.standard().strings().withLength(8).alphanumeric().stream().limit(10).toList();

        assertEquals(10, list.size());
    }

    @Test
    public void randomUuidGeneration(){

        var list = RandomGenerator.seedable(13).uuids().stream().limit(2).toList();

        assertEquals(2, list.size());
        assertEquals(UUID.fromString("5862d4ba-5828-4054-ae31-d2717d634405"), list.get(0));
        assertEquals(UUID.fromString("47f1200d-7c21-40c3-8a00-57b81a104429"), list.get(1));

        var uuid = UUID.randomUUID();

        assertEquals(uuid.variant(), list.get(0).variant());
        assertEquals(uuid.variant(), list.get(1).variant());

        var keysList = RandomGenerator.seedable(13).uuids()
                .generateNext(r -> Key.of(TestConcept.class, r.next().toString()))
                .stream()
                .limit(2)
                .toList();

        assertEquals(2, keysList.size());
        assertEquals("5862d4ba-5828-4054-ae31-d2717d634405", keysList.get(0).value());
        assertEquals("47f1200d-7c21-40c3-8a00-57b81a104429", keysList.get(1).value());

    }
}

interface TestConcept extends Concept{}