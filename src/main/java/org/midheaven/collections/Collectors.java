package org.midheaven.collections;

import java.util.stream.Collector;

/**
 * Utility class for {@link Collector}s related to the Assortments API.
 */
public class Collectors {
    
    /**
     * Returns to Sequence.
     * @return the result of toSequence
     */
    public static <T> Collector<T, ?, Sequence<T>> toSequence() {
        return new SequenceCollector<>();
    }
    
  
    private Collectors(){}
}
