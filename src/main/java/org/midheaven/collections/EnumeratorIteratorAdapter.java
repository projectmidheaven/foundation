package org.midheaven.collections;

import java.util.Iterator;

final class EnumeratorIteratorAdapter<T> implements Iterator<T> {

    private final Enumerator<T> enumerator;
    final Object[] current = new Object[]{null , false};

    EnumeratorIteratorAdapter(Enumerator<T> enumerator){
        this.enumerator = enumerator;

    }

    @Override
    public boolean hasNext() {
        current[1] = false;
        enumerator.tryNext(it -> {
            current[0] = it;
            current[1] = true;
        } );
        return (boolean)current[1];
    }

    @Override
    public T next() {
        return (T)current[0];
    }
}
