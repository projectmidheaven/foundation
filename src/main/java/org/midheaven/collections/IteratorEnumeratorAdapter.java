package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Iterator;

public final class IteratorEnumeratorAdapter<T> implements Enumerator<T> {

    private final Iterator<T> iterator;
    private final Length size;

    IteratorEnumeratorAdapter(Iterator<T> iterator, Int size){
        this.iterator = iterator;
        this.size = Length.finite(size);
    }

    IteratorEnumeratorAdapter(Iterator<T> iterator){
        this.iterator = iterator;
        this.size = new Length.Unknown();
    }

    @Override
    public Iterator<T> toIterator(){
        return iterator;
    }

    boolean moved = false;
    T current;
    @Override
    public boolean moveNext() {
        if (iterator.hasNext()){
            moved = true;
            current = iterator.next();
            return true;
        }
        return false;
    }

    @Override
    public T current() {
        if (!moved){
            throw new IllegalStateException();
        }
        return current;
    }

    @Override
    public Length length() {
        return size;
    }
}
