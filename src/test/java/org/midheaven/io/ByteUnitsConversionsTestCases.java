package org.midheaven.io;

import org.junit.jupiter.api.Test;
import org.midheaven.math.Rational;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteUnitsConversionsTestCases {
    
    @Test
    public void conversion(){
        
        var size = ByteContentSize.of(3 , ByteContentSizeUnit.MEGA_BYTE);
        
        assertEquals(Rational.of(3).times( 1024).times(1024) , size.in(ByteContentSizeUnit.BYTE));
        assertEquals(Rational.of(3).times( 1024) , size.in(ByteContentSizeUnit.KILO_BYTE));
        assertEquals(Rational.of(3 ) ,  size.in(ByteContentSizeUnit.MEGA_BYTE));
        assertEquals(Rational.of(3 , 1024) , size.in(ByteContentSizeUnit.GIGA_BYTE));
        assertEquals(Rational.of(3 , 1024).over(1024) , size.in(ByteContentSizeUnit.TERA_BYTE));
    }
}
