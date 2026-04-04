package org.midheaven.math;

/**
 * Represents Arithmetic Exceptions.
 */
public class ArithmeticExceptions {
    
    /**
     * Performs divisionByZero.
     * @return the result of divisionByZero
     */
    public static ArithmeticException divisionByZero(){
        return new ArithmeticException("Cannot divide be zero");
    }
    
    /**
     * Performs integerInversion.
     * @return the result of integerInversion
     */
    public static ArithmeticException integerInversion(){
        return new ArithmeticException("Cannot invert an Int. Convert to Rational first.");
    }
}
