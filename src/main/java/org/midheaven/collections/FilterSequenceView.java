package org.midheaven.collections;

import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

class FilterSequenceView<T> extends AbstractSequence<T> {

    private final Sequence<T> original;
    private final Predicate<T> predicate;

    public FilterSequenceView(Sequence<T> original, Predicate<T> predicate) {
        this.original = original;
        this.predicate = predicate;
    }
    
    @Override
    public Int indexOf(Object element) {
        IntAccumulator index = new IntAccumulator();
        var enumerator =  this.enumerator();
        while(enumerator.moveNext() && predicate.test(enumerator.current())){
            if (Objects.equals(enumerator.current(),element)){
                return index.get();
            }
            index.increment();
        }
        return Int.NEGATIVE_ONE;
    }

    @Override
    public Int lastIndexOf(Object element) {
        IntAccumulator index = new IntAccumulator(this.count());
        index.decrement();
        var iterator = this.reverseIterator();
        while(iterator.hasNext()){
            var item = iterator.next();
            if (predicate.test(item) && Objects.equals(item, element)){
                return index.get();
            }
            index.decrement();
        }
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public Int count() {

        long count = 0;
        var enumerator =  this.enumerator();
        while(enumerator.moveNext()){
            count++;
        }

        return Int.of(count);
    }

    @Override
    public boolean isEmpty() {
        for (var item : original){
            if (predicate.test(item)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Enumerator<T> enumerator() {
        return new PipeEnumerator<>(original.enumerator(), new FilterPipe<>(predicate));
    }
    
    
    @Override
    public List<T> toCollection() {
        return original.toCollection().stream().filter(predicate).toList();
    }

}
