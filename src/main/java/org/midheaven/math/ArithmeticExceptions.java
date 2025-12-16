package org.midheaven.math;

class ArithmeticExceptions {
    
    static ArithmeticException divisionByZero(){
        return new ArithmeticException("Cannot divide be zero");
    }
    
    static ArithmeticException integerInversion(){
        return new ArithmeticException("Cannot invert an Int. Convert to Rational first.");
    }
}
