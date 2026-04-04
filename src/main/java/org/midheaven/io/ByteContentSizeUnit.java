package org.midheaven.io;

import org.midheaven.math.Rational;

/**
 * Enumerates Byte Content Size Unit values.
 */
public enum ByteContentSizeUnit {
    
    BYTE("B", 1),
    KILO_BYTE("KB", 1024),
    MEGA_BYTE("MB", 1024 * 1024),
    GIGA_BYTE("GB", 1024 * 1024 * 1024),
    TERA_BYTE("TB", 1024 * 1024 * 1024 * 1024L),
    ;
    
    private final String symbol;
    private final long factor;
    
    ByteContentSizeUnit(String symbol, long factor){
        this.symbol = symbol;
        this.factor = factor;
    }
    
    /**
     * Performs symbol.
     * @return the result of symbol
     */
    String symbol(){
        return symbol;
    }
    
    /**
     * Returns to Bytes.
     * @param inUnit the inUnit value
     * @return the result of toBytes
     */
    Rational toBytes(Rational inUnit){
        // 2 TB  -> 2 * TB.factor
        return inUnit.times(this.factor);
    }
    
    /**
     * Returns from Bytes.
     * @param inBytes the inBytes value
     * @return the result of fromBytes
     */
    Rational fromBytes(Rational inBytes){
        // 1024 B  ->  1024 / unit.factor
        return inBytes.over(this.factor);
    }
}

