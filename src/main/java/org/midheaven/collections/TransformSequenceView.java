package org.midheaven.collections;

import org.midheaven.lang.HashCode;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
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
    public int indexOf(Object element) {
        var index = 0;
        for (var item : original){
            if (Objects.equals(item, element)){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object element) {
        var index = size() - 1;
        var iterator = this.reverseIterator();
        while(iterator.hasNext()){
            var item = iterator.next();
            if (Objects.equals(item, element)){
                return index;
            }
            index--;
        }
        return -1;
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
    public long count() {
        return original.count();
    }

    @Override
    public int size() {
        return original.size();
    }

    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public Enumerator<T> enumerator() {
        return new IteratorEnumeratorAdapter<>(this.iterator(), this.size());
    }

    public <R> Sequence<R> map(Function<T, R> transform) {
        return new TransformSequenceView<>(original, transformation.andThen(transform));
    }

    @Override
    public Optional<T> getAt(int index) {
        return original.getAt(index).map(transformation::apply);
    }


    @Override
    public Optional<T> first() {
        return original.first().map(transformation::apply);
    }

    @Override
    public Optional<T> last() {
        return original.last().map(transformation::apply);
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new TransformSequenceView<>(original.subSequence(fromIndex, toIndex), transformation);
    }

    @Override
    public Sequence<T> reversed() {
        return new TransformSequenceView<>(original.reversed(),transformation);
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return new TransformListIterator<>(original.reverseIterator(), transformation);
    }

    @Override
    public ListIterator<T> iterator() {
        return new TransformListIterator<>(original.iterator(), transformation);
    }



    @Override
    public List<T> asCollection() {
        return original.asCollection().stream().map(transformation).toList();
    }

}

class TransformListIterator<O,T> implements ListIterator<T> {

    private final ListIterator<O> original;
    private final Function<O, T> transformation;

    TransformListIterator(ListIterator<O> original, Function<O,T> transformation){
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
    public boolean hasPrevious() {
        return original.hasPrevious();
    }

    @Override
    public T previous() {
        return transformation.apply(original.previous());
    }

    @Override
    public int nextIndex() {
        return original.nextIndex();
    }

    @Override
    public int previousIndex() {
        return original.previousIndex();
    }

    @Override
    public void remove() {
        original.remove();
    }

    @Override
    public void set(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException();
    }
}