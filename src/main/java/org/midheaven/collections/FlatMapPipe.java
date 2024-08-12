package org.midheaven.collections;

import java.util.LinkedList;
import java.util.function.Function;

class FlatMapPipe<T, O> extends Pipe<O, T, LinkedList<Enumerator<T>>> {

    private final Function<O, Enumerable<T>> transform;

    FlatMapPipe(Function<O,Enumerable<T>> transform){
        this.transform = transform;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
    LinkedList<Enumerator<T>> newState(Enumerator<O> original, Length finalLength) {
        return new LinkedList<>();
    }

    @Override
    PipeMoveResult<T> move(Enumerator<O> original, LinkedList<Enumerator<T>> enumerators) {
        if (enumerators.isEmpty()){
            // no enumerator currently being read
            if (original.moveNext()){
                var pileEnumerator = transform.apply(original.current()).enumerator();
                enumerators.addFirst(pileEnumerator);
            } else {
                return PipeMoveResult.notMoved();
            }
        }
        var pileEnumerator = enumerators.getFirst();
        if (pileEnumerator.moveNext()){
            return PipeMoveResult.moved(pileEnumerator.current());
        } else {
            enumerators.clear();
            return move(original, enumerators);
        }
    }

}
