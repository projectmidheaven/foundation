package org.midheaven.collections;

import org.junit.jupiter.api.Test;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Numbers;
import org.midheaven.math.Rational;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumerableTestCases {

    Assortment<Integer> assortment = Sequence.builder().of(1,2,3);

    @Test
    public void enumerableFirst(){
        var possibleFirst = assortment.map(it ->  it * 2).first();

        assertTrue(possibleFirst.isPresent());
        assertEquals(2, possibleFirst.get());

        assertEquals(Maybe.none(), Sequence.builder().of(null,null,1).first());
        assertEquals(Maybe.none(), Sequence.builder().of(null,1).first());
    }

    @Test
    public void enumerableFilter(){
        var possibleFirst  = Sequence.builder().of(1,2,3,4,5,6,7,8,9)
                .filter(it -> it > 5)
                .filter(it -> it % 2 == 0).first();

        assertTrue(possibleFirst.isPresent());
        assertEquals(6, possibleFirst.get());
    }

    @Test
    public void enumerableCount(){
        var count = assortment.filter(it -> it % 2 == 0).count().toLong();

        assertEquals(1,count);
    }

    @Test
    public void enumerableMap(){
        var sequence = assortment.map(it -> it * 5).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(3, sequence.count().toInt());
        assertEquals(5, sequence.getAt(0).orElse(-1));
        assertEquals(10, sequence.getAt(1).orElse(-1));
        assertEquals(15, sequence.getAt(2).orElse(-1));
    }

    @Test
    public void enumerableFlatMap(){
        var sequence = assortment.flatMap(it -> Sequence.builder().of(it, it + 1)).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(6, sequence.count().toInt());
        assertEquals(1, sequence.getAt(0).orElse(-1));
        assertEquals(2, sequence.getAt(1).orElse(-1));
        assertEquals(2, sequence.getAt(2).orElse(-1));
        assertEquals(3, sequence.getAt(3).orElse(-1));
        assertEquals(3, sequence.getAt(4).orElse(-1));
        assertEquals(4, sequence.getAt(5).orElse(-1));
    }

    @Test
    public void enumerableDistinct(){
        var distinctEnumerable = assortment.flatMap(it -> Sequence.builder().of(it, it + 1)).distinct();

        distinctEnumerable.toSequence();

        var sequence = distinctEnumerable.toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(4, sequence.count().toInt());
        assertEquals(1, sequence.getAt(0).orElse(-1));
        assertEquals(2, sequence.getAt(1).orElse(-1));
        assertEquals(3, sequence.getAt(2).orElse(-1));
        assertEquals(4, sequence.getAt(3).orElse(-1));
    }

    @Test
    public void enumerableSorted(){
        var sequence = Sequence.builder().of(2,1,3,7,4,9,2).sorted(Comparator.naturalOrder()).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(7, sequence.count().toInt());
        assertEquals(1, sequence.getAt(0).orElse(-1));
        assertEquals(2, sequence.getAt(1).orElse(-1));
        assertEquals(2, sequence.getAt(2).orElse(-1));
        assertEquals(3, sequence.getAt(3).orElse(-1));
    }

    @Test
    public void enumerableUnboundLimitShouldThrow() {
        assertThrows(IllegalStateException.class, () -> Enumerable.iterate(0, it -> it + 1 ).toSequence());
        assertThrows(IllegalStateException.class, () -> Enumerable.iterate(0, it -> it + 1 ).allMatch(it -> it > -1));
        assertThrows(IllegalStateException.class, () -> Enumerable.iterate(0, it -> it + 1 ).anyMatch(it -> it == -1));
    }

    @Test
    public void enumerableLimit(){
        var sequence = Enumerable.iterate(0, it -> it + 1).limit(6).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(6, sequence.count().toInt());
        assertEquals(0, sequence.getAt(0).orElse(-1));
        assertEquals(1, sequence.getAt(1).orElse(-1));
        assertEquals(2, sequence.getAt(2).orElse(-1));
        assertEquals(3, sequence.getAt(3).orElse(-1));
        assertEquals(4, sequence.getAt(4).orElse(-1));
        assertEquals(5, sequence.getAt(5).orElse(-1));
    }

    @Test
    public void enumerableGroup(){
        var sequence = Enumerable.iterate(0, it -> it + 1).limit(10).groupBy(it -> it % 2 == 0).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(2, sequence.count().toInt());
        
    }
    @Test
    public void enumerableZip(){
        var a = Enumerable.iterate(0, it -> it + 1).limit(10);
        var b = Enumerable.iterate(0, it -> it + 1).limit(15);

        var sequence = a.zip(b, (x,y) -> x * y).toSequence();

        assertFalse(sequence.isEmpty());
        assertEquals(10, sequence.count().toInt());

        var even = a.zip(b, (x,y) -> x * y).filter(it -> it % 2 == 0);

        assertEquals(5, even.count().toInt());
        assertFalse(even.isEmpty());
        assertTrue(even.any());
    }

    @Test
    public void enumerableAnyIsEmpty(){
        var finite = Enumerable.iterate(0, it -> it + 1).limit(10);

        assertEquals(10, finite.count().toInt());
        assertFalse(finite.isEmpty());
        assertTrue(finite.any());

        var undetermined = Enumerable.iterate(0, it -> it + 1).limit(10).filter(it -> it % 2 == 0);

        assertEquals(5, undetermined.count().toInt());
        assertFalse(undetermined.isEmpty());
        assertTrue(undetermined.any());

        var infinite = Enumerable.iterate(0, it -> it + 1);

        assertThrows(IllegalStateException.class, () -> infinite.count().toInt());
        assertFalse(infinite.isEmpty());
        assertTrue(infinite.any());
    }

    @Test
    public void enumerableAssociate(){
        var association = Enumerable.iterate(0, it -> it + 1).limit(10)
                .associate(it ->   String.valueOf((char)('a' + it)));

        var sequence = association.toSequence();
        var mapping = association.toAssociation();

        assertFalse(sequence.isEmpty());
        assertEquals(10, sequence.count().toInt());

        assertFalse(mapping.isEmpty());
        assertEquals(10, mapping.count().toInt());
        assertEquals(1, mapping.getValue("b").orElse(-1));

        var otherMapping = association.mapKey(key -> "x"+ key).toAssociation();

        assertFalse(otherMapping.isEmpty());
        assertEquals(10, otherMapping.count().toInt());
        assertEquals(1, otherMapping.getValue("xb").orElse(-1));
    }



    @Test
    public void enumerableAnyMatch(){
        var sequence = Sequence.builder().of(1,2,3,4,5,6,7); //Enumerable.iterate(0, it -> it + 1);

        assertTrue(sequence.anyMatch(it -> it == 4));
        assertFalse(sequence.anyMatch(it -> it == -1));

    }

    @Test
    public void enumerableAllMatch(){
        var sequence = Sequence.builder().of(1,2,3,4,5,6,7); //Enumerable.iterate(0, it -> it + 1);

        assertTrue(sequence.allMatch(it -> it > -1));
        assertFalse(sequence.allMatch(it -> it == 0));

    }

    @Test
    public void enumerableAllSum(){
        var integerSequence = Sequence.builder().of(1,2,3,4,5,6,7).with(Numbers.Integers.arithmetic());

        var sum = integerSequence.sum();

        var avg = integerSequence.average();

        assertEquals(Rational.of(sum, 7) , avg);

        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).reduce(0, Integer::sum));
        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).reduce(Integer::sum).orElse(-1));
    }



    @Test
    public void enumerableAllMin(){
        assertEquals(1 ,  Sequence.builder().of(1,2,3,4,5,6,7).minimum(Comparator.naturalOrder()).orElse(-1));
        assertEquals( 7,  Sequence.builder().of(1,2,3,4,5,6,7).minimum(Comparator.<Integer>naturalOrder().reversed()).orElse(-1));
    }

    @Test
    public void enumerableAllMax(){
        assertEquals(7 ,  Sequence.builder().of(1,2,3,4,5,6,7).maximum((Comparator.naturalOrder())).orElse(-1));
        assertEquals( 1,  Sequence.builder().of(1,2,3,4,5,6,7).maximum(Comparator.<Integer>naturalOrder().reversed()).orElse(-1));
    }

    @Test
    public void union(){
        var a = Association.builder().resizable().empty();
        a.putValue(1, "A");
        a.putValue(2, "B");
        a.putValue(3, "C");

        var b = Association.builder().resizable().empty();
        b.putValue(1, "A1");
        b.putValue(2, "B2");
        b.putValue(4, "D");

        var c = Association.builder().resizable().empty();
        c.putValue(1, "A");
        c.putValue(2, "B");
        c.putValue(3, "C");
        c.putValue(4, "D");

        assertEquals(c, a.union(b, (x,y) -> x));
        assertEquals(c, b.union(a, (x,y) -> y));
    }

}
