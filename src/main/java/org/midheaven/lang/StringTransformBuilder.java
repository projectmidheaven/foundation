package org.midheaven.lang;


public interface StringTransformBuilder extends Strings.Transform {
    
    StringTransformBuilder thenReplace(char match, char replacement);
    StringTransformBuilder thenReplaceAll(CharSequence match, CharSequence replacement);
    StringTransformBuilder thenTrim();
    StringTransformBuilder thenLowerCase();
    StringTransformBuilder thenRaiseCase();
}
