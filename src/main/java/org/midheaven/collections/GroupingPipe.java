package org.midheaven.collections;

import java.util.function.Function;

public class GroupingPipe<T, G> extends Pipe<T, Association.Entry<G, Enumerable<T>>, Enumerator<Association.Entry<G, Enumerable<T>>>> {
    private final Function<T, G> groupSelector;

    public GroupingPipe(Function<T, G> groupSelector) {
        this.groupSelector = groupSelector;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
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


    PipeAssociatedEnumerable<G, Enumerable<T>> applyTo(Enumerable<T> previous) {
        return new PipeAssociatedEnumerable<>(new PipeEnumerable<>(previous, this));
    }

    @Override
    PipeMoveResult<Association.Entry<G, Enumerable<T>>> move(Enumerator<T> original, Enumerator<Association.Entry<G, Enumerable<T>>> grouppedEnumerator) {
        if (grouppedEnumerator.moveNext()){
            return PipeMoveResult.moved(grouppedEnumerator.current());
        }
        return PipeMoveResult.notMoved();
    }


}
