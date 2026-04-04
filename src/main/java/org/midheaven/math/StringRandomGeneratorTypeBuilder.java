package org.midheaven.math;

/**
 * Builder for String Random Generator Type instances.
 */
public class StringRandomGeneratorTypeBuilder {

    private final RandomGeneratorProvider parent;
    private final int maxLength;
    private final int minLength;

    /**
     * Creates a new StringRandomGeneratorTypeBuilder.
     * @param parent the parent value
     * @param minLength the minLength value
     * @param maxLength the maxLength value
     */
    public StringRandomGeneratorTypeBuilder(RandomGeneratorProvider parent, int minLength, int maxLength) {
        this.parent = parent;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * Performs alphanumeric.
     * @return the result of alphanumeric
     */
    public RandomGenerator<String> alphanumeric() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ALPHANUMERIC);
    }

    /**
     * Performs numeric.
     * @return the result of numeric
     */
    public RandomGenerator<String> numeric() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.NUMERIC);
    }

    /**
     * Performs alphabetic.
     * @return the result of alphabetic
     */
    public RandomGenerator<String> alphabetic() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ALPHABETIC);
    }

    /**
     * Performs symbolic.
     * @return the result of symbolic
     */
    public RandomGenerator<String> symbolic() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.SYMBOLIC);
    }

    /**
     * Performs any.
     * @return the result of any
     */
    public RandomGenerator<String> any() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ANY);
    }
}
