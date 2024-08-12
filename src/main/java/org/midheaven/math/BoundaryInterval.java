package org.midheaven.math;

import org.midheaven.lang.Comparables;
import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Ordered;

import java.util.Comparator;

final class BoundaryInterval<T> extends Interval<T> {

    private final Comparator<T> comparator;
    private final ActualBoundary<T> min;
    private final ActualBoundary<T> max;

    BoundaryInterval(Comparator<T> comparator, ActualBoundary<T> min, ActualBoundary<T> max) {
        this.comparator = comparator;
        this.min = min;
        this.max = max;
    }

    public Boundary<T> mininum() {
        return min;
    }

    public Boundary<T> maximum() {
        return max;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(T other) {
        return valueInMinimumBoundary(other)
                && valueInMaximumBoundary(other);
    }

    private boolean valueInMaximumBoundary(T other) {
        if (max.isInfinity()) return true;

        if (max.isOpen()) {
            return Comparables.compare(comparator, max.value().orElseThrow(), other) > 0;
        } else {
            return Comparables.compare(comparator, max.value().orElseThrow(), other) >= 0;
        }
    }

    private boolean valueInMinimumBoundary(T other) {
        if (min.isInfinity()) return true;

        if (min.isOpen()) {
            return Comparables.compare(comparator, min.value().orElseThrow(), other) < 0;
        } else {
            return Comparables.compare(comparator, min.value().orElseThrow(), other) <= 0;
        }
    }

    public boolean intersects(Interval<T> other) {
        if (other.isEmpty()) {
            return false; // empty interval does not intersect others
        } else if (min.isInfinity() && max.isInfinity()) {
            return true; // unbound interval intersects all
        } else if (other.mininum().isInfinity() && other.maximum().isInfinity()) {
            return true; // unbound interval intersects all
        }
        return compareBoundaries(this.min, other.maximum()) <= 0
                && compareBoundaries(this.max, other.mininum()) >= 0;
    }

    @Override
    public boolean contains(Interval<T> other) {
        if (other.isEmpty()) {
            return true; // empty interval is contained in all others
        } else if (min.isInfinity() && max.isInfinity()) {
            return true; // unbound interval contains all
        } else if (other.mininum().isInfinity() && other.maximum().isInfinity()) {
            return false; // bound interval cannot contain unbounded interval
        }
        return compareBoundaries(this.min, other.mininum()) <= 0
                && compareBoundaries(this.max, other.maximum()) >= 0;
    }

    private int compareBoundaries(ActualBoundary<T> a, Boundary<T> b) {
       if (b instanceof ActualBoundary<T> actualBoundary){
           return a.compareTo(actualBoundary);
       }
        return Comparables.compare(comparator, a.value().orElse(null), b.value().orElse(null));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Interval<?> interval
                && !interval.isEmpty()
                && this.min.equals(interval.mininum())
                && this.max.equals(interval.maximum());
    }

    @Override
    public int hashCode() {
        return HashCode.asymmetric().add(this.min).add(this.max).hashCode();
    }

    @Override
    public String toString() {
        return min + "," + max;
    }

    Interval<T> reduce() {
        if (min.isEqualTo(max)){
            return empty();
        }
        return this;
    }
}

final class ActualBoundary<T> implements Interval.Boundary<T>, Ordered<ActualBoundary<T>> {

    private final boolean isOpen;
    private final int order;
    private final T value;
    private final Comparator<T> comparator;

    ActualBoundary (Comparator<T> comparator,boolean isMinimum, boolean isOpen, T value){
        this.comparator = comparator;
        this.order = isMinimum ? -1 : 1;
        this.isOpen = isOpen;
        this.value = value;
    }

    public String toString(){
        if (isInfinity()){
            if (order == -1){
                return isOpen ? "(" : "[";
            } else {
                return isOpen ? ")" : "]";
            }
        } else {
            if (order == -1){
                return (isOpen ? "( " : "[ ") + value;
            } else {
                return value + (isOpen ? "  )" : " ]");
            }
        }

    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean isInfinity() {
        return value == null;
    }

    @Override
    public Maybe<T> value() {
        return Maybe.of(value);
    }

    @Override
    public int compareTo(ActualBoundary<T> other) {
        if (this.isInfinity() && other.isInfinity()){
            return this.order - other.order;
        } else if (this.isInfinity() && !other.isInfinity()){
            return this.order == -1 ? -1 : 1;
        } else if (!this.isInfinity() && other.isInfinity()){
            return other.order == -1 ? 1 : -1;
        }

        return comparator.compare(this.value, other.value);

    }
}