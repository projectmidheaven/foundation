package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

final class TransformSequenceView<O, T> implements Sequence<T>{

    final Sequence<O> original;
    final Function<O, T> transformation;

    TransformSequenceView( Sequence<O> original, Function<O, T> transformation){
        this.original = original;
        this.transformation = transformation;
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
        for (var item : original){
            if (Objects.equals(item, element)){
                return index.get();
            }
            index.increment();
        }
        return Int.MINUS_ONE;
    }

    @Override
    public Int lastIndexOf(Object element) {
        Int index = this.count().minus(1);
        var iterator = this.reverseIterator();
        while(iterator.hasNext()){
            var item = iterator.next();
            if (Objects.equals(item, element)){
                return index;
            }
            index = index.minus(1);
        }
        return Int.MINUS_ONE;
    }

    @Override
    public boolean containsAll(Iterable<? extends T> all) {
        for (var candidate : all) {
            boolean found = false;
            for (var item : original) {
                if (Objects.equals(item, candidate)) {
                    found = true;
                    break;
                }
            }
            if (!found){
                return false;
            }
        }
        return true;
    }

    @Override
    public Int count() {
        return original.count();
    }

    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(this.iterator(), this.count());
    }

    public <R> Sequence<R> map(Function<T, R> transform) {
        return new TransformSequenceView<>(original, transformation.andThen(transform));
    }

    @Override
    public Maybe<T> getAt(int index) {
        return original.getAt(index).map(transformation::apply);
    }

    @Override
    public Maybe<T> getAt(Int index) {
        return original.getAt(index).map(transformation::apply);
    }

    @Override
    public Maybe<T> first() {
        return original.first().map(transformation::apply);
    }

    @Override
    public Maybe<T> last() {
        return original.last().map(transformation::apply);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new TransformSequenceView<>(original.subSequence(fromIndex, toIndex), transformation);
    }

    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return new TransformSequenceView<>(original.subSequence(fromIndex, toIndex), transformation);
    }

    @Override
    public Sequence<T> reversed() {
        return new TransformSequenceView<>(original.reversed(),transformation);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return new TransformIterator<>(original.reverseIterator(), transformation);
    }

    @Override
    public Iterator<T> iterator() {
        return new TransformIterator<>(original.iterator(), transformation);
    }


    @Override
    public List<T> asCollection() {
        return original.asCollection().stream().map(transformation).toList();
    }

}

class TransformIterator<O,T> implements Iterator<T> {

    private final Iterator<O> original;
    private final Function<O, T> transformation;

    TransformIterator(Iterator<O> original, Function<O,T> transformation){
        this.original = original;
        this.transformation = transformation;
    }

    @Override
    public boolean hasNext() {
        return original.hasNext();
    }

    @Override
    public T next() {
        return transformation.apply(original.next());
    }

    @Override
    public void remove(){
        original.remove();
    }
}