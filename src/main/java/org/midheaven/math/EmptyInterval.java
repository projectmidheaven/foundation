package org.midheaven.math;

import java.util.Optional;

final class EmptyInterval<T> extends Interval<T> {

    private final static Boundary EMPTY_BOUNDARY = new Boundary() {

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public boolean isInfinity() {
            return false;
        }

        @Override
        public Optional value() {
            return Optional.empty();
        }
    };

    @Override
    public Boundary<T> mininum() {
        return EMPTY_BOUNDARY;
    }

    @Override
    public Boundary<T> maximum() {
        return EMPTY_BOUNDARY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(T other) {
        return false;
    }

    @Override
    public boolean intersects(Interval<T> other) {
        return false;
    }

    @Override
    public boolean contains(Interval<T> other) {
        return other.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Interval<?> interval && interval.isEmpty());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "\u2205";
    }
}
