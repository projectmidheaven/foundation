package org.midheaven.io;

public class NamedByteContentBuilder {
    
    private final String name;
    
    NamedByteContentBuilder(String name) {
        this.name = name;
    }
    
    public ByteContentSourceReader<NamedByteContent> withFormat(ByteContentFormat format){
        return new ByteContentSourceReader<>(format, c -> new NamedByteContent(name, c));
    }
}
