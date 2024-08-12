package org.midheaven.collections;

import org.midheaven.math.Int;

public final class LimitingPipe<T> extends Pipe<T,T, long[]> {

    private final long maxCount;

    public LimitingPipe(long maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.finite(Int.of(maxCount));
    }

    @Override
    long[] newState(Enumerator<T> original, Length finalLength) {
        return new long[]{0};
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, long[] state) {
        if (state[0] < maxCount && original.moveNext()){
            state[0]++;
            return PipeMoveResult.moved(original.current());
        }
        return PipeMoveResult.notMoved();
    }

}
