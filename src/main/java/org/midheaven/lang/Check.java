package org.midheaven.lang;

import java.util.Collection;

public class Check {
    
    public static <T> T argumentIsNotNull(String argumentName, T value){
        if (value == null){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null");
        }
        return value;
    }
    
    public static CharSequence argumentIsNotBlank(String argumentName, CharSequence value){
        if (value == null || value.isEmpty() || value.toString().isBlank()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor blank");
        }
        return value;
    }
    
    public static CharSequence argumentIsNotEmpty(String argumentName, CharSequence value){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
    
    public static <T, C extends Collection<T>> C argumentIsNotEmpty(String argumentName, C value){
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Argument " + argumentName + " cannot be null, nor empty");
        }
        return value;
    }
}
