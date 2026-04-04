package org.midheaven.math;

/**
 * Defines the contract for Available Random Generators.
 */
public interface AvailableRandomGenerators {

    /**
     * Performs booleans.
     * @return the result of booleans
     */
    BooleansRandomGenerator booleans();

    /**
     * Performs bytes.
     * @return the result of bytes
     */
    BytesRandomGenerator bytes();

    /**
     * Performs integers.
     * @return the result of integers
     */
    IntegersRandomGenerator integers();

    /**
     * Performs longs.
     * @return the result of longs
     */
    LongsRandomGenerator longs();

    /**
     * Performs doubles.
     * @return the result of doubles
     */
    DoublesRandomGenerator doubles();

    /**
     * Performs bigIntegers.
     * @return the result of bigIntegers
     */
    BigIntegersRandomGenerator bigIntegers();

    /**
     * Performs bigDecimals.
     * @return the result of bigDecimals
     */
    BigDecimalsRandomGenerator bigDecimals();

    /**
     * Performs localDates.
     * @return the result of localDates
     */
    LocalDatesRandomGenerator localDates();

    /**
     * Performs rationals.
     * @return the result of rationals
     */
    RationalRandomGenerator rationals();

}
