package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Base32EncoderTestCases {
    
    @Test
    public void CrockfordEncoding (){
        
        assertEquals("91JPRV3F" , Base32.crockford().encoder().encode("Hello"));
        assertEquals("Hello" , Base32.crockford().decoder().decodeToString("91JPRV3F"));
        assertEquals("Hello" , Base32.crockford().decoder().decodeToString("9iJPRV3F"));
    }
    
}
