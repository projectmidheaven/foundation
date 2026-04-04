package org.midheaven.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;


final class FlatMappedTraversable<T, R> implements Traversable<R> {
    
    private final Traversable<T> original;
    private final Function<T, Traversable<R>> mapper;
    
    FlatMappedTraversable(Traversable<T> original, Function<T, Traversable<R>> mapper) {
        this.original = original;
        this.mapper = mapper;
    }
    
    @Override
    public void close() {
        original.close();
    }
    
    @Override
    public Iterator<R> iterator() {
        Iterator<Iterator<R>> all = new TransformIterator<>(original.iterator(), (o) -> mapper.apply(o).iterator());
        
        if (!all.hasNext()){
            return Collections.emptyIterator();
        }
        
        return new Iterator<>() {
            
            Iterator<R> current = all.next();
            
            @Override
            public boolean hasNext() {
                while(true) {
                    if (current.hasNext()) {
                        return true;
                    } else if (all.hasNext()) {
                        current = all.next();
                    } else {
                        return false;
                    }
                }
            }
            
            @Override
            public R next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                return current.next();
            }
        };
    }
}
