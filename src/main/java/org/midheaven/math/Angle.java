package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.Ordered;

import java.math.BigDecimal;

public class Angle implements AdditionGroup<Angle>, Ordered<Angle> {
    
    public static Angle ofRadians(Rational radians){
        return new Angle(radians.times(180).over(Rational.PI));
    }
    
    public static Angle ofDegrees(Rational degrees){
        return new Angle(reduceDegrees(degrees));
    }
    
    public static Angle ofDegrees(long degrees){
        return ofDegrees(Rational.of(degrees));
    }
    
    private final Rational angleInDegrees;
    
    private Angle(Rational angleInDegrees){
        this.angleInDegrees = angleInDegrees;
    }
    
    @Nullable
    @Override
    public Angle negate() {
        return new Angle(reduceDegrees(this.angleInDegrees.negate()));
    }
    
    @Nullable
    @Override
    public Angle minus(@Nullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.minus(other.angleInDegrees)));
    }
    
    @Nullable
    @Override
    public Angle plus(@Nullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.plus(other.angleInDegrees)));
    }
    
    @Nullable
    @Override
    public Angle abs() {
        return new Angle(this.angleInDegrees.abs());
    }
    
    @Override
    public boolean isZero() {
        return angleInDegrees.isZero();
    }
    
    @Override
    public int compareTo(Angle other) {
        return angleInDegrees.compareTo(other.angleInDegrees);
    }
    
    private static Rational reduceDegrees(Rational degrees) {
        if (degrees.isZero()){
            return Rational.ZERO;
        }
        var fullCircle = BigDecimal.valueOf(360);
        var modulo = degrees.toBigDecimal().divideAndRemainder(fullCircle)[1];
        
        if (modulo.signum() < 0){
            modulo = modulo.add(fullCircle);
        }
        return Rational.of(modulo);
    }
    
    public Angle times(Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.times(factor)));
    }
    
    public Angle over(Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.over(factor)));
    }
    
    public Angle times(long factor){
        if (factor == 1){
            return this;
        }
        return times(Rational.of(factor));
    }
    
    public Angle over(long factor){
        if (factor == 1){
            return this;
        }
        return over(Rational.of(factor));
    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof Angle that
            && this.angleInDegrees.equals(that.angleInDegrees);
    }
    
    @Override
    public int hashCode(){
        return this.angleInDegrees.hashCode();
    }
    
    @Override
    public String toString(){
        return angleInDegrees.toString();
    }
}
