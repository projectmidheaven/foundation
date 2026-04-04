package org.midheaven.collections;

import java.util.function.Predicate;

class FilterPipe<T> extends Pipe<T,T,Void> {

    private final Predicate<T> predicate;

    /**
     * Creates a new FilterPipe.
     * @param predicate the predicate value
     */
    public FilterPipe(Predicate<T> predicate){
        this.predicate = predicate;
    }
    /**
     * Performs estimateLength.
     * @param previousPipeLength the previousPipeLength value
     * @return the result of estimateLength
     */
    @Override
    /**
     * Performs estimateLength.
     * @param previousPipeLength the previousPipeLength value
     * @return the result of estimateLength
     */
    Length estimateLength(Length previousPipeLength) {
        return previousPipeLength.filter();
    }

    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     */
    @Override
    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     */
    Void newState(Enumerator<T> original, Length finalLength) {
        return null;
    }

    /**
     * Performs move.
     * @param original the original value
     * @param unused the unused value
     * @return the result of move
     */
    @Override
    /**
     * Performs move.
     * @param original the original value
     * @param unused the unused value
     * @return the result of move
     */
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
