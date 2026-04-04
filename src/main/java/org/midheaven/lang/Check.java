package org.midheaven.lang;

import java.util.Collection;

/**
 * Represents Check.
 */
public class Check {
    
    /**
     * Performs argumentIsNotNull.
     * @param value the value value
     * @return the result of argumentIsNotNull
     */
    public static <T> @NotNullable T argumentIsNotNull(@Nullable T value){
        if (value == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotNull.
     * @param value the value value
     * @param argumentName the argumentName value
     * @return the result of argumentIsNotNull
     */
    public static <T> @NotNullable T argumentIsNotNull(@Nullable T value, @Nullable String argumentName){
        if (value == null){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotBlank.
     * @param value the value value
     * @return the result of argumentIsNotBlank
     */
    public static @NotNullable CharSequence argumentIsNotBlank(@Nullable CharSequence value){
        if (value == null || value.isEmpty() || value.toString().isBlank()){
            throw new IllegalArgumentException("Argument cannot be null, nor blank");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotBlank.
     * @param value the value value
     * @param argumentName the argumentName value
     * @return the result of argumentIsNotBlank
     */
    public static @NotNullable CharSequence argumentIsNotBlank(@Nullable CharSequence value, @NotNullable String argumentName){
        if (value == null || value.isEmpty() || value.toString().isBlank()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor blank");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotEmpty.
     * @param value the value value
     * @param argumentName the argumentName value
     * @return the result of argumentIsNotEmpty
     */
    public static  @NotNullable CharSequence argumentIsNotEmpty(@Nullable CharSequence value, @NotNullable String argumentName){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotEmpty.
     * @param value the value value
     * @return the result of argumentIsNotEmpty
     */
    public static <T, C extends Collection<T>> @NotNullable C argumentIsNotEmpty(@Nullable C value){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument cannot be null, nor empty");
        }
        return value;
    }
    
    /**
     * Performs argumentIsNotEmpty.
     * @param value the value value
     * @param argumentName the argumentName value
     * @return the result of argumentIsNotEmpty
     */
    public static <T, C extends Collection<T>> @NotNullable C argumentIsNotEmpty(@Nullable C value, @NotNullable String argumentName){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
}
