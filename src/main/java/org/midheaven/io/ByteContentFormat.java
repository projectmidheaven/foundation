package org.midheaven.io;

import org.midheaven.lang.Check;
import org.midheaven.lang.ValueClass;

@ValueClass
/**
 * Represents Byte Content Format.
 */
public final class ByteContentFormat {
    
    /**
     * Creates an instance from the provided value.
     * @param extension the extension value
     * @param contentType the contentType value
     * @return the result of of
     */
    public static ByteContentFormat of(String extension, String contentType){
        return new ByteContentFormat(
            Check.argumentIsNotNull(extension),
            Check.argumentIsNotNull(contentType)
        );
    }
    
    private final String extension;
    private final String contentType;
    
    /**
     * Performs ByteContentFormat.
     * @param extension the extension value
     * @param contentType the contentType value
     * @return the result of ByteContentFormat
     */
    private ByteContentFormat(String extension, String contentType){
        this.extension = extension;
        this.contentType = contentType;
    }
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public boolean equals(Object other){
        return this == other
                   || (other instanceof ByteContentFormat that && that.extension().equals(this.extension));
    }
    
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode(){
        return extension.hashCode();
    }
    
    /**
     * Returns to String.
     * @return the result of toString
     */
    @Override
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString(){
        return extension;
    }
    
    /**
     * Performs extension.
     * @return the result of extension
     */
    public String extension() {
        return extension;
    }
    
    /**
     * Performs contentType.
     * @return the result of contentType
     */
    public String contentType() {
        return contentType;
    }
    
}
