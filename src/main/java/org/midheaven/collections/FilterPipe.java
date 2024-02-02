package org.midheaven.collections;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class FilterPipe<T> extends Pipe<T,T,Void> {

    private final Predicate<T> predicate;

    public FilterPipe(Predicate<T> predicate){
        this.predicate = predicate;
    }
    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength.filter();
    }

    @Override
    Void newState(Length finalLength) {
        return null;
    }

    @Override
    boolean apply(Void state, T candidate, Consumer<T> consumer) {
        if (predicate.test(candidate)){
            consumer.accept(candidate);
        }
        return true;
    }
}
