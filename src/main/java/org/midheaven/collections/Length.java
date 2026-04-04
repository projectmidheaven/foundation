package org.midheaven.collections;

import org.midheaven.lang.Ordered;
import org.midheaven.math.Int;

/**
 * Represents the Length of an {@link Enumerator}. It can be infinite, finite or unknown
 */
public abstract sealed class Length
        permits Length.Finite , Length.Infinite , Length.Unknown
{
    
    /**
     * Creates a finite {@code Length} with the given length
     * @param length the length value
     * @return
     */
    public static Length finite(Int length) {
        return new Finite(length);
    }
  
    abstract Length filter();
    
    /**
     * Returns a new Length that is the minimum between {@code this} and the given Length
     * {@code
     * min(finite(x), finite(y)) ->  finite(x <= y ? x : y)
     * min(finite(x), infinite) ->  finite(x)
     * min(finite(x), unknown) ->  unknown
     * min(infinite, unknown) ->  unknown
     * }
     *
     * @param other the other value
     * @return the minimum Length
     */
    public abstract Length min(Length other);
    
    /**
     * Subtracts the given value from this value.
     * {@code
     * finite(x) - finite(y) ->  x - y
     * finite(x) - infinite ->  infinite
     * infinite - finite(x) ->  infinite
     * finite(x) - unknown ->  unknown
     * unknown - finite(x) ->  unknown
     * infinite - infinite -> infinite
     * infinite - unknown ->  infinite
     * unknown - infinite ->  unknown
     * unknown - unknown -> unknown
     * }
     *
     * @param other the value to subtract
     * @return {@code this} - {@code other}
     */
    public abstract Length minus(Length other);
    
    public static final class Finite extends Length {

        private final Int count;

        Finite(Int count){
            this.count = count;
        }

        public Int count(){
            return count;
        }

        @Override
        public Length filter() {
            return Unknown.INSTANCE;
        }

        @Override
        public Length min(Length other) {
            if (other instanceof Finite finite){
                return new Finite(Ordered.min(this.count , finite.count));
            } else if (other instanceof Infinite){
                return this;
            }
            return other;
        }
        
        @Override
        public Length minus(Length other) {
            if (other instanceof Finite finite){
                return new Finite(finite.count.minus(this.count));
            }
            return other;
        }

    }

    public static final class Infinite extends Length {
        static final Infinite INSTANCE = new Infinite();
        
        @Override
        Length filter() {
            return this;
        }
        
        @Override
        public Length min(Length other) {
            return other;
        }
        
        @Override
        public Length minus(Length other) {
            return this;
        }
        
    }

    public static final class Unknown extends Length {
        static final Unknown INSTANCE = new Unknown();
        
        @Override
        Length filter() {
            return this;
        }
        
        @Override
        public Length min(Length other) {
            return this;
        }
        
        @Override
        public Length minus(Length other) {
            return this;
        }
        
    }
}
