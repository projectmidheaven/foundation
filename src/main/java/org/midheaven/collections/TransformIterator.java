package org.midheaven.collections;

import java.util.Iterator;
import java.util.function.Function;


class TransformIterator<O, T> implements Iterator<T> {

    private final Iterator<O> original;
    private final Function<O, T> transformation;

    TransformIterator(Iterator<O> original, Function<O, T> transformation) {
        this.original = original;
        this.transformation = transformation;
    }

    @Override
    public boolean hasNext() {
        return original.hasNext();
    }

    @Override
    public T next() {
        return transformation.apply(original.next());
    }

    @Override
    public void remove() {
        original.remove();
    }
}
