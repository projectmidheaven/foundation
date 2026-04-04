package org.midheaven.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.midheaven.collections.Enumerable;
import org.midheaven.collections.Sequence;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class RationalTestCases {

    @Test
    public void hashCodesAreTheSame(){
        var a = Rational.of(32, 13);
        var b = Rational.of(BigInteger.valueOf(32), BigInteger.valueOf(13));

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void isParseable(){
        assertEquals(Rational.of(12345, 100) , Rational.parse(Rational.parse("123.45").toString()));
        assertEquals(Rational.ONE , Rational.parse("123 / 123"));
        assertEquals(Rational.ONE , Rational.parse(" 2 / 2 "));

        assertThrows(IllegalArgumentException.class, () ->  Rational.parse(" 1 / 1.5 "));
    }

    @Test
    public void sumIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(a, a.plus(Rational.ZERO));
        assertEquals(a, Rational.ZERO.plus(a));
        assertEquals(b, b.plus(Rational.ZERO));
        assertEquals(b, Rational.ZERO.plus(b));
        assertEquals(Rational.of(133, 221), a.plus(b));
    }

    @Test
    public void subtractionIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(a, a.minus(Rational.ZERO));
        assertEquals(a.negate(), Rational.ZERO.minus(a));
        assertEquals(b, b.minus(Rational.ZERO));
        assertEquals(b.negate(), Rational.ZERO.minus(b));
        assertEquals(Rational.of(3, 221), a.minus(b));
    }

    @Test
    public void multiplicationIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(Rational.ZERO, a.times(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.times(a));
        assertEquals(Rational.ZERO, b.times(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.times(b));
        assertEquals(Rational.of(20, 221), a.times(b));
    }
    
    @Test
    public void inversionIsCorrect(){
        var a = Rational.of(4);
        var b = Rational.of(BigInteger.valueOf(-5));
        var c = Rational.of(1, 3);
        
        var bigInteger = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        var d = Rational.of(bigInteger);
        var g = Rational.of(BigInteger.TWO, BigInteger.valueOf(3));
        
        assertEquals(Rational.of(1, 4), a.invert());
        assertEquals(Rational.of(-1, 5), b.invert());
        assertEquals(Rational.of(3), c.invert());
        assertEquals(Rational.of(BigInteger.ONE,bigInteger ), d.invert());
        assertEquals(Rational.of(BigInteger.valueOf(3), BigInteger.TWO), g.invert());
        
        assertEquals(Rational.of(1, 9), c.square());
        assertEquals(Rational.of(1, 27), c.cube());
        
        assertEquals(Rational.ONE, b.times(b.invert()));
        assertEquals(Rational.ONE, a.times(a.invert()));
        assertEquals(Rational.ONE, c.times(c.invert()));
        assertEquals(Rational.ONE, d.times(d.invert()));
        assertEquals(Rational.ONE, g.times(g.invert()));
        
        assertEquals(Rational.ONE, b.invert().times(b));
        assertEquals(Rational.ONE, a.invert().times(a));
        assertEquals(Rational.ONE, c.invert().times(c));
        assertEquals(Rational.ONE, d.invert().times(d));
        assertEquals(Rational.ONE, g.invert().times(g));
    }

    @Test
    public void divisionIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertThrows(ArithmeticException.class, () -> a.over(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.over(a));
        assertThrows(ArithmeticException.class, () -> b.over(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.over(b));
        assertEquals(Rational.of(68, 65), a.over(b));
    }
    
    @Test
    public void raiseToPowerIsCorrect(){
        var a = Rational.of(2, 11);
        
        assertEquals( Rational.of(4, 121) , a.raisedTo(2));
        assertEquals( Rational.of(121, 4) , a.raisedTo(-2));
    }
    
    @Test
    public void enumerableSum(){
        var rationalEnumerable = Enumerable.iterate(Rational.ONE, it -> it.plus(1))
                .map(MultiplicationGroup::invert)
                .limit(10)
                .with(Rational.arithmetic());
      
        assertEquals(Rational.of(7381,2520),  rationalEnumerable.sum());
        assertEquals(Rational.of(7381,25200),  rationalEnumerable.average());

    }

    @Test
    public void enumerableAllRationalSum(){
        var rationalSequence = Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).with(Rational.arithmetic());

        var sum = rationalSequence.sum();

        var avg = rationalSequence.average();

        assertEquals(sum.over(7) , avg);

        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).reduce(Rational.ZERO, Rational::plus));
        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).reduce(Rational::plus).orNull());
    }

    @Test
    public void floorIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(21, 17);
        var c = Rational.of(4);
        var d = Rational.of(-4, 13);
        var f = Rational.of(-21, 17);

        assertEquals(Rational.ZERO, a.floor());
        assertEquals(Rational.ONE, b.floor());
        assertEquals(c, c.floor());
        assertEquals(Rational.from(Math.floor(d.toBigDecimal().doubleValue())), d.floor());
        assertEquals(Rational.from(Math.floor(f.toBigDecimal().doubleValue())), f.floor());
    }

    @Test
    public void ceilIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(21, 17);
        var c = Rational.of(4);
        var d = Rational.of(-4, 13);
        var f = Rational.of(-21, 17);

        assertEquals(Rational.ONE, a.ceil());
        assertEquals(Rational.of(2), b.ceil());
        assertEquals(c, c.ceil());
        assertEquals(Rational.ZERO, d.ceil());
        assertEquals(Rational.ONE.negate(), f.ceil());
    }
    
    @Test
    @Timeout(value = 3, unit = TimeUnit.MINUTES)
    public void approximationEnumerable(){
        var target = Rational.PI.square().over(6);
        
        var value = Enumerable.iterate(1 , i -> i + 1)
                        .limit(10_000)
                        .map(i -> Rational.of(1, i).square())
                        .with(Rational.arithmetic())
                        .sum();
      
        var diff = target.minus(value);
        assertTrue(diff.isLessThan(Rational.parse("0.0001")) , "Difference is " + diff.toBigDecimal());
        
        
    }
}
