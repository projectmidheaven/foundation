package org.midheaven.collections;

import java.util.function.Supplier;

abstract class Pipe<Original, Final, State> {

    abstract Length estimateLength(Length previousPipeLength);

    abstract State newState(Enumerator<Original> original,Length finalLength);

    Enumerable<Final> applyTo(Enumerable<Original> previous) {
        return new PipeEnumerable<>(previous, this);
    }

    abstract PipeMoveResult<Final> move(Enumerator<Original> original, State state);
}

interface PipeMoveResult<T> extends Supplier<T> {

    static <X> PipeMoveResult<X> notMoved() {
        return new PipeMoveResult<>(){

            @Override
            public X get() {
                throw new IllegalStateException();
            }

            @Override
            public boolean wasSuccessful() {
                return false;
            }
        };
    }

    static <X> PipeMoveResult<X> moved(X instance) {
        return new PipeMoveResult<>(){

            @Override
            public X get() {
                return instance;
            }

            @Override
            public boolean wasSuccessful() {
                return true;
            }
        };
    }

    boolean wasSuccessful();
}