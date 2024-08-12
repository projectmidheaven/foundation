package org.midheaven.collections;

import java.util.function.Supplier;

class GenerationPipe<T> extends Pipe<T, T, Void> {

    private final Supplier<T> supplier;

    public GenerationPipe(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Infinite.INSTANCE;
    }

    @Override
    Void newState(Enumerator<T> original, Length finalLength) {
        return null;
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, Void unused) {
        return PipeMoveResult.moved(supplier.get());
    }


}
