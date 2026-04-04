package org.midheaven.math;

/**
 * Immutable record for Divider And Remainder.
 * @param <T> type of the result
 */
public record DividerAndRemainder<T>(T divider, T remainder) {
}
