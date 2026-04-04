package org.midheaven.io;

/**
 * Exception related to Io.
 */
public class IoException extends RuntimeException{
    
    /**
     * Performs wrap.
     * @param cause the cause value
     * @return the result of wrap
     */
    public static IoException wrap(Exception cause){
        if (cause instanceof IoException original){
            return original;
        }
        return new IoException(cause);
    }
    
    IoException(Exception cause) {
        super(cause);
    }
}
