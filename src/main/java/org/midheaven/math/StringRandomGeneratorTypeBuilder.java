package org.midheaven.math;

public class StringRandomGeneratorTypeBuilder {

    private final RandomGeneratorBuilder parent;
    private final int maxLength;
    private final int minLength;

    public StringRandomGeneratorTypeBuilder(RandomGeneratorBuilder parent, int minLength, int maxLength) {
        this.parent = parent;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public RandomGenerator<String> alphanumeric() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ALPHANUMERIC);
    }

    public RandomGenerator<String> numeric() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.NUMERIC);
    }

    public RandomGenerator<String> alphabetic() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ALPHABETIC);
    }

    public RandomGenerator<String> symbolic() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.SYMBOLIC);
    }

    public RandomGenerator<String> any() {
        return new StringRandomGenerator(parent.source, minLength, maxLength, StringType.ANY);
    }
}
