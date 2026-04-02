package org.midheaven.io;

import org.midheaven.lang.NotNullable;
import org.midheaven.lang.ValueClass;

import java.util.Arrays;

@ValueClass
final class ArrayByteContent extends ByteContent {
    
    private final byte[] content;
    
    ArrayByteContent(byte[] content, ByteContentFormat format){
        super(format);
        this.content = content;
    }
    
    @Override
    public byte[] readAllBytes() {
        return content;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public ByteContentSize size() {
        return ByteContentSize.fromBytes(content.length);
    }
    
    public boolean hasSameContentAs(@NotNullable ByteContent other) {
        if (other instanceof ArrayByteContent arrayByteContent){
            return Arrays.equals(arrayByteContent.content, content);
        }
        return super.hasSameContentAs(other);
    }
}
