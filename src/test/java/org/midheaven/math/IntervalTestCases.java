package org.midheaven.math;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntervalTestCases {

    @Test
    public void boundariesAreConsistent(){

        var minor = new ActualBoundary<Integer>(Comparator.naturalOrder(), true, true, null);
        var min = new ActualBoundary<Integer>(Comparator.naturalOrder(), true, true, 1);
        var max = new ActualBoundary<Integer>(Comparator.naturalOrder(), false, true, 4);
        var major = new ActualBoundary<Integer>(Comparator.naturalOrder(), false, true, null);

        assertTrue(minor.isEqualTo(minor));
        assertTrue(major.isEqualTo(major));
        assertTrue(min.isEqualTo(min));
        assertTrue(max.isEqualTo(max));

        assertFalse(min.isGreaterThan(max));

        assertTrue(min.isLessThan(max));
        assertTrue(minor.isLessThan(min), "Minor is no less than mim");
        assertTrue(major.isGreaterThan(max));
        assertTrue(max.isLessThan(major), "Max is no less than major");
    }

    @Test
    public void emptyIsConsistent(){
        Interval<String> empty = Interval.empty();

        assertTrue(empty.isEmpty());
        assertFalse(empty.contains("A"));
        assertTrue(empty.contains(empty));
        assertFalse(empty.intersects(empty));

        Interval<String> alsoEmpty = Interval.ranging(String.class).from("A").exclusive().to("A").exclusive();

        assertTrue(alsoEmpty.isEmpty());
        assertFalse(alsoEmpty.contains("A"));
        assertTrue(alsoEmpty.contains(empty));
        assertFalse(alsoEmpty.intersects(empty));

        assertEquals(empty, alsoEmpty);
    }

    @Test
    public void nonEmptyIsConsistent(){
        Interval<Integer> a = Interval.ranging(Integer.class).between(2, 8);
        Interval<Integer> b = Interval.ranging(Integer.class).between(3, 9);

        assertFalse(a.isEmpty());
        assertFalse(a.contains(1));
        assertTrue(a.contains(2));
        assertTrue(a.contains(5), a + " does not contain " + 5);
        assertTrue(a.contains(3));
        assertTrue(a.contains(8));
        assertFalse(a.contains(9));
        assertTrue(a.contains(a));
        assertTrue(a.contains(Interval.empty()));
        assertFalse(Interval.empty().contains(a));
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    public void unboundIsConsistent(){
        Interval<Integer> a = Interval.ranging(Integer.class).fromInfinity().toInfinity();
        Interval<Integer> b = Interval.ranging(Integer.class).between(3, 9);

        assertFalse(a.isEmpty());
        assertTrue(a.contains(1));
        assertTrue(a.contains(2));
        assertTrue(a.contains(5));
        assertTrue(a.contains(3));
        assertTrue(a.contains(8));
        assertTrue(a.contains(9));
        assertTrue(a.contains(a));
        assertTrue(a.contains(Interval.empty()));
        assertFalse(Interval.empty().contains(a));
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    public void uppedBoundedIsConsistent(){
        Interval<Integer> a = Interval.ranging(Integer.class).fromInfinity().to(8).inclusive();
        Interval<Integer> b = Interval.ranging(Integer.class).between(3, 9);

        assertFalse(a.isEmpty());
        assertTrue(a.contains(1));
        assertTrue(a.contains(2));
        assertTrue(a.contains(5));
        assertTrue(a.contains(3));
        assertTrue(a.contains(8));
        assertFalse(a.contains(9));
        assertTrue(a.contains(a));
        assertTrue(a.contains(Interval.empty()));
        assertFalse(Interval.empty().contains(a));
        assertTrue(b.intersects(a));
        assertTrue(a.intersects(b));

    }

    @Test
    public void lowerBoundedIsConsistent(){
        Interval<Integer> a = Interval.ranging(Integer.class).from(2).inclusive().toInfinity();
        Interval<Integer> b = Interval.ranging(Integer.class).between(3, 9);

        assertFalse(a.isEmpty());
        assertFalse(a.contains(1));
        assertTrue(a.contains(2));
        assertTrue(a.contains(5));
        assertTrue(a.contains(3));
        assertTrue(a.contains(8));
        assertTrue(a.contains(9));
        assertTrue(a.contains(a));
        assertTrue(a.contains(Interval.empty()));
        assertFalse(Interval.empty().contains(a));
        assertTrue(a.intersects(a), "Interval does not intersects itself");
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));

    }
}
