package org.midheaven.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

class RationalDivisionSpecification {
    
    static BigDecimal reduceToBigDecimal(Rational value){
        return new BigDecimal(value.numerator().toBigInteger()).divide(new BigDecimal(value.denominator().toBigInteger()), 20, RoundingMode.HALF_DOWN);
    }
}
