package org.midheaven.collections;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Enumerator<T> {

    static <T> Enumerator<T> empty() {
        return new Enumerator<>(){

            @Override
            public boolean tryNext(Consumer<T> consumer) {
                return false;
            }

            @Override
            public Length length() {
                return Length.finite(0);
            }
        };
    }

    boolean tryNext(Consumer<T> consumer);

    Length length();

    default Iterator<T> toIterator(){
        return new EnumeratorIteratorAdapter<>(this);
    }
}
