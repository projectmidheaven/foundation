package org.midheaven.collections;

public abstract sealed class Length
        permits Length.Finite , Length.Infinite , Length.Unknown
{

    public static Length finite(long count) {
        return new Finite(count);
    }

    public Length filter() {
        return this;
    }

    public abstract Length min(Length other);

    public static final class Finite extends Length {

        private final long count;

        Finite(long count){
            this.count = count;
        }

        public long count(){
            return count;
        }

        @Override
        public Length filter() {
            return Unknown.INSTANCE;
        }

        @Override
        public Length min(Length other) {
            if (other instanceof Finite finite){
                return new Finite(Long.min(this.count , finite.count));
            } else if (other instanceof Infinite){
                return this;
            }
            return other;
        }
    }

    public static final class Infinite extends Length {
        static final Infinite INSTANCE = new Infinite();

        @Override
        public Length min(Length other) {
            return other;
        }
    }

    public static final class Unknown extends Length {
        static final Unknown INSTANCE = new Unknown();

        @Override
        public Length min(Length other) {
            return this;
        }
    }
}