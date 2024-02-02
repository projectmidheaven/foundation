package org.midheaven.collections;

import java.util.Iterator;
import java.util.function.Consumer;

final class GeneratorEnumerable<T, S> implements Enumerable<T>{

    private final Pipe<T, T, S> pipe;

    GeneratorEnumerable(Pipe<T, T, S> pipe){
        this.pipe = pipe;
    }

    @Override
    public Enumerator<T> enumerator() {
        return new Enumerator<>(){
            final S state = pipe.newState(Length.Infinite.INSTANCE);

            @Override
            public boolean tryNext(Consumer<T> consumer) {
                return pipe.apply(state, null, consumer);
            }

            @Override
            public Length length() {
                return Length.Infinite.INSTANCE;
            }
        };
    }
}
