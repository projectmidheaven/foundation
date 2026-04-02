package org.midheaven.io;

import java.util.function.Function;

public class ByteContentBuilder {
    
    public ByteContentSourceReader<ByteContent> withFormat(ByteContentFormat format){
        return new ByteContentSourceReader<>(format, Function.identity());
    }
    
 
}
