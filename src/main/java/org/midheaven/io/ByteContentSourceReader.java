package org.midheaven.io;

import java.io.InputStream;
import java.util.function.Function;

/**
 * Represents Byte Content Source Reader.
 * @param <T> type of {@link ByteContent} this reader will read
 */
public class ByteContentSourceReader<T extends ByteContent> {
    
    private final ByteContentFormat format;
    private final Function<ByteContent, T> wrapper;
    
    ByteContentSourceReader(ByteContentFormat format, Function<ByteContent, T> wrapper){
        this.format = format;
        this.wrapper = wrapper;
    }
    
    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public T empty(){
        return wrapper.apply(new EmptyByteContent(format));
    }
    
    /**
     * Creates an instance from the provided source.
     * @param bytes the bytes value
     * @return the result of from
     */
    public T from(byte[] bytes){
        if (bytes.length == 0){
            return empty();
        }
        return wrapper.apply(new ArrayByteContent(bytes, format));
    }
    
    /**
     * Creates an instance from the provided source.
     * @param text the text value
     * @return the result of from
     */
    public T from(String text){
        if (text.isEmpty()){
            return empty();
        }
        return wrapper.apply(new StringByteContent(text, format));
    }
    
    /**
     * Creates an instance from the provided source.
     * @param inputStream the inputStream value
     * @return the result of from
     */
    public T from(InputStream inputStream){
        return wrapper.apply(new InputStreamByteContent(inputStream, format));
    }
    
    /**
     * Performs embeddedResource.
     * @param resourcePath the resourcePath value
     * @return the result of embeddedResource
     */
    public T embeddedResource(String resourcePath){
        var in = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (in == null){
            return empty();
        }
   
        return wrapper.apply(new InputStreamByteContent(in, format));
    }
}
