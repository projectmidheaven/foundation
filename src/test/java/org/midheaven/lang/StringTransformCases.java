package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringTransformCases {

    @Test
    public void transform(){
        
        assertEquals(
            "herro worrd" ,
            Strings.Transform.create()
                .thenRaiseCase()
                .thenReplace('L', 'R')
                .thenLowerCase()
                .thenTrim()
                .apply("   Hello World   ")
        );
        
        assertEquals(
            "HERRO WORRD" ,
            Strings.Transform.create()
                .thenLowerCase()
                .thenReplace('l', 'r')
                .thenRaiseCase()
                .thenTrim()
                .apply("   Hello World   ")
        );
    }

}
