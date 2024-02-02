package org.midheaven.lang.reflection;

public class ReflectionException extends RuntimeException{
    public ReflectionException(Exception cause) {
        super(cause);
    }

    public ReflectionException(String message) {
        super(message);
    }
}
