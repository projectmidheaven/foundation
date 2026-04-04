package org.midheaven.io;

import org.midheaven.lang.ValueClass;
import org.midheaven.math.Rational;

@ValueClass
/**
 * Represents Byte Content Size.
 */
public final class ByteContentSize {
    
    /**
     * Constant for ZERO.
     */
    public static final ByteContentSize ZERO = fromBytes(0);
    
    /**
     * Returns from Bytes.
     * @param size the size value
     * @return the result of fromBytes
     */
    public static ByteContentSize fromBytes(long size){
        return of(size, ByteContentSizeUnit.BYTE);
    }
    
    /**
     * Creates an instance from the provided value.
     * @param size the size value
     * @param unit the unit value
     * @return the result of of
     */
    public static ByteContentSize of(Rational size, ByteContentSizeUnit unit){
        return new ByteContentSize(size, unit);
    }
    
    /**
     * Creates an instance from the provided value.
     * @param size the size value
     * @param unit the unit value
     * @return the result of of
     */
    public static ByteContentSize of(long size, ByteContentSizeUnit unit){
        return of(Rational.of(size), unit);
    }
    
    private final Rational sizeInUnit;
    private final ByteContentSizeUnit unit;
    
    /**
     * Performs ByteContentSize.
     * @param sizeInUnit the sizeInUnit value
     * @param unit the unit value
     * @return the result of ByteContentSize
     */
    private ByteContentSize(Rational sizeInUnit, ByteContentSizeUnit unit) {
        this.sizeInUnit = sizeInUnit;
        this.unit = unit;
    }
    
    /**
     * Performs inBytes.
     * @return the result of inBytes
     */
    public long inBytes(){
        return unit.toBytes(sizeInUnit).toLong();
    }
    
    /**
     * Performs in.
     * @param targetUnit the targetUnit value
     * @return the result of in
     */
    public Rational in(ByteContentSizeUnit targetUnit){
        if (targetUnit.equals(this.unit)){
            return sizeInUnit;
        }
        return targetUnit.fromBytes(unit.toBytes(sizeInUnit));
    }
    
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public boolean equals(Object other) {
        return other instanceof ByteContentSize that
            && that.sizeInUnit.equals(sizeInUnit)
            && that.unit.equals(unit);
    }
    
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    @Override
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode() {
        return sizeInUnit.hashCode();
    }
}
