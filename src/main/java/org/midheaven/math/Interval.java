package org.midheaven.math;

import org.midheaven.lang.Maybe;

import java.util.Comparator;

public abstract class Interval<T> {

    public interface Boundary<T> {
        boolean isOpen();
        boolean isInfinity();
        Maybe<T> value();
    }

    /**
     * An empty interval.
     * @return
     * @param <C>
     */
    static <C extends Comparable<C>> IntervalBuilder<C> ranging(Class<C> type){
        return new IntervalBuilder<C>(Comparator.naturalOrder());
    }

    /**
     * An empty interval.
     * @return
     * @param <C>
     */
    static <C> IntervalBuilder<C> ranging(Comparator<C> comparator){
        return new IntervalBuilder<>(comparator);
    }

    /**
     * An empty interval.
     * @return
     * @param <C>
     */
    static <C> Interval<C> empty(){
        return new EmptyInterval<>();
    }

    Interval(){}

    public abstract Boundary<T> mininum();

    public abstract Boundary<T> maximum();

    public abstract boolean isEmpty();
    public abstract boolean contains(T other);

    public abstract boolean intersects(Interval<T> other);
    public abstract boolean contains(Interval<T> other);
}

