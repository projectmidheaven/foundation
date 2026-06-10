package org.midheaven.collections;

import java.util.Iterator;


class ImmutableIterableWrapper<T> implements Enumerable<T>{

    private final Iterable original;

    ImmutableIterableWrapper(Iterable<? extends T> original){
        this.original = original;
    }

    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.fromIterator(this.iterator());
    }

    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }

}
