package org.midheaven.lang;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public abstract class Base32 {
    
    public interface Encoder {
        String encode(byte[] bytes);
        default String encode(String text){
            return encode(text.getBytes(StandardCharsets.UTF_8));
        }
    }
    
    public interface Decoder {
        byte[] decode(String textRepresentation);
        default String decodeToString(String textRepresentation){
            return new String(decode(textRepresentation),StandardCharsets.UTF_8);
        }
    }
    
    public static Base32 crockford() {
        return new CrockfordBase32();
    }
    
    public  abstract  Encoder encoder();
    
    public abstract Decoder decoder();
    
 
}

class CrockfordBase32 extends Base32{
    
    // Crockford's Alphabet: 0-9, A-Z (excluding I, L, O, U)
    static final char[] ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    
    @Override
    public Encoder encoder() {
        return new CrockfordBase32Encoder();
    }
    
    @Override
    public Decoder decoder() {
        return new CrockfordBase32Decoder();
    }
}

class CrockfordBase32Encoder implements Base32.Encoder {
    
 
    @Override
    public String encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        int buffer = 0;
        int bitsLeft = 0;
        
        for (byte b : bytes) {
            // Shift current buffer 8 bits to the left and add the new byte
            buffer = (buffer << 8) | (b & 0xFF);
            bitsLeft += 8;
            
            // While we have at least 5 bits, extract them
            while (bitsLeft >= 5) {
                int index = (buffer >> (bitsLeft - 5)) & 0x1F; // 0x1F = 31 (5 bits)
                result.append(CrockfordBase32.ALPHABET[index]);
                bitsLeft -= 5;
                
                // Clear the bits we just used to prevent integer overflow
                // (though not strictly necessary with 32-bit int and 8-bit increments)
                buffer &= (1 << bitsLeft) - 1;
            }
        }
        
        // Handle remaining bits (less than 5)
        if (bitsLeft > 0) {
            int index = (buffer << (5 - bitsLeft)) & 0x1F;
            result.append(CrockfordBase32.ALPHABET[index]);
        }
        
        return result.toString();
    }
}

class CrockfordBase32Decoder implements Base32.Decoder {
    
    // Lookup table for decoding
    private static final byte[] DECODE_MAP = new byte[128];
    
    static {
        // Initialize map with -1 (invalid)
        for (int i = 0; i < 128; i++) DECODE_MAP[i] = -1;
        
        // Map standard characters
        for (int i = 0; i < CrockfordBase32.ALPHABET.length; i++) {
            DECODE_MAP[CrockfordBase32.ALPHABET[i]] = (byte) i;
            // Also map lowercase
            DECODE_MAP[Character.toLowerCase(CrockfordBase32.ALPHABET[i])] = (byte) i;
        }
        
        // Map Crockford aliases
        DECODE_MAP['O'] = 0;  DECODE_MAP['o'] = 0;
        DECODE_MAP['I'] = 1;  DECODE_MAP['i'] = 1;
        DECODE_MAP['L'] = 1;  DECODE_MAP['l'] = 1;
    }
    
    @Override
    public byte[] decode(String textRepresentation) {
        if (textRepresentation == null || textRepresentation.isEmpty()) {
            return new byte[0];
        }
        
        // Remove hyphens as per Crockford spec
        textRepresentation = textRepresentation.replace("-", "");
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int buffer = 0;
        int bitsLeft = 0;
        
        for (int i = 0; i < textRepresentation.length(); i++) {
            char c = textRepresentation.charAt(i);
            if (c >= 128 || DECODE_MAP[c] == -1) {
                throw new IllegalArgumentException("Invalid character in Base32 string: " + c);
            }
            
            int value = DECODE_MAP[c];
            
            // Push 5 bits into the buffer
            buffer = (buffer << 5) | value;
            bitsLeft += 5;
            
            // If we have enough bits for a full byte (8 bits), extract it
            if (bitsLeft >= 8) {
                int b = (buffer >> (bitsLeft - 8)) & 0xFF;
                out.write(b);
                bitsLeft -= 8;
                
                // Keep the buffer clean to prevent overflow
                buffer &= (1 << bitsLeft) - 1;
            }
        }
        
        // Note: Any leftover bits (bitsLeft < 8) are ignored as they
        // were padding added during the encoding phase.
        return out.toByteArray();
    }
}