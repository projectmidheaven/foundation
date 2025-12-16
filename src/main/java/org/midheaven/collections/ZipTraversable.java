package org.midheaven.collections;

import java.util.Iterator;
import java.util.function.BiFunction;

final class ZipTraversable<T, O , R> implements Traversable<R> {
    
    private final Traversable<T> left;
    private final Traversable<O> right;
    private final BiFunction<T, O, R> zipper;
    
    public ZipTraversable(Traversable<T> left, Traversable<O> right, BiFunction<T, O, R> zipper) {
        this.left = left;
        this.right = right;
        this.zipper = zipper;
    }
    
    @Override
    public void close() {
        RuntimeException exception = null;
        try {
            left.close();
        } catch (RuntimeException e){
            exception = e;
        }
        try {
            right.close();
        } catch (RuntimeException e){
            if (exception == null) {
                exception = e;
            }
        }
        if (exception != null){
            throw exception;
        }
    }
    
    @Override
    public Iterator<R> iterator() {
        return new Iterator<R>() {
            
            final Iterator<T> a = left.iterator();
            final Iterator<O> b = right.iterator();
            
            @Override
            public boolean hasNext() {
                return a.hasNext() && b.hasNext();
            }
            
            @Override
            public R next() {
                return zipper.apply(a.next(), b.next());
            }
        };
    }
}
