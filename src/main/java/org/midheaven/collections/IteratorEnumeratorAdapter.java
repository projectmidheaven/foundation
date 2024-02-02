package org.midheaven.collections;

import java.util.Iterator;
import java.util.function.Consumer;

public final class IteratorEnumeratorAdapter<T> implements Enumerator<T> {

    private final Iterator<T> iterator;
    private final long size;

    IteratorEnumeratorAdapter(Iterator<T> iterator, long size){
        this.iterator = iterator;
        this.size = size;
    }

    @Override
    public boolean tryNext(Consumer<T> consumer) {
        if (iterator.hasNext()){
            consumer.accept(iterator.next());
        }
        return iterator.hasNext();
    }

    @Override
    public Length length() {
        return Length.finite(size);
    }
}
