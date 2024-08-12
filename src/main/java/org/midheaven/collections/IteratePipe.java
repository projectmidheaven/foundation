package org.midheaven.collections;

import java.util.ArrayList;
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
    ArrayList<T> newState(Enumerator<T> original, Length finalLength) {
        var array = new ArrayList<T>(1);
        array.add(seed);
        return array;
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, ArrayList<T> state) {
        var current = state.getFirst();
        state.set(0, successor.apply(current));
        return PipeMoveResult.moved(current);
    }

}
