package org.midheaven.lang;

import java.util.function.Function;

/**
 * Represents Parser.
 * @param <T> the type being parsed
 */
public abstract class Parser<T> {
    
    /**
     * Performs lowerCode.
     * @param factory the factory value
     * @return the result of lowerCode
     */
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
    
    /**
     * Performs upperCode.
     * @param factory the factory value
     * @return the result of upperCode
     */
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
    
    /**
     * Performs numericCode.
     * @param factory the factory value
     * @return the result of numericCode
     */
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
    
    /**
     * Parses the provided value.
     * @param text the text value
     * @return the result of parse
     */
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
    
    /**
     * Performs tryParse.
     * @param text the text value
     * @return the result of tryParse
     */
    public final @NotNullable Maybe<T> tryParse(@Nullable String text){
        return Strings.filled(text)
                   .map(this::removeIllegalChars)
                   .map(this::wrap);
    }
    
    /**
     * Checks whether matches Expected Pattern.
     * @param text the text value
     * @return the result of matchesExpectedPattern
     */
    protected boolean matchesExpectedPattern(String text){
        return true;
    }
    
    /**
     * Checks whether contains Illegal Chars.
     * @param text the text value
     * @return the result of containsIllegalChars
     */
    protected boolean containsIllegalChars(String text){
        return !removeIllegalChars(text).equals(text);
    }
    
    /**
     * Performs remove Illegal Chars.
     * @param text the text value
     * @return the result of removeIllegalChars
     */
    protected abstract String removeIllegalChars(String text);
    /**
     * Performs wrap.
     * @param text the text value
     * @return the result of wrap
     */
    protected abstract T wrap(String text);
    
}
