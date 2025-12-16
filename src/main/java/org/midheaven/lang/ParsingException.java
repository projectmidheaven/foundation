package org.midheaven.lang;

/**
 * Indicates a parsing operation was not successful.
 */
public class ParsingException extends RuntimeException {

    public ParsingException(){}
    public ParsingException(String message){
        super(message);
    }
}
