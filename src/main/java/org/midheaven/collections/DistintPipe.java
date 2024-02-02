package org.midheaven.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class DistintPipe<T> extends Pipe<T, T, Set<T>> {

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
    Set<T> newState(Length length) {
        return new HashSet<T>();
    }

    @Override
    boolean apply(Set<T> state, T candidate, Consumer<T> objectConsumer) {
        if (state.add(candidate)){
            objectConsumer.accept(candidate);
        }
        return true;
    }

}
