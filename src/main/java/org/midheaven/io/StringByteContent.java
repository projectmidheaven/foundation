package org.midheaven.io;

import org.midheaven.lang.NotNullable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

final class StringByteContent extends ByteContent {
    
    private final String text;
    
    public StringByteContent(String text, ByteContentFormat format) {
        super(format);
        this.text = text;
    }
    
    @Override
    public byte[] readAllBytes() {
        return this.text.getBytes(StandardCharsets.UTF_8);
    }
    
    @Override
    public boolean isEmpty() {
        return text.isEmpty();
    }
    
    @Override
    public ByteContentSize size() {
        return ByteContentSize.fromBytes(readAllBytes().length);
    }
    
    @Override
    public void writeTo(OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out)){
            writer.write(text);
            out.flush();
        } catch (IOException e) {
            throw IoException.wrap(e);
        }
    }
    
    @Override
    public String readAsText(){
        return text;
    }
    
    public boolean hasSameContentAs(@NotNullable ByteContent other) {
        if (other instanceof StringByteContent stringByteContent){
            return text.equals(stringByteContent.text);
        }
        return super.hasSameContentAs(other);
    }
}
