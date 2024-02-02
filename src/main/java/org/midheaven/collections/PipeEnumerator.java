package org.midheaven.collections;

import org.midheaven.collections.Enumerator;
import org.midheaven.collections.Length;
import org.midheaven.collections.Pipe;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class PipeEnumerator<Original, Final, State> implements Enumerator<Final> {

    private final Enumerator<Original> previous;
    private final Pipe<Original, Final, State> pipe;
    private final State state;
    private final Length length;

    PipeEnumerator(Enumerator<Original> previous, Pipe<Original, Final, State> pipe){
        this.previous = previous;
        this.pipe = pipe;
        this.length = pipe.estimateLength(previous.length());
        this.state = pipe.newState(this.length);
    }

    @Override
    public boolean tryNext(Consumer<Final> action) {
        var flag = new AtomicBoolean(true);
        var originalHasMore = previous.tryNext(sourceItem -> {
            flag.set(pipe.apply(state, sourceItem, action));
        });
        var willContinue = originalHasMore && flag.get();

        if (!willContinue){
            pipe.terminate(state, action);
        }

        return willContinue;
    }

    @Override
    public Length length() {
        return length;
    }
}
