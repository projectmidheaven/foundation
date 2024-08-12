package org.midheaven.collections;

import java.util.HashSet;
import java.util.Set;

public class DistintPipe<T> extends Pipe<T, T, Set<T>> {

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
    Set<T> newState(Enumerator<T> original, Length finalLength) {
        return new HashSet<T>();
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, Set<T> distinct) {
        while (original.moveNext()){
            var current = original.current();
            if (distinct.add(current)){
                return PipeMoveResult.moved(current);
            }
        }
        return PipeMoveResult.notMoved();
    }

}
