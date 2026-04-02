package org.midheaven.io;

import org.midheaven.lang.NotNullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class ByteContent {
    
    public static ByteContentBuilder create(){
        return new ByteContentBuilder();
    }
    
    final ByteContentFormat format;
    
    public ByteContent(ByteContentFormat format){
        this.format= format;
    }
    
    public final @NotNullable ByteContentFormat format(){
        return format;
    }
    
    public @NotNullable String readAsText(){
        return new String(readAllBytes(), StandardCharsets.UTF_8);
    }
    
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
    
    @Override
    public final boolean equals(Object other){
        return this == other
            || (other instanceof ByteContent that && that.hasSameContentAs(this));
    }
    
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
    
    public abstract byte[] readAllBytes();
    public abstract boolean isEmpty();
    public abstract ByteContentSize size();
}
