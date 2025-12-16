package org.midheaven.collections;

import java.util.Iterator;

final class ConcatTraversable<T> implements Traversable<T> {
    
    private final Traversable<T> last;
    private final Traversable<T> first;
    
    public ConcatTraversable(Traversable<T> first, Traversable<T> last) {
        this.first = first;
        this.last = last;
    }
    
    @Override
    public void close() {
        RuntimeException exception = null;
        try {
            first.close();
        } catch (RuntimeException e){
            exception = e;
        }
        try {
            last.close();
        } catch (RuntimeException e){
            if (exception == null) {
                exception = e;
            }
        }
        if (exception != null){
            throw exception;
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ComposedIterator<>(first.iterator(), last.iterator());
    }
}
