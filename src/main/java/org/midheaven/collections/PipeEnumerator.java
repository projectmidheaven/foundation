package org.midheaven.collections;

public class PipeEnumerator<Original, Final, State> implements Enumerator<Final> {

    private final Enumerator<Original> original;
    private final Pipe<Original, Final, State> pipe;
    private final State state;
    private final Length length;

    private Final current;
    private boolean moved;
    PipeEnumerator(Enumerator<Original> original, Pipe<Original, Final, State> pipe){
        this.original = original;
        this.pipe = pipe;
        this.length = pipe.estimateLength(original.length());
        this.state = pipe.newState(original, this.length);
    }

    @Override
    public boolean moveNext() {
        if (!moved){
            moved = true;
        }
        var move =  pipe.move(original, state);
        if (move.wasSuccessful()){
            this.current = move.get();
            return true;
        }
        return false;
    }

    @Override
    public Final current() {
        if (!moved){
            throw new IllegalStateException();
        }
        return current;
    }

    @Override
    public Length length() {
        return length;
    }
}
