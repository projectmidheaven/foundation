package org.midheaven.collections;

import java.util.stream.Collector;

public class Collectors {
    
    public static <T> Collector<T, ?, Sequence<T>> toSequence() {
        return new SequenceCollector<>();
    }
    
    private Collectors(){}
}
