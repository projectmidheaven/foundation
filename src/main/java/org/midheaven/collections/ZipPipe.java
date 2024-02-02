package org.midheaven.collections;

import java.util.function.BiFunction;
import java.util.function.Consumer;

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
    Enumerator<R> newState(Length finalLength) {
        return other.enumerator();
    }

    @Override
    boolean apply(Enumerator<R> state, L candidate, Consumer<Final> consumer) {
        return state.tryNext(otherItem -> consumer.accept(transform.apply(candidate, otherItem)));
    }
}
