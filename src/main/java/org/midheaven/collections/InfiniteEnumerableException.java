package org.midheaven.collections;

public class InfiniteEnumerableException extends RuntimeException {
    
    public InfiniteEnumerableException(){
        super("Enumerable is infinite");
    }
    
    public InfiniteEnumerableException(String message){
        super(message);
    }
}
