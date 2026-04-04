package org.midheaven.collections;

import org.midheaven.math.Int;

final class SkipPipe<T> extends Pipe<T,T, long[]> {

    private final long minCount;

    /**
     * Creates a new SkipPipe.
     * @param minCount the minCount value
     */
    public SkipPipe(long minCount) {
        this.minCount = minCount;
    }

    /**
     * Performs estimateLength.
     * @param previousPipeLength the previousPipeLength value
     * @return the result of estimateLength
     */
    @Override
    /**
     * Performs estimateLength.
     * @param previousPipeLength the previousPipeLength value
     * @return the result of estimateLength
     */
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength.minus(Length.finite(Int.of(minCount)));
    }

    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     * @return the result of newState
     */
    @Override
    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     * @return the result of newState
     */
    long[] newState(Enumerator<T> original, Length finalLength) {
        int index = 0;
        while(index < minCount && original.moveNext()){
            index++;
        }
        return new long[]{0};
    }

    /**
     * Performs move.
     * @param original the original value
     * @param state the state value
     * @return the result of move
     */
    @Override
    /**
     * Performs move.
     * @param original the original value
     * @param state the state value
     * @return the result of move
     */
    PipeMoveResult<T> move(Enumerator<T> original, long[] state) {
        if (original.moveNext()){
            return PipeMoveResult.moved(original.current());
        }
        return PipeMoveResult.notMoved();
    
    }

}
