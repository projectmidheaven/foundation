package org.midheaven.lang;

import org.midheaven.math.Int;

import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Base implementation for Indexed Splitter.
 */
abstract sealed class AbstractIndexedSplitter implements Strings.Splitter
    permits ArraySplitter, BackwardsShiftSplitter, CharSplitter, ForwardShiftSplitter, ListSplitter, MappedIndexedSplitter {
    
    @Override
    public Strings.Splitter map(Function<String, String> transform) {
        return new MappedIndexedSplitter(this, transform);
    }
    
    @Override
    public final Int count() {
        return Int.of(length());
    }
    
    protected abstract int length();
    
    @Override
    public final String get(int index) {
        if (index < 0 || index >= this.length()){
            throw new IndexOutOfBoundsException(index);
        }
        return secureGetAt(index);
    }
    
    protected abstract String secureGetAt(int index);
    
    @Override
    public Strings.Splitter withoutFirst() {
        if (this.length() == 1){
            return EmptySplitter.instance();
        }
        return new ForwardShiftSplitter(this);
    }
    
    @Override
    public Strings.Splitter withoutLast() {
        if (this.length() == 1){
            return EmptySplitter.instance();
        }
        return new BackwardsShiftSplitter(this);
    }
    
    @Override
    public Maybe<String> first() {
        return isEmpty()
                   ? Maybe.none()
                   : Maybe.of(secureGetAt(0));
    }
    
    @Override
    public Maybe<String> last() {
        return isEmpty()
                   ? Maybe.none()
                   : Maybe.of(secureGetAt(length() - 1));
    }
    
    @Override
    public <A> String collect(Collector<CharSequence, A, String> collector) {
        var result = collector.supplier().get();
        var acc = collector.accumulator();
        for (int i =0; i < this.length(); i++){
            acc.accept(result, get(i));
        }
        
        return collector.finisher().apply(result);
    }
    
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
}
