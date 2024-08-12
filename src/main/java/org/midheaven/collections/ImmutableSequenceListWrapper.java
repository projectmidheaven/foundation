package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;

class ImmutableSequenceListWrapper<T> extends AbstractCollectionWrapper<T> implements Sequence<T> {

    protected final List<T> original;

    ImmutableSequenceListWrapper(List<T> original){
        this.original = original;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Sequence sequence
                && SequencesSupport.equals(this , sequence);
    }

    @Override
    public int hashCode( ){
        return HashCode.of(original);
    }

    @Override
    public String toString( ){
        return original.toString();
    }

    public Spliterator<T> spliterator() {
        return original.spliterator();
    }

    @Override
    protected Collection<T> original() {
        return original;
    }

    public Int indexOf(Object o) {
        return Int.of(original.indexOf(o));
    }

    public Int lastIndexOf(Object o) {
        return Int.of(original.lastIndexOf(o));
    }

    @Override
    public Maybe<T> getAt(int index) {
        try {
            return Maybe.of(original.get(index));
        } catch (IndexOutOfBoundsException e){
            return Maybe.none();
        }
    }

    @Override
    public Maybe<T> getAt(Int index) {
        try {
            return Maybe.of(original.get(index.toInt()));
        } catch (ArithmeticException | IndexOutOfBoundsException e){
            return Maybe.none();
        }
    }

    @Override
    public Maybe<T> first() {
        return Maybe.of(original.getFirst());
    }

    @Override
    public Maybe<T> last() {
        return Maybe.of(original.getLast());
    }

    @Override
    public ListIterator<T> iterator() {
        return original.listIterator();
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return original.listIterator(original.size());
    }

    @Override
    public Sequence<T> subSequence(int fromIndex, int toIndex) {
        return new ImmutableSequenceListWrapper<>(original.subList(fromIndex, toIndex));
    }

    @Override
    public Sequence<T> subSequence(Int fromIndex, Int toIndex) {
        return subSequence(fromIndex.toInt(), toIndex.toInt());
    }

    @Override
    public Sequence<T> reversed() {
        return new ReversedImmutableSequenceView<>(this);
    }

    @Override
    public List<T> asCollection() {
        return Collections.unmodifiableList(original);
    }
}
