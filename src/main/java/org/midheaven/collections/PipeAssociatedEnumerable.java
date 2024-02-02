package org.midheaven.collections;


import java.util.Collections;
import java.util.Iterator;

class PipeAssociatedEnumerable<K,V> implements AssociatedEnumerable<K,V> {

    private final Enumerable<Association.Entry<K, V>> previous;

    protected PipeAssociatedEnumerable(Enumerable<Association.Entry<K, V>> previous) {
         this.previous = previous;
    }


    @Override
    public Enumerator<Association.Entry<K, V>> enumerator() {
        return previous.enumerator();
    }


    @Override
    public Iterator<Association.Entry<K, V>> iterator() {
        var enumerator = this.enumerator();

        if (enumerator.length() instanceof Length.Finite finite && finite.count() == 0){
            return Collections.emptyIterator();
        }

        return new Iterator<>() {
            final Object[] current = new Object[1];
            @Override
            public boolean hasNext() {
                return enumerator.tryNext(it -> current[0] = it);
            }

            @Override
            public Association.Entry<K, V> next() {
                return (Association.Entry<K, V>)current[0];
            }
        };
    }

}
