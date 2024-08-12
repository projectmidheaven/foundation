package org.midheaven.collections;

import java.util.function.BiFunction;

public class ZipPipe<L, R, Final> extends Pipe<L, Final, Enumerator<R>> {

    private final Enumerable<R> other;
    private final BiFunction<L, R, Final> transform;

    public ZipPipe(Enumerable<R> other, BiFunction<L, R, Final> transform) {
        this.other = other;
        this.transform = transform;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength.min(other.enumerator().length());
    }

    @Override
    Enumerator<R> newState(Enumerator<L> original, Length finalLength) {
        return other.enumerator();
    }

    @Override
    PipeMoveResult<Final> move(Enumerator<L> original, Enumerator<R> rEnumerator) {
        if (original.moveNext() && rEnumerator.moveNext()){
            return PipeMoveResult.moved(transform.apply(original.current(), rEnumerator.current()));
        }
        return PipeMoveResult.notMoved();
    }
}
