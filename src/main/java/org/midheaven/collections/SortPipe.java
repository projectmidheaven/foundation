package org.midheaven.collections;

import java.util.ArrayList;
import java.util.Comparator;

final class SortPipe<X> extends Pipe<X,X, Enumerator<X>> {

    private final Comparator<X> comparator;

    /**
     * Creates a new SortPipe.
     * @param comparator the comparator value
     */
    public SortPipe(Comparator<X> comparator) {
       this.comparator = comparator;
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
        return previousPipeLength;
    }

    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     * @return the result of newState
     */
    @Override
    /**
     * Creates new State.
     * @param original the original value
     * @param finalLength the finalLength value
     * @return the result of newState
     */
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

    /**
     * Performs move.
     * @param original the original value
     * @param intermediate the intermediate value
     * @return the result of move
     */
    @Override
    /**
     * Performs move.
     * @param original the original value
     * @param intermediate the intermediate value
     * @return the result of move
     */
    PipeMoveResult<X> move(Enumerator<X> original, Enumerator<X> intermediate) {
        if (intermediate.moveNext()){
            return PipeMoveResult.moved(intermediate.current());
        }
        return PipeMoveResult.notMoved();
    }

}
