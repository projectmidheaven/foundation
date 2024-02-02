package org.midheaven.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

public final class SortPipe<X> extends Pipe<X,X, ArrayList<X>> {

    private final Comparator<X> comparator;

    public SortPipe(Comparator<X> comparator) {
       this.comparator = comparator;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength;
    }

    @Override
    ArrayList<X> newState(Length length) {
        if (length instanceof Length.Finite finite){
            return new ArrayList<>((int)finite.count());
        } else if (length instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }
        return new ArrayList<>();
    }

    @Override
    boolean apply(ArrayList<X> state, X candidate, Consumer<X> objectConsumer) {
        state.add(candidate);
        return true;
    }

    @Override
    void terminate(ArrayList<X> state, Consumer<X> objectConsumer){

        state.sort(comparator);
        state.forEach(objectConsumer);
    }
}
