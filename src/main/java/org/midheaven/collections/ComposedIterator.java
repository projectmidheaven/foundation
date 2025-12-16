package org.midheaven.collections;

import java.util.Iterator;

class ComposedIterator<T> implements Iterator<T> {
    
    private Iterator<T> current;
    private Iterator<T> next;
    
    public ComposedIterator(Iterator<T> first, Iterator<T> last) {
        this.current = first;
        this.next = last;
    }
    
    @Override
    public boolean hasNext() {
        if(current.hasNext()){
            return true;
        } else if (next != null) {
            current = next;
            next = null;
            return current.hasNext();
        }
        return false;
    }
    
    @Override
    public T next() {
        return current.next();
    }
}
