package org.midheaven.collections;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

class IteratePipe<T> extends Pipe<T,T, ArrayList<T>> {
    private final T seed;
    private final Function<T, T> successor;

    public IteratePipe(T seed, Function<T, T> successor) {
        this.seed = seed;
        this.successor = successor;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Infinite.INSTANCE;
    }

    @Override
    ArrayList<T> newState(Length length) {
        var array = new ArrayList<T>(1);
        array.add(seed);
        return array;
    }

    @Override
    boolean apply(ArrayList<T> state, T candidate, Consumer<T> consumer) {
        var current = state.getFirst();
        consumer.accept(current);
        state.set(0, successor.apply(current));
        return true;
    }

}
