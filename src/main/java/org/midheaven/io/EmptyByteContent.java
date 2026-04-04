package org.midheaven.io;

import org.midheaven.lang.NotNullable;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents Empty Byte Content.
 */
class EmptyByteContent extends ByteContent {
    
    public EmptyByteContent(ByteContentFormat format) {
        super(format);
    }
    
    public String readAsText(){
        return "";
    }
    
    @Override
    public byte[] readAllBytes() {
        return new byte[0];
    }
    
    @Override
    public InputStream readAsInputStream(){
        return InputStream.nullInputStream();
    }
    
    @Override
    public void writeTo(OutputStream out) throws IoException{
       //no-op
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public ByteContentSize size() {
        return ByteContentSize.ZERO;
    }
    
    public boolean hasSameContentAs(@NotNullable ByteContent other) {
        return other.isEmpty();
    }
}
