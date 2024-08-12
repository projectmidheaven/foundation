package org.midheaven.collections;

class PipeEnumerable<Original, Final, State> implements Enumerable<Final> {

    private final Enumerable<Original> previous;
    private final Pipe<Original, Final, State> pipe;

    PipeEnumerable(Enumerable<Original> previous, Pipe<Original, Final, State> pipe){
        this.previous = previous;
        this.pipe = pipe;
    }

    public Enumerator<Final> enumerator(){
        return new PipeEnumerator<>(previous.enumerator(), pipe);
    }

}
