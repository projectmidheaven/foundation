package org.midheaven.math;

import org.midheaven.lang.Maybe;
import org.midheaven.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.function.Function;

public interface Interval<T> {
    
    interface Domain<T, U> {
        
        static <C extends Comparable<C>> Domain<C, C> natural(){
            return new IdentityDomain<>(Comparable::compareTo);
        }
        
        /**
         * Consider intervals between 00h00 of the minimum to the 24h00 of the maximum.
         * 24h00 of the maximum is 00h00 of the next day.
         * @return
         */
        static Domain<LocalDateTime, LocalDate> days(){
            return new Domain<>() {
                @Override
                public int compare(LocalDateTime a, LocalDateTime b) {
                    return a.compareTo(b);
                }
                
                @Nullable
                @Override
                public LocalDateTime applyMinimum(@Nullable LocalDate minimum) {
                    return minimum.atStartOfDay();
                }
                
                @Nullable
                @Override
                public LocalDateTime applyMaximum(@Nullable LocalDate maximum) {
                    return maximum.plusDays(1).atStartOfDay();
                }
            };
        }
        
        /**
         * Consider intervals between the dates, not considering time.
         */
        static Domain<LocalDate, LocalDate> dates(){
            return new IdentityDomain<>(LocalDate::compareTo);
        }
        
        static Domain<ZonedDateTime , ZonedDateTime> time(){
            return new IdentityDomain<>(ZonedDateTime::compareTo);
        }
        
        int compare(T a, T b);
        
        @Nullable
        T applyMinimum(@Nullable U minimum);
        @Nullable
        T applyMaximum(@Nullable U maximum);
    }
    
    interface Boundary<T> {
        boolean isOpen();
        boolean isInfinity();
        Maybe<T> value();
    }
    
    static <C extends Comparable<C>> IntervalBuilder<C, C> ranging(Class<C> type){
        return new IntervalBuilder<C, C>(Domain.natural());
    }
    
    static <C, S> IntervalBuilder<C, S> ranging(Domain<C, S> order){
        return new IntervalBuilder<>(order);
    }
    
    static <C> IntervalBuilder<C, C> ranging(Comparator<C> comparator){
        return new IntervalBuilder<>(new IdentityDomain<C>(comparator::compare));
    }

    /**
     * An empty interval.
     * @return
     * @param <C>
     */
    static <C> Interval<C> empty(){
        return EmptyInterval.INSTANCE;
    }
    
    Boundary<T> minimum();

    Boundary<T> maximum();

    boolean isEmpty();
    boolean contains(T other);

    boolean intersects(Interval<T> other);
    boolean contains(Interval<T> other);
    
    default <R> R apply(Function<Interval<T> , R> calculation){
        return calculation.apply(this);
    }
}

