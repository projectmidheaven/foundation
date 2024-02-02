package org.midheaven.collections;

import java.util.Collection;
import java.util.Iterator;

class ImmutableCollectionWrapper<T> extends AbstractCollectionWrapper<T> implements Assortment<T>{

    private final Collection<T> original;

    ImmutableCollectionWrapper(Collection<T> original){
        this.original = original;
    }

    @Override
    protected Collection<T> original() {
        return original;
    }

    @Override
    public Collection<T> asCollection() {
        return original;
    }

    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }

}
