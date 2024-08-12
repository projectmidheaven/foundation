package org.midheaven.math;

public class StringRandomGeneratorBuilder {

    private final RandomGeneratorProvider parent;

    StringRandomGeneratorBuilder(RandomGeneratorProvider parent) {
        this.parent = parent;
    }

    public StringRandomGeneratorTypeBuilder withLengthBetween(int minLength, int maxLength) {
        return new StringRandomGeneratorTypeBuilder(parent, minLength, maxLength);
    }

    public StringRandomGeneratorTypeBuilder withLengthUpTo(int maxLength) {
        return withLengthBetween(0, maxLength);
    }

    public StringRandomGeneratorTypeBuilder withLength(int length) {
        return withLengthBetween(length, length);
    }
}
