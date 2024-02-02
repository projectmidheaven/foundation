package org.midheaven.collections;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class GroupingPipe<T, G> extends Pipe<T, Association.Entry<G, Enumerable<T>>, Map<G, List<T>>> {
    private final Function<T, G> groupSelector;

    public GroupingPipe(Function<T, G> groupSelector) {
        this.groupSelector = groupSelector;
    }

    @Override
    Length estimateLength(Length previousPipeLength) {
        return Length.Unknown.INSTANCE;
    }

    @Override
    Map<G, List<T>> newState(Length length) {
        return new HashMap<>();
    }

    @Override
    boolean apply(Map<G, List<T>> state, T candidate, Consumer<Association.Entry<G, Enumerable<T>>> objectConsumer) {
        var group = groupSelector.apply((T)candidate);
        state.computeIfAbsent(group, (k) -> new LinkedList<>()).add(candidate);
        return true;
    }

    void terminate(Map<G, List<T>> state, Consumer<Association.Entry<G, Enumerable<T>>> objectConsumer){
        state.forEach((key, value) -> objectConsumer.accept(Association.Entry.entry(
                key,
                new EditableSequenceListWrapper<T>(value)
        )));
    }


    PipeAssociatedEnumerable<G, Enumerable<T>> applyTo(Enumerable<T> previous) {
        return new PipeAssociatedEnumerable<>(new PipeEnumerable<>(previous, this));
    }
}
