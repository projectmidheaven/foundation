package org.midheaven.collections;

import java.util.Collections;
import java.util.Iterator;

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

//    @Override
//    public Iterator<Final> iterator() {
//        var enumerator = this.enumerator();
//
//        if (enumerator.length() instanceof Length.Finite finite && finite.count() == 0){
//            return Collections.emptyIterator();
//        }
//
//
//        return new Iterator<>() {
//            final Object[] current = new Object[1];
//            @Override
//            public boolean hasNext() {
//                return enumerator.tryNext(it -> current[0] = it);
//            }
//
//            @Override
//            public Final next() {
//                return (Final)current[0];
//            }
//        };
//    }






}
