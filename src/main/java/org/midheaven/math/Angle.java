package org.midheaven.math;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Ordered;

import java.math.BigDecimal;

public class Angle implements AdditionGroup<Angle>, Ordered<Angle> {
    
    public static @NotNullable Angle ofRadians(@NotNullable Rational radians){
        Check.argumentIsNotNull(radians);
        return new Angle(radians.times(180).over(Rational.PI));
    }
    
    public static @NotNullable Angle ofDegrees(@NotNullable Rational degrees){
        Check.argumentIsNotNull(degrees);
        return new Angle(reduceDegrees(degrees));
    }
    
    public static @NotNullable Angle ofDegrees(long degrees){
        return ofDegrees(Rational.of(degrees));
    }
  
    private static @NotNullable Rational reduceDegrees(@NotNullable Rational degrees) {
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
    
    private final Rational angleInDegrees;
    
    private Angle(Rational angleInDegrees){
        this.angleInDegrees = angleInDegrees;
    }
    
    
    @Override
    public @NotNullable Angle negate() {
        return new Angle(reduceDegrees(this.angleInDegrees.negate()));
    }
    
    @Override
    public @NotNullable Angle minus(@NotNullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.minus(other.angleInDegrees)));
    }
    
    @Override
    public @NotNullable Angle plus(@NotNullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.plus(other.angleInDegrees)));
    }
    
    @Override
    public @NotNullable Angle abs() {
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
    
    
    public @NotNullable Angle times(@NotNullable Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.times(factor)));
    }
    
    public @NotNullable Angle over(@NotNullable Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.over(factor)));
    }
    
    public @NotNullable Angle times(long factor){
        if (factor == 1){
            return this;
        }
        return times(Rational.of(factor));
    }
    
    public @NotNullable Angle over(long factor){
        if (factor == 1){
            return this;
        }
        return over(Rational.of(factor));
    }
    
    public @NotNullable Rational ratio(Angle other){
        return this.angleInDegrees.over(other.angleInDegrees);
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
