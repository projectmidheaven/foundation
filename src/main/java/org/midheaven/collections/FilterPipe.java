package org.midheaven.collections;

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
    Void newState(Enumerator<T> original, Length finalLength) {
        return null;
    }

    @Override
    PipeMoveResult<T> move(Enumerator<T> original, Void unused) {
       while(original.moveNext()){
           var current = original.current();
           if (predicate.test(current)){
               return PipeMoveResult.moved(current);
           }
       }
       return PipeMoveResult.notMoved();
    }
}
