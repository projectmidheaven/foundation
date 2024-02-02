package org.midheaven.collections;

import java.util.function.Consumer;
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
    Void newState(Length length) {
        return null;
    }

    @Override
    boolean apply(Void state, T candidate, Consumer<T> objectConsumer) {
        objectConsumer.accept(supplier.get());
        return true;
    }

}
