package org.midheaven.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByteContentTestCases {
    
    @Test
    public void fromByte(){
        byte[] bytes = {(byte)1, (byte)2, (byte)3, (byte)4, (byte)5};
        var content = ByteContent.create().withFormat(ByteContentFormats.TXT).from(bytes);
        
        assertFalse(content.isEmpty());
        assertEquals(bytes.length, content.size().inBytes());
        
        var copy = ByteContent.create().withFormat(ByteContentFormats.TXT).from(bytes);
        
        assertEquals(content, copy);
        
        byte[] otherBytes = {(byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6};
        var other = ByteContent.create().withFormat(ByteContentFormats.TXT).from(otherBytes);
        
        assertNotEquals(content, other);
        assertNotEquals(other, content);
    }
    
    @Test
    public void fromInputStream(){
        var text = "Hello";
        var content = ByteContent.create().withFormat(ByteContentFormats.TXT).from(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        
        assertFalse(content.isEmpty());
        assertEquals(text.length(), content.size().inBytes());
        assertEquals(text, content.readAsText());
        
    }
    
    @Test
    public void fromResource(){
        var path = "test.txt";
        var content = ByteContent.create().withFormat(ByteContentFormats.TXT).embeddedResource(path);
        
        assertFalse(content.isEmpty());
        assertEquals("test", content.readAsText());
        
    }
    
    @Test
    public void fromText(){
        var text = "Hello";
        var content = ByteContent.create().withFormat(ByteContentFormats.TXT).from(text);
        
        assertFalse(content.isEmpty());
        assertEquals(text.length(), content.size().inBytes());
        assertEquals(text, content.readAsText());
        
    }
    
    @Test
    public void fromEmpty(){
        var text = "";
        var content = ByteContent.create().withFormat(ByteContentFormats.TXT).from(text);
        var empty = ByteContent.create().withFormat(ByteContentFormats.TXT).empty();
        
        assertTrue(content.isEmpty());
        assertEquals(text.length(), content.size().inBytes());
        assertEquals(text, content.readAsText());
        
        assertTrue(empty.isEmpty());
        assertEquals(text.length(), empty.size().inBytes());
        assertEquals(text, empty.readAsText());
        
        assertEquals(content, empty);
        assertEquals(empty, content);
    }
}
