package org.midheaven.io;

import org.midheaven.lang.NotNullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Represents Byte Content.
 */
public abstract class ByteContent {
    
    /**
     * Performs create.
     * @return the result of create
     */
    public static ByteContentBuilder create(){
        return new ByteContentBuilder();
    }
    
    final ByteContentFormat format;
    
    /**
     * Creates a new ByteContent.
     * @param format the format value
     */
    public ByteContent(ByteContentFormat format){
        this.format= format;
    }
    
    /**
     * Performs format.
     * @return the result of format
     */
    public final @NotNullable ByteContentFormat format(){
        return format;
    }
    
    /**
     * Performs readAsText.
     * @return the result of readAsText
     */
    public @NotNullable String readAsText(){
        return new String(readAllBytes(), StandardCharsets.UTF_8);
    }
    
    /**
     * Performs readAsInputStream.
     * @return the result of readAsInputStream
     */
    public @NotNullable InputStream readAsInputStream(){
        return new ByteArrayInputStream(readAllBytes());
    }
    
    /**
     * Writes the content to an OutputStream but does not close it
     * @param out
     * @throws IoException
     */
    public void writeTo(@NotNullable OutputStream out) throws IoException{
        try {
            out.write(readAllBytes());
            out.flush();
        } catch (IOException e) {
            throw IoException.wrap(e);
        }
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
    public final boolean equals(Object other){
        return this == other
            || (other instanceof ByteContent that && that.hasSameContentAs(this));
    }
    
    /**
     * Checks whether has Same Content As.
     * @param other the other value
     * @return the result of hasSameContentAs
     */
    public boolean hasSameContentAs(@NotNullable ByteContent other) {
        if (this == other || this.isEmpty() && other.isEmpty()){
            return true;
        } else if (this.isEmpty() != other.isEmpty()){
            return false;
        } else if (!this.size().equals(other.size())){
            return false;
        }
        
        try (var a = this.readAsInputStream(); var b = other.readAsInputStream()){
            int byteA;
            int byteB;
            
            do {
                // Read a byte from each stream
                byteA = a.read();
                byteB = b.read();
                
                // If a mismatch is found, the streams are not equal
                if (byteA != byteB) {
                    return false;
                }
                
                // Continue looping as long as both streams still have data
            } while (byteA != -1);
            
            // The streams are equal if and only if both reached the end simultaneously
            return true;
        } catch (Exception e){
            throw IoException.wrap(e);
        }
    }
    
    /**
     * Performs readAllBytes.
     * @return the result of readAllBytes
     */
    public abstract byte[] readAllBytes();
    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    public abstract boolean isEmpty();
    /**
     * Performs size.
     * @return the result of size
     */
    public abstract ByteContentSize size();
}
