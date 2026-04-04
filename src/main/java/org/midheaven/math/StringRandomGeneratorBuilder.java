package org.midheaven.math;

/**
 * Builder for String Random Generator instances.
 */
public class StringRandomGeneratorBuilder {

    private final RandomGeneratorProvider parent;

    StringRandomGeneratorBuilder(RandomGeneratorProvider parent) {
        this.parent = parent;
    }

    /**
     * Performs withLengthBetween.
     * @param minLength the minLength value
     * @param maxLength the maxLength value
     * @return the result of withLengthBetween
     */
    public StringRandomGeneratorTypeBuilder withLengthBetween(int minLength, int maxLength) {
        return new StringRandomGeneratorTypeBuilder(parent, minLength, maxLength);
    }

    /**
     * Performs withLengthUpTo.
     * @param maxLength the maxLength value
     * @return the result of withLengthUpTo
     */
    public StringRandomGeneratorTypeBuilder withLengthUpTo(int maxLength) {
        return withLengthBetween(0, maxLength);
    }

    /**
     * Performs withLength.
     * @param length the length value
     * @return the result of withLength
     */
    public StringRandomGeneratorTypeBuilder withLength(int length) {
        return withLengthBetween(length, length);
    }
}
