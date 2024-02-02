package org.midheaven.collections;

import java.util.Iterator;

public abstract class AbstractEnumerableDecorator<T> implements Enumerable<T> {

    private final Enumerable<T> original;

    public AbstractEnumerableDecorator(Enumerable<T> original){
        this.original = original;
    }

    @Override
    public final Enumerator<T> enumerator() {
        return original.enumerator();
    }

    @Override
    public final Iterator<T> iterator() {
        return original.iterator();
    }
}
