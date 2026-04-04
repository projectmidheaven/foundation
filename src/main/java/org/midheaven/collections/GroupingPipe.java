package org.midheaven.collections;

import java.util.function.Function;


class GroupingPipe<T, G> extends Pipe<T, Association.Entry<G, Enumerable<T>>, Enumerator<Association.Entry<G, Enumerable<T>>>> {
    private final Function<T, G> groupSelector;

    /**
     * Creates a new GroupingPipe.
     * @param groupSelector the groupSelector value
     */
    public GroupingPipe(Function<T, G> groupSelector) {
        this.groupSelector = groupSelector;
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
        return Length.Unknown.INSTANCE;
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
    Enumerator<Association.Entry<G, Enumerable<T>>> newState(Enumerator<T> original, Length finalLength) {
        Association<G, Enumerable<T>> map = Association.builder().resizable().empty();

        while(original.moveNext()){
            var current = original.current();
            var group = groupSelector.apply(current);
            var list = (ResizableSequence<T>) map.computeValueIfAbsent(group, (k) -> Sequence.builder().resizable().empty());
            list.add(current);
        }

        return map.enumerator();
    }


    /**
     * Performs applyTo.
     * @param previous the previous value
     * @return the result of applyTo
     */
    PipeAssociatedEnumerable<G, Enumerable<T>> applyTo(Enumerable<T> previous) {
        return new PipeAssociatedEnumerable<>(new PipeEnumerable<>(previous, this));
    }

    /**
     * Performs move.
     * @param original the original value
     * @param grouppedEnumerator the grouppedEnumerator value
     * @return the result of move
     */
    @Override
    /**
     * Performs move.
     * @param original the original value
     * @param grouppedEnumerator the grouppedEnumerator value
     * @return the result of move
     */
    PipeMoveResult<Association.Entry<G, Enumerable<T>>> move(Enumerator<T> original, Enumerator<Association.Entry<G, Enumerable<T>>> grouppedEnumerator) {
        if (grouppedEnumerator.moveNext()){
            return PipeMoveResult.moved(grouppedEnumerator.current());
        }
        return PipeMoveResult.notMoved();
    }


}
