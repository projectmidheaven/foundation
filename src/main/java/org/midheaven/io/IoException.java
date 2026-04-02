package org.midheaven.io;

public class IoException extends RuntimeException{
    
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
