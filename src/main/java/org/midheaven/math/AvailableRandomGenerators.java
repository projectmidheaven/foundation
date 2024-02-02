package org.midheaven.math;

public interface AvailableRandomGenerators {

    BooleansRandomGenerator booleans();

    BytesRandomGenerator bytes();

    IntegersRandomGenerator integers();

    LongsRandomGenerator longs();

    DoublesRandomGenerator doubles();

    BigIntegersRandomGenerator bigIntegers();

    BigDecimalsRandomGenerator bigDecimals();

    LocalDatesRandomGenerator localDates();

}
