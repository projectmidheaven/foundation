package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Ordered;

/**
 * Represents Boundary Interval.
 */
class BoundaryInterval<T> implements Interval<T> {

    private final Domain<T, ?> order;
    private final ActualBoundary<T> min;
    private final ActualBoundary<T> max;

    BoundaryInterval(Domain<T, ?> order, ActualBoundary<T> min, ActualBoundary<T> max) {
        this.order = order;
        this.min = min;
        this.max = max;
    }
    
    @Override
    public Boundary<T> minimum() {
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
            return compare(order, max.value().orElseThrow(), other) > 0;
        } else {
            return compare(order, max.value().orElseThrow(), other) >= 0;
        }
    }
    
    static <T> int compare(Domain<T, ?> order, T a , T b){
        if (a == b){
            return 0;
        } else if (a == null){
            return -1;
        } else if (b == null){
            return 1;
        }
        return order.compare(a,b);
    }

    private boolean valueInMinimumBoundary(T other) {
        if (min.isInfinity()) return true;

        if (min.isOpen()) {
            return compare(order, min.value().orElseThrow(), other) < 0;
        } else {
            return compare(order, min.value().orElseThrow(), other) <= 0;
        }
    }

    public boolean intersects(Interval<T> other) {
        if (other.isEmpty()) {
            return false; // empty interval does not intersect others
        } else if (min.isInfinity() && max.isInfinity()) {
            return true; // unbound interval intersects all
        } else if (other.minimum().isInfinity() && other.maximum().isInfinity()) {
            return true; // unbound interval intersects all
        }
        return compareBoundaries(this.min, other.maximum()) <= 0
                && compareBoundaries(this.max, other.minimum()) >= 0;
    }

    @Override
    public boolean contains(Interval<T> other) {
        if (other.isEmpty()) {
            return true; // empty interval is contained in all others
        } else if (min.isInfinity() && max.isInfinity()) {
            return true; // unbound interval contains all
        } else if (other.minimum().isInfinity() && other.maximum().isInfinity()) {
            return false; // bound interval cannot contain unbounded interval
        }
        return compareBoundaries(this.min, other.minimum()) <= 0
                && compareBoundaries(this.max, other.maximum()) >= 0;
    }
    
    private int compareBoundaries(ActualBoundary<T> a, Boundary<T> b) {
       if (b instanceof ActualBoundary<T> actualBoundary){
           return a.compareTo(actualBoundary);
       }
        return compare(order, a.value().orElse(null), b.value().orElse(null));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Interval<?> interval
                && !interval.isEmpty()
                && this.min.equals(interval.minimum())
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
        //  reduce interval (x) to empty
        if (min.isOpen() && !min.isInfinity() && max.isOpen() && !max.isInfinity() && min.isEqualTo(max)){
            return Interval.empty();
        }
        return this;
    }
}

final class ActualBoundary<T> implements Interval.Boundary<T>, Ordered<ActualBoundary<T>> {

    private final boolean isOpen;
    private final int order;
    private final T value;
    private final Interval.Domain<T, ?> domain;

    <U> ActualBoundary (Interval.Domain<T, U> domain, boolean isMinimum, boolean isOpen, U value){
        this.domain = domain;
        this.order = isMinimum ? -1 : 1;
        this.isOpen = isOpen;
        this.value = value == null
                         ? null
                         : isMinimum
                               ? domain.applyMinimum(value)
                               : domain.applyMaximum(value)
        ;
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
        } else if (this.isInfinity()){
            return this.order == -1 ? -1 : 1;
        } else if (other.isInfinity()){
            return other.order == -1 ? 1 : -1;
        }

        return domain.compare(this.value, other.value);

    }
}
