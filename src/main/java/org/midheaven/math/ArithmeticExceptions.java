package org.midheaven.math;

public class ArithmeticExceptions {
    
    public static ArithmeticException divisionByZero(){
        return new ArithmeticException("Cannot divide be zero");
    }
    
    public static ArithmeticException integerInversion(){
        return new ArithmeticException("Cannot invert an Int. Convert to Rational first.");
    }
}
