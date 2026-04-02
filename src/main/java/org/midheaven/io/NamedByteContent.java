package org.midheaven.io;

import java.io.InputStream;
import java.io.OutputStream;

public class NamedByteContent extends ByteContent{
    
    static NamedByteContentBuilder named(String name){
        return new NamedByteContentBuilder(name);
    }
    
    private final String name;
    private final ByteContent content;
    
    NamedByteContent(String name, ByteContent content){
        super(content.format());
        this.name = name;
        this.content = content;
    }
    
    public String name(){
        return name;
    }
    
    public static ByteContentBuilder read() {
        return ByteContent.create();
    }
    
    @Override
    public String readAsText() {
        return content.readAsText();
    }
    
    @Override
    public InputStream readAsInputStream() {
        return content.readAsInputStream();
    }
    
    @Override
    public void writeTo(OutputStream out) throws IoException {
        content.writeTo(out);
    }
    
    @Override
    public byte[] readAllBytes() {
        return content.readAllBytes();
    }
    
    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }
    
    @Override
    public ByteContentSize size() {
        return content.size();
    }
    
}
