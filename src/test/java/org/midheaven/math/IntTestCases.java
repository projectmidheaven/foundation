package org.midheaven.math;

import org.junit.jupiter.api.Test;
import org.midheaven.collections.Enumerable;
import org.midheaven.collections.Sequence;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntTestCases {

    @Test
    public void hashCodesAreTheSame(){
        var a = Int.of(32);
        var b = Int.of(BigInteger.valueOf(32));

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void isParseable(){
        assertEquals(Int.of(123) , Int.parse(Int.parse("123").toString()));

        assertThrows(IllegalArgumentException.class, () ->  Int.parse("1.5"));
    }

    @Test
    public void dividerAndRemainder(){
        var r = Int.of(123).divideAndRemainder(Int.of(10));

        assertEquals(Int.of(12) ,  r.divider());
        assertEquals(Int.of(3) ,  r.remainder());

        r = Int.of(2).divideAndRemainder(Int.of(10));

        assertEquals(Int.of(0) ,  r.divider());
        assertEquals(Int.of(2) ,  r.remainder());
    }

    @Test
    public void sumIsCorrect(){
        var a = Int.of(4);
        var b = Int.of(BigInteger.valueOf(5));

        assertEquals(a, a.plus(Int.ZERO));
        assertEquals(a, Int.ZERO.plus(a));
        assertEquals(b, b.plus(Int.ZERO));
        assertEquals(b, Int.ZERO.plus(b));
        assertEquals(Int.of(9), a.plus(b));
    }

    @Test
    public void subtractionIsCorrect(){
        var a = Int.of(13);
        var b = Int.of(BigInteger.valueOf(5));

        assertEquals(a, a.minus(Int.ZERO));
        assertEquals(a.negate(), Int.ZERO.minus(a));
        assertEquals(b, b.minus(Int.ZERO));
        assertEquals(b.negate(), Int.ZERO.minus(b));
        assertEquals(Int.of(8), a.minus(b));
    }

    @Test
    public void multiplicationIsCorrect(){
        var a = Int.of( 13);
        var b = Int.of(BigInteger.valueOf(17));

        assertEquals(Int.ZERO, a.times(Int.ZERO));
        assertEquals(Int.ZERO, Int.ZERO.times(a));
        assertEquals(Int.ZERO, b.times(Int.ZERO));
        assertEquals(Int.ZERO, Int.ZERO.times(b));
        assertEquals(Int.of(221), a.times(b));
    }

    @Test
    public void divisionIsCorrect(){
        var a = Int.of(4);
        var b = Int.of(BigInteger.valueOf(5));

        assertThrows(ArithmeticException.class, () -> a.over(Int.ZERO));
        assertEquals(Rational.ZERO, Int.ZERO.over(a));
        assertThrows(ArithmeticException.class, () -> b.over(Int.ZERO));
        assertEquals(Rational.ZERO, Int.ZERO.over(b));
        assertEquals(Rational.of(4, 5), a.over(b));
    }

    @Test
    public void enumerableSum(){
        var enumerable = Enumerable.iterate(Int.ONE, it -> it.plus(1))
                .limit(30)
                .with(Int.arithmetic());

        assertEquals(Int.of(465),  enumerable.sum());
        assertEquals(Rational.of(31, 2),  enumerable.average());

    }

    @Test
    public void enumerableAllIntSum(){
        var IntSequence = Sequence.builder().of(1,2,3,4,5,6,7).map(Int::of).with(Int.arithmetic());

        var sum = IntSequence.sum();

        var avg = IntSequence.average();

        assertEquals(sum.over(7) , avg);

        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Int::of).reduce(Int.ZERO, Int::plus));
        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Int::of).reduce(Int::plus).orElse(null));
    }

}
