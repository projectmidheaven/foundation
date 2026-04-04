package org.midheaven.lang;

/**
 * Indicates a parsing operation was not successful.
 */
public class ParsingException extends RuntimeException {

    /**
     * Creates a new ParsingException.
     */
    public ParsingException(){}
    /**
     * Creates a new ParsingException.
     * @param message the message value
     */
    public ParsingException(String message){
        super(message);
    }
}
