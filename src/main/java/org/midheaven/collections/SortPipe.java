package org.midheaven.collections;

import java.util.ArrayList;
import java.util.Comparator;

public final class SortPipe<X> extends Pipe<X,X, Enumerator<X>> {

    private final Comparator<X> comparator;

    public SortPipe(Comparator<X> comparator) {
       this.comparator = comparator;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength;
    }

    @Override
    Enumerator<X> newState(Enumerator<X> original, Length finalLength) {
        if (finalLength instanceof Length.Infinite){
            throw new IllegalStateException("Infinite enumerable cannot be made into a sequence");
        }

        ArrayList<X> list;
        if (finalLength instanceof Length.Finite finite){
            list = new ArrayList<>((int)finite.count().toLong());
        } else {
            list =  new ArrayList<>();
        }

        while(original.moveNext()){
            list.add(original.current());
        }
        list.sort(comparator);
        return Sequence.builder().from(list).enumerator();
    }

    @Override
    PipeMoveResult<X> move(Enumerator<X> original, Enumerator<X> intermediate) {
        if (intermediate.moveNext()){
            return PipeMoveResult.moved(intermediate.current());
        }
        return PipeMoveResult.notMoved();
    }

}
