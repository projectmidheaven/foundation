package org.midheaven.collections;

import java.util.function.Consumer;

abstract class Pipe<Original, Final, State> {

    abstract Length estimateLength(Length previousPipeLength);

    abstract State newState(Length finalLength);

    abstract boolean apply(State state, Original candidate, Consumer<Final> consumer);

    void terminate(State state, Consumer<Final> objectConsumer){
        //no-op
    }

    Enumerable<Final> applyTo(Enumerable<Original> previous) {
        return new PipeEnumerable<>(previous, this);
    }
}
