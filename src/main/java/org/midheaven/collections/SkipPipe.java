package org.midheaven.collections;

import org.midheaven.math.Int;

public final class SkipPipe<T> extends Pipe<T,T, long[]> {

    private final long minCount;

    public SkipPipe(long minCount) {
        this.minCount = minCount;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength.minus(Length.finite(Int.of(minCount)));
    }

    @Override
    long[] newState(Enumerator<T> original, Length finalLength) {
        int index = 0;
        while(index < minCount && original.moveNext()){
            index++;
        }
        return new long[]{0};
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, long[] state) {
        if (original.moveNext()){
            return PipeMoveResult.moved(original.current());
        }
        return PipeMoveResult.notMoved();
    
    }

}
