package org.midheaven.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AngleTestCase {
    
    @Test
    public void arithmetic(){
        
        var a = Angle.ofDegrees(10);
        var b = Angle.ofDegrees(20);
        
        assertEquals(a.plus(a),b);
        assertEquals(b.minus(a),a);
        assertEquals(b.over(2),a);
        assertEquals(a.times(2),b);
    }
    
    @Test
    public void reduction(){
        
        var a = Angle.ofDegrees(10);
        var b = Angle.ofDegrees(370);
        
        assertEquals(b,a);
        assertEquals(Angle.ofDegrees(350), b.negate());
    }
}
