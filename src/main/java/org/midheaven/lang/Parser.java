package org.midheaven.lang;

import java.util.function.Function;

public abstract class Parser<T> {
    
    public static <X> Parser<X> lowerCode(Function<String , X> factory) {
        return new Parser<X>() {
            @Override
            public String removeIllegalChars(String text) {
                return Strings.Transform.create()
                           .thenRetainAlphabeticOnly()
                           .thenLowerCase()
                           .apply(text);
            }
            
            @Override
            public X wrap(String text) {
                return factory.apply(text);
            }
        };
    }
    
    public static <X> Parser<X> upperCode(Function<String , X> factory) {
        return new Parser<X>() {
            @Override
            public String removeIllegalChars(String text) {
                return Strings.Transform.create()
                           .thenRetainAlphabeticOnly()
                           .thenRaiseCase()
                           .apply(text);
            }
            
            @Override
            public X wrap(String text) {
                return factory.apply(text);
            }
        };
    }
    
    public static <X> Parser<X> numericCode(Function<String , X> factory) {
        return new Parser<X>() {
            @Override
            public String removeIllegalChars(String text) {
                return Strings.Transform.create()
                           .thenRetainNumericsOnly()
                           .apply(text);
            }
            
            @Override
            public X wrap(String text) {
                return factory.apply(text);
            }
        };
    }
    
    public final @Nullable T parse(@Nullable String text){
        if (Strings.isBlank(text)){
            return null;
        } else if (!matchesExpectedPattern(text)){
            throw new ParsingException("Cannot parse '" + text + "'. Incorrect pattern");
        } else if (containsIllegalChars(text)){
            throw new ParsingException("Cannot parse '" + text + "'. Illegal characters present, or incorrect casing");
        }
        
        return wrap(text);
    }
    
    public final @NotNullable Maybe<T> tryParse(@Nullable String text){
        return Strings.filled(text)
                   .map(this::removeIllegalChars)
                   .map(this::wrap);
    }
    
    protected boolean matchesExpectedPattern(String text){
        return true;
    }
    
    protected boolean containsIllegalChars(String text){
        return !removeIllegalChars(text).equals(text);
    }
    
    protected abstract String removeIllegalChars(String text);
    protected abstract T wrap(String text);
    
}
