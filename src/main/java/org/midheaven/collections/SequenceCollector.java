package org.midheaven.collections;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class SequenceCollector<T> implements Collector<T, ResizableSequence<T>, Sequence<T>> {
    
    @Override
    public Supplier<ResizableSequence<T>> supplier() {
        return () -> Sequence.builder().resizable().empty();
    }
    
    @Override
    public BiConsumer<ResizableSequence<T>, T> accumulator() {
        return (r, item ) -> r.add(item);
    }
    
    @Override
    public BinaryOperator<ResizableSequence<T>> combiner() {
        return (a, b ) -> {
            a.addAll(b);
            return a;
        };
    }
    
    @Override
    public Function<ResizableSequence<T>, Sequence<T>> finisher() {
        return ResizableSequence::toSequence;
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
