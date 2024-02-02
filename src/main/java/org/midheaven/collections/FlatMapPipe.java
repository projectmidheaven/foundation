package org.midheaven.collections;

import java.util.function.Consumer;
import java.util.function.Function;

class FlatMapPipe<T, O> extends Pipe<O, T, Void> {

    private final Function<O, Enumerable<T>> transform;

    FlatMapPipe(Function<O,Enumerable<T>> transform){
        this.transform = transform;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
    Void newState(Length finalLength) {
        return null;
    }

    @Override
    boolean apply(Void unused, O candidate, Consumer<T> consumer) {

        var pileEnumerator = transform.apply(candidate).enumerator();

        while(pileEnumerator.tryNext(consumer));

        return true;
    }

}
