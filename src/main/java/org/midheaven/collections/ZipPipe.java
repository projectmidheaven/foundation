package org.midheaven.collections;

import java.util.function.BiFunction;

class ZipPipe<L, R, Final> extends Pipe<L, Final, Enumerator<R>> {

    private final Enumerable<R> other;
    private final BiFunction<L, R, Final> transform;

    /**
     * Creates a new ZipPipe.
     * @param other the other value
     * @param transform the transform value
     */
    public ZipPipe(Enumerable<R> other, BiFunction<L, R, Final> transform) {
        this.other = other;
        this.transform = transform;
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
        return previousPipeLength.min(other.enumerator().length());
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
    Enumerator<R> newState(Enumerator<L> original, Length finalLength) {
        return other.enumerator();
    }

    /**
     * Performs move.
     * @param original the original value
     * @param rEnumerator the rEnumerator value
     * @return the result of move
     */
    @Override
    /**
     * Performs move.
     * @param original the original value
     * @param rEnumerator the rEnumerator value
     * @return the result of move
     */
    PipeMoveResult<Final> move(Enumerator<L> original, Enumerator<R> rEnumerator) {
        if (original.moveNext() && rEnumerator.moveNext()){
            return PipeMoveResult.moved(transform.apply(original.current(), rEnumerator.current()));
        }
        return PipeMoveResult.notMoved();
    }
}
