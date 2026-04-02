package org.midheaven.io;

import java.io.InputStream;
import java.util.function.Function;

public class ByteContentSourceReader<T extends ByteContent> {
    
    private final ByteContentFormat format;
    private final Function<ByteContent, T> wrapper;
    
    ByteContentSourceReader(ByteContentFormat format, Function<ByteContent, T> wrapper){
        this.format = format;
        this.wrapper = wrapper;
    }
    
    public T empty(){
        return wrapper.apply(new EmptyByteContent(format));
    }
    
    public T from(byte[] bytes){
        if (bytes.length == 0){
            return empty();
        }
        return wrapper.apply(new ArrayByteContent(bytes, format));
    }
    
    public T from(String text){
        if (text.isEmpty()){
            return empty();
        }
        return wrapper.apply(new StringByteContent(text, format));
    }
    
    public T from(InputStream inputStream){
        return wrapper.apply(new InputStreamByteContent(inputStream, format));
    }
    
    public T embeddedResource(String resourcePath){
        var in = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (in == null){
            return empty();
        }
   
        return wrapper.apply(new InputStreamByteContent(in, format));
    }
}
