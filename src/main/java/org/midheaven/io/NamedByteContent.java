package org.midheaven.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents Named Byte Content.
 */
public class NamedByteContent extends ByteContent{
    
    /**
     * Returns named.
     * @param name the name value
     * @return the result of named
     */
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
    
    /**
     * Performs name.
     * @return the result of name
     */
    public String name(){
        return name;
    }
    
    /**
     * Performs read.
     * @return the result of read
     */
    public static ByteContentBuilder read() {
        return ByteContent.create();
    }
    
    /**
     * Performs readAsText.
     * @return the result of readAsText
     */
    @Override
    /**
     * Performs readAsText.
     * @return the result of readAsText
     */
    public String readAsText() {
        return content.readAsText();
    }
    
    /**
     * Performs readAsInputStream.
     * @return the result of readAsInputStream
     */
    @Override
    /**
     * Performs readAsInputStream.
     * @return the result of readAsInputStream
     */
    public InputStream readAsInputStream() {
        return content.readAsInputStream();
    }
    
    /**
     * Performs writeTo.
     * @param out the out value
     */
    @Override
    /**
     * Performs writeTo.
     * @param out the out value
     */
    public void writeTo(OutputStream out) throws IoException {
        content.writeTo(out);
    }
    
    /**
     * Performs readAllBytes.
     * @return the result of readAllBytes
     */
    @Override
    /**
     * Performs readAllBytes.
     * @return the result of readAllBytes
     */
    public byte[] readAllBytes() {
        return content.readAllBytes();
    }
    
    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    @Override
    /**
     * Checks whether is Empty.
     * @return the result of isEmpty
     */
    public boolean isEmpty() {
        return content.isEmpty();
    }
    
    /**
     * Performs size.
     * @return the result of size
     */
    @Override
    /**
     * Performs size.
     * @return the result of size
     */
    public ByteContentSize size() {
        return content.size();
    }
    
}
