package org.midheaven.math;

import org.midheaven.lang.Check;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Ordered;

import java.math.BigDecimal;

/**
 * Represents Angle.
 */
public class Angle implements AdditionGroup<Angle>, Ordered<Angle> {
    
    /**
     * Returns of Radians.
     * @param radians the radians value
     * @return the result of ofRadians
     */
    public static @NotNullable Angle ofRadians(@NotNullable Rational radians){
        Check.argumentIsNotNull(radians);
        return new Angle(radians.times(180).over(Rational.PI));
    }
    
    /**
     * Returns of Degrees.
     * @param degrees the degrees value
     * @return the result of ofDegrees
     */
    public static @NotNullable Angle ofDegrees(@NotNullable Rational degrees){
        Check.argumentIsNotNull(degrees);
        return new Angle(reduceDegrees(degrees));
    }
    
    /**
     * Returns of Degrees.
     * @param degrees the degrees value
     * @return the result of ofDegrees
     */
    public static @NotNullable Angle ofDegrees(long degrees){
        return ofDegrees(Rational.of(degrees));
    }
  
    /**
     * Performs reduceDegrees.
     * @param degrees the degrees value
     * @return the result of reduceDegrees
     */
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
    
    /**
     * Performs Angle.
     * @param angleInDegrees the angleInDegrees value
     * @return the result of Angle
     */
    private Angle(Rational angleInDegrees){
        this.angleInDegrees = angleInDegrees;
    }
    
    
    /**
     * Performs negate.
     * @return the result of negate
     */
    @Override
    /**
     * Performs negate.
     * @return the result of negate
     */
    public @NotNullable Angle negate() {
        return new Angle(reduceDegrees(this.angleInDegrees.negate()));
    }
    
    /**
     * Performs minus.
     * @param other the other value
     * @return the result of minus
     */
    @Override
    /**
     * Performs minus.
     * @param other the other value
     * @return the result of minus
     */
    public @NotNullable Angle minus(@NotNullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.minus(other.angleInDegrees)));
    }
    
    /**
     * Performs plus.
     * @param other the other value
     * @return the result of plus
     */
    @Override
    /**
     * Performs plus.
     * @param other the other value
     * @return the result of plus
     */
    public @NotNullable Angle plus(@NotNullable Angle other) {
        return new Angle(reduceDegrees(this.angleInDegrees.plus(other.angleInDegrees)));
    }
    
    /**
     * Performs abs.
     * @return the result of abs
     */
    @Override
    /**
     * Performs abs.
     * @return the result of abs
     */
    public @NotNullable Angle abs() {
        return new Angle(this.angleInDegrees.abs());
    }
    
    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    @Override
    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    public boolean isZero() {
        return angleInDegrees.isZero();
    }
    
    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    @Override
    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    public int compareTo(Angle other) {
        return angleInDegrees.compareTo(other.angleInDegrees);
    }
    
    
    /**
     * Performs times.
     * @param factor the factor value
     * @return the result of times
     */
    public @NotNullable Angle times(@NotNullable Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.times(factor)));
    }
    
    /**
     * Performs over.
     * @param factor the factor value
     * @return the result of over
     */
    public @NotNullable Angle over(@NotNullable Rational factor){
        if (factor.isOne()){
            return this;
        }
        return new Angle(reduceDegrees(this.angleInDegrees.over(factor)));
    }
    
    /**
     * Performs times.
     * @param factor the factor value
     * @return the result of times
     */
    public @NotNullable Angle times(long factor){
        if (factor == 1){
            return this;
        }
        return times(Rational.of(factor));
    }
    
    /**
     * Performs over.
     * @param factor the factor value
     * @return the result of over
     */
    public @NotNullable Angle over(long factor){
        if (factor == 1){
            return this;
        }
        return over(Rational.of(factor));
    }
    
    /**
     * Performs ratio.
     * @param other the other value
     * @return the result of ratio
     */
    public @NotNullable Rational ratio(Angle other){
        return this.angleInDegrees.over(other.angleInDegrees);
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
    public boolean equals(Object other){
        return other instanceof Angle that
            && this.angleInDegrees.equals(that.angleInDegrees);
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
    public int hashCode(){
        return this.angleInDegrees.hashCode();
    }
    
    /**
     * Returns to String.
     * @return the result of toString
     */
    @Override
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString(){
        return angleInDegrees.toString();
    }
}
