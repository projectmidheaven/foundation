package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class StringTransformTestCases {

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

        assertEquals(
            "Hello World" ,
            Strings.Transform.create()
                .thenRemoveAllNumerics()
                .thenTrim()
                .apply("   1He3ll0o Wo0rld4  6 ")
        );

        assertEquals(
            "Hello World" ,
            Strings.Transform.create()
                .thenRemoveAllNumerics()
                .thenTrim()
                .apply("   1He3ll0o Wo0rld4  6 ")
        );

        assertEquals(
            "130046" ,
            Strings.Transform.create()
                .thenLowerCase()
                .thenRetainNumericsOnly()
                .thenTrim()
                .apply("   1He3ll0o Wo0rld4  6 ")
        );

        assertEquals(
            "Hello World" ,
            Strings.Transform.create()
                .thenRemoveAllSymbols()
                .thenTrim()
                .apply("   !!*¨Hello, -World-?   ")
        );

        assertEquals(
            "Hello -World-" ,
            Strings.Transform.create()
                .thenRemoveAllSymbolsExcept('-')
                .thenTrim()
                .apply("   !!*¨Hello, -World-?   ")
        );

        assertEquals(
            "pt-BR" ,
            Strings.Transform.create()
                .thenRemoveAllNumerics()
                .thenRemoveAllSymbolsExcept('-', '_')
                .apply("pt-BR")
        );
        
    }

}
