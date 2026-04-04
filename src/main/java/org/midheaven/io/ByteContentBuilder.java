package org.midheaven.io;

import java.util.function.Function;

/**
 * Builder for Byte Content instances.
 */
public class ByteContentBuilder {
    
    /**
     * Performs withFormat.
     * @param format the format value
     * @return the result of withFormat
     */
    public ByteContentSourceReader<ByteContent> withFormat(ByteContentFormat format){
        return new ByteContentSourceReader<>(format, Function.identity());
    }
    
 
}
