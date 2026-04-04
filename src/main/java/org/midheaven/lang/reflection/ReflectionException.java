package org.midheaven.lang.reflection;

/**
 * Exception related to Reflection.
 */
public class ReflectionException extends RuntimeException{
    /**
     * Creates a new ReflectionException.
     * @param cause the cause value
     */
    public ReflectionException(Exception cause) {
        super(cause);
    }

    /**
     * Creates a new ReflectionException.
     * @param message the message value
     */
    public ReflectionException(String message) {
        super(message);
    }
}
