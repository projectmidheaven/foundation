package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparableTestCases {

    @Test
    public void nullIsMinimum(){
        assertEquals(1, Comparables.compare(4, 3));
        assertEquals(0, Comparables.compare(4, 4));
        assertEquals(0, Comparables.compare(null, null));
        assertEquals(-1, Comparables.compare(null, 4)); // Null is less that other values
        assertEquals(1, Comparables.compare(4, null));
    }

    @Test
    public void findMinCorrectly(){
        assertEquals(2, Comparables.min(4, 3, 2, 6,  7));
        assertEquals(null, Comparables.min(4, 3, 2, null, 1, 7));
        assertEquals(3, Comparables.min(4, 3));
        assertEquals(null, Comparables.min((Integer)null, null));
        assertEquals(Maybe.of(2), Comparables.min(List.of(4, 3, 2, 6,  7)));
        assertTrue(Comparables.min(Arrays.asList(4, 3, 2, 6, null, 7)).isAbsent());
        assertTrue(Comparables.min(List.<Integer>of()).isAbsent());
    }

    @Test
    public void findMaxCorrectly(){
        assertEquals(6, Comparables.max(4, 3, 2, 6,  5));
        assertEquals(7, Comparables.max(4, 3, 2, null, 1, 7));
        assertEquals(4, Comparables.max(4, 3));
        assertEquals(null, Comparables.max((Integer)null, null));
        assertEquals(Maybe.of(7), Comparables.max(List.of(4, 3, 2, 6,  7)));
        assertTrue(Comparables.max(Arrays.asList(4, 3, 2, 6, null, 7)).isPresent());
        assertTrue(Comparables.max(Arrays.<Integer>asList(null, null)).isAbsent());
        assertTrue(Comparables.max(List.<Integer>of()).isAbsent());
    }
}
