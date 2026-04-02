package org.midheaven.collections;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.math.Int;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class FilterDistinctAssortmentView<T> implements DistinctAssortment<T> {

    private final DistinctAssortment<T> original;
    private final Predicate<T> predicate;

    public FilterDistinctAssortmentView(DistinctAssortment<T> original, Predicate<T> predicate) {
        this.original = original;
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Sequence sequence
                && AssortmentSupport.equals(this , sequence);
    }

    @Override
    public int hashCode(){
        return HashCode.of(this.toCollection());
    }

    @Override
    public String toString( ){
        return this.toCollection().toString();
    }
    
    @Override
    public boolean contains(T other) {
        return predicate.test(other) && original.contains(other);
    }
    
    @Override
    public Int count() {

        long count = 0;
        var enumerator =  this.enumerator();
        while(enumerator.moveNext()){
            count++;
        }

        return Int.of(count);
    }

    @Override
    public boolean isEmpty() {
        for (var item : original){
            if (predicate.test(item)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Enumerator<T> enumerator() {
        return new PipeEnumerator<>(original.enumerator(), new FilterPipe<>(predicate));
    }
    
    @Override
    public Maybe<T> first() {
        var iterator =  iterator();
        if (iterator.hasNext()) {
            return Maybe.of(iterator.next());
        }
        return Maybe.none();
    }


    @Override
    public Iterator<T> iterator() {
        return this.enumerator().toIterator();
    }

    @Override
    public Set<T> toCollection() {
        return original.toCollection().stream().filter(predicate).collect(Collectors.toUnmodifiableSet());
    }

}