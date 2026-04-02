package org.midheaven.io;

import org.midheaven.lang.Check;
import org.midheaven.lang.ValueClass;

@ValueClass
public final class ByteContentFormat {
    
    public static ByteContentFormat of(String extension, String contentType){
        return new ByteContentFormat(
            Check.argumentIsNotNull(extension),
            Check.argumentIsNotNull(contentType)
        );
    }
    
    private final String extension;
    private final String contentType;
    
    private ByteContentFormat(String extension, String contentType){
        this.extension = extension;
        this.contentType = contentType;
    }
    @Override
    public boolean equals(Object other){
        return this == other
                   || (other instanceof ByteContentFormat that && that.extension().equals(this.extension));
    }
    
    public int hashCode(){
        return extension.hashCode();
    }
    
    @Override
    public String toString(){
        return extension;
    }
    
    public String extension() {
        return extension;
    }
    
    public String contentType() {
        return contentType;
    }
    
}