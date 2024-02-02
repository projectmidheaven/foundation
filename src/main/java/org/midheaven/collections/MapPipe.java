package org.midheaven.collections;

import java.util.function.Consumer;
import java.util.function.Function;

class MapPipe<T, O> extends Pipe<O, T, Void> {

    private final Function<O, T> transform;

    MapPipe(Function<O,T> transform){
        this.transform = transform;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength;
    }

    @Override
    Void newState(Length finalLength) {
        return null;
    }

    @Override
    boolean apply(Void unused, O candidate, Consumer<T> consumer) {
        consumer.accept(transform.apply(candidate));
        return true;
    }

}
