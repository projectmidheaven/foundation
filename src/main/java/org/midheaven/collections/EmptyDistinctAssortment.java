package org.midheaven.collections;

import java.util.Collections;
import java.util.Set;

public class EmptyDistinctAssortment<T> implements DistinctAssortment<T> {

    private static final EmptyDistinctAssortment ME = new EmptyDistinctAssortment();

    public static <S> DistinctAssortment<S> instance(){
        return ME;
    }

    @Override
    public boolean containsAll(Iterable<? extends T> all){
        return false;
    }

    @Override
    public boolean contains(T any) {
        return false;
    }

    @Override
    public Set<T> asCollection() {
        return Collections.emptySet();
    }

    @Override
    public Enumerator<T> enumerator() {
        return Enumerator.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
