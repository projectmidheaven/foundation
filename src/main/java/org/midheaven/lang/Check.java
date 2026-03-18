package org.midheaven.lang;

import java.util.Collection;

public class Check {
    
    public static <T> @NotNullable T argumentIsNotNull(@Nullable T value){
        if (value == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        return value;
    }
    
    public static <T> @NotNullable T argumentIsNotNull(@Nullable T value, @Nullable String argumentName){
        if (value == null){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null");
        }
        return value;
    }
    
    public static @NotNullable CharSequence argumentIsNotBlank(@Nullable CharSequence value){
        if (value == null || value.isEmpty() || value.toString().isBlank()){
            throw new IllegalArgumentException("Argument cannot be null, nor blank");
        }
        return value;
    }
    
    public static @NotNullable CharSequence argumentIsNotBlank(@Nullable CharSequence value, @NotNullable String argumentName){
        if (value == null || value.isEmpty() || value.toString().isBlank()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor blank");
        }
        return value;
    }
    
    public static  @NotNullable CharSequence argumentIsNotEmpty(@Nullable CharSequence value, @NotNullable String argumentName){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
    
    public static <T, C extends Collection<T>> @NotNullable C argumentIsNotEmpty(@Nullable C value){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument cannot be null, nor empty");
        }
        return value;
    }
    
    public static <T, C extends Collection<T>> @NotNullable C argumentIsNotEmpty(@Nullable C value, @NotNullable String argumentName){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
}
