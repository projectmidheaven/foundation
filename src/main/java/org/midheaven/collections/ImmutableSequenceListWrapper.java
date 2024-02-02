package org.midheaven.collections;

import org.midheaven.lang.HashCode;

import java.util.*;

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

    public int indexOf(Object o) {
        return original.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return original.lastIndexOf(o);
    }

    @Override
    public Optional<T> getAt(int index) {
        try {
            return Optional.ofNullable(original.get(index));
        } catch (IndexOutOfBoundsException e){
            return Optional.empty();
        }
    }


    @Override
    public Optional<T> first() {
        return Optional.ofNullable(original.getFirst());
    }

    @Override
    public Optional<T> last() {
        return Optional.ofNullable(original.getLast());
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
    public Sequence<T> reversed() {
        return new ReversedImmutableSequenceView<>(this);
    }

    @Override
    public List<T> asCollection() {
        return Collections.unmodifiableList(original);
    }
}
