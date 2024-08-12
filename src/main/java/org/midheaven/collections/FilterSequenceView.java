package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

class FilterSequenceView<T> implements Sequence<T> {

    private final Sequence<T> original;
    private final Predicate<T> predicate;

    public FilterSequenceView(Sequence<T> original, Predicate<T> predicate) {
        this.original = original;
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Sequence sequence
                && SequencesSupport.equals(this , sequence);
    }

    @Override
    public int hashCode(){
        return HashCode.of(asCollection());
    }

    @Override
    public String toString( ){
        return asCollection().toString();
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
        return Int.MINUS_ONE;
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
        return Int.MINUS_ONE;
    }

    @Override
    public boolean containsAll(Iterable<? extends T> all) {
        for (var candidate : all) {
            if (!contains(candidate)){
                return false;
            }
        }
        return true;
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
    public Maybe<T> getAt(Int index) {
        var iterator =  iterator();
        IntAccumulator position = new IntAccumulator(Int.MINUS_ONE);
        T found = null;
        while (iterator.hasNext() && position.isLessThan(index)) {
            found = iterator.next();
            position.increment();
        }
        return Maybe.of(found);
    }

    @Override
    public Maybe<T> first() {
        var iterator =  iterator();
        if (iterator.hasNext()) {
            return Maybe.of(iterator.next());
        }
        return Maybe.none();
    }

    @Override
    public Maybe<T> last() {
        var reverseIterator =  reverseIterator();
        if (reverseIterator.hasNext()) {
            return Maybe.of(reverseIterator.next());
        }
        return Maybe.none();
    }

    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return new ImmutableSubsequenceView<>(this, fromIndex, toIndex);
    }

    @Override
    public Sequence<T> reversed() {
        return new ReversedImmutableSequenceView<>(this);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return reversed().enumerator().toIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return this.enumerator().toIterator();
    }

    @Override
    public List<T> asCollection() {
        return original.asCollection().stream().filter(predicate).toList();
    }

}