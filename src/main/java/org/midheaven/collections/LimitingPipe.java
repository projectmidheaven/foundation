package org.midheaven.collections;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public final class LimitingPipe<T> extends Pipe<T,T, AtomicLong> {

    private final long maxCount;

    public LimitingPipe(long maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.finite(maxCount);
    }

    @Override
    AtomicLong newState(Length length) {
        return new AtomicLong(0);
    }

    @Override
    boolean apply(AtomicLong state, T candidate, Consumer<T> consumer) {
        if (state.get() < maxCount){
            consumer.accept(candidate);
            return state.incrementAndGet()  < maxCount;
        }
        return false;
    }

}
