package org.midheaven.io;

/**
 * Builder for Named Byte Content instances.
 */
public class NamedByteContentBuilder {
    
    private final String name;
    
    NamedByteContentBuilder(String name) {
        this.name = name;
    }
    
    /**
     * Performs withFormat.
     * @param format the format value
     * @return the result of withFormat
     */
    public ByteContentSourceReader<NamedByteContent> withFormat(ByteContentFormat format){
        return new ByteContentSourceReader<>(format, c -> new NamedByteContent(name, c));
    }
}
