package org.midheaven.io;

import org.midheaven.lang.ValueClass;

import java.io.IOException;
import java.io.InputStream;

@ValueClass
/**
 * Represents Input Stream Byte Content.
 */
final class InputStreamByteContent extends AbstractReadOnceByteContent{
    
    private final InputStream stream;
    
    InputStreamByteContent(InputStream stream, ByteContentFormat format){
        super(format);
        this.stream = stream;
    }
    
    @Override
    protected byte[] loadAllBytes() {
        try (stream){
            return stream.readAllBytes();
        } catch (IOException e) {
            throw IoException.wrap(e);
        }
    }
}
