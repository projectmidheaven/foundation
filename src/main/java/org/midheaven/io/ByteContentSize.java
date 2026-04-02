package org.midheaven.io;

import org.midheaven.lang.ValueClass;
import org.midheaven.math.Rational;

@ValueClass
public final class ByteContentSize {
    
    public static final ByteContentSize ZERO = fromBytes(0);
    
    public static ByteContentSize fromBytes(long size){
        return of(size, ByteContentSizeUnit.BYTE);
    }
    
    public static ByteContentSize of(Rational size, ByteContentSizeUnit unit){
        return new ByteContentSize(size, unit);
    }
    
    public static ByteContentSize of(long size, ByteContentSizeUnit unit){
        return of(Rational.of(size), unit);
    }
    
    private final Rational sizeInUnit;
    private final ByteContentSizeUnit unit;
    
    private ByteContentSize(Rational sizeInUnit, ByteContentSizeUnit unit) {
        this.sizeInUnit = sizeInUnit;
        this.unit = unit;
    }
    
    public long inBytes(){
        return unit.toBytes(sizeInUnit).toLong();
    }
    
    public Rational in(ByteContentSizeUnit targetUnit){
        if (targetUnit.equals(this.unit)){
            return sizeInUnit;
        }
        return targetUnit.fromBytes(unit.toBytes(sizeInUnit));
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof ByteContentSize that
            && that.sizeInUnit.equals(sizeInUnit)
            && that.unit.equals(unit);
    }
    
    @Override
    public int hashCode() {
        return sizeInUnit.hashCode();
    }
}
