package org.midheaven.collections;

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
    Void newState(Enumerator<O> original, Length finalLength) {
        return null;
    }


    @Override
    PipeMoveResult<T> move(Enumerator<O> original, Void unused) {
        if (original.moveNext()){
            return PipeMoveResult.moved(transform.apply(original.current()));
        }
        return PipeMoveResult.notMoved();
    }

}
