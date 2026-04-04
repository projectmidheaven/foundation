package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Iterator;


final class IteratorEnumeratorAdapter<T> implements Enumerator<T> {

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

    /**
     * Returns to Iterator.
     * @return the result of toIterator
     */
    @Override
    /**
     * Returns to Iterator.
     * @return the result of toIterator
     */
    public Iterator<T> toIterator(){
        return iterator;
    }

    boolean moved = false;
    T current;
    /**
     * Performs move Next.
     * @return the result of moveNext
     */
    @Override
    /**
     * Performs move Next.
     * @return the result of moveNext
     */
    public boolean moveNext() {
        if (iterator.hasNext()){
            moved = true;
            current = iterator.next();
            return true;
        }
        return false;
    }

    /**
     * Performs current.
     * @return the result of current
     */
    @Override
    /**
     * Performs current.
     * @return the result of current
     */
    public T current() {
        if (!moved){
            throw new IllegalStateException();
        }
        return current;
    }

    /**
     * Performs length.
     * @return the result of length
     */
    @Override
    /**
     * Performs length.
     * @return the result of length
     */
    public Length length() {
        return size;
    }
}
