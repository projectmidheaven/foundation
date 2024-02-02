package org.midheaven.collections;

import java.util.Collections;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
final class EmptyEnumerable<T> implements Enumerable<T> {

    private static final EmptyEnumerable ME = new EmptyEnumerable();

    @SuppressWarnings("unchecked")
    static <S> Enumerable<S> instance(){
        return ME;
    }

    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.empty();
    }

    @Override
    public Iterator<T> iterator() {
        return Collections.emptyIterator();
    }
}
