package org.midheaven.collections;

import java.util.Iterator;

final class EnumeratorIteratorAdapter<T> implements Iterator<T> {

    private final Enumerator<T> enumerator;
    EnumeratorIteratorAdapter(Enumerator<T> enumerator){
        this.enumerator = enumerator;

    }

    @Override
    public boolean hasNext() {
        return enumerator.moveNext();
    }

    @Override
    public T next() {
        return enumerator.current();
    }
}
