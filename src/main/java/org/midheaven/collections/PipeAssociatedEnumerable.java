package org.midheaven.collections;


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
        return this.enumerator().toIterator();
    }

}
