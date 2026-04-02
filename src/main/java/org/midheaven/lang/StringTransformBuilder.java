package org.midheaven.lang;


public interface StringTransformBuilder extends Strings.Transform {
    
    StringTransformBuilder thenReplace(char match, char replacement);
    StringTransformBuilder thenReplaceAll(CharSequence match, CharSequence replacement);
    StringTransformBuilder thenTrim();
    StringTransformBuilder thenLowerCase();
    StringTransformBuilder thenRaiseCase();
    StringTransformBuilder thenRemoveAllNumerics();
    StringTransformBuilder thenRetainNumericsOnly();
    StringTransformBuilder thenRemoveAllAlphabetic();
    StringTransformBuilder thenRetainAlphabeticOnly();
    StringTransformBuilder thenRemoveAllSymbols();
    StringTransformBuilder thenRemoveAllSymbolsExcept(char ... symbols);
    StringTransformBuilder thenRetainSymbolsOnly();

}
