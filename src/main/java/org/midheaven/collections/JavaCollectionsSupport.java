package org.midheaven.collections;

import java.util.Collection;
import java.util.HashSet;

class JavaCollectionsSupport {
    
    static <T> boolean addAll(Collection<T> original, Iterable<? extends T> c) {
        if (c instanceof Collection<? extends T> collection) {
            return original.addAll(collection);
        }
        
        var iterator = c.iterator();
        boolean changed = false;
        while (iterator.hasNext()) {
            changed = changed | original.add(iterator.next());
        }
        return changed;
        
    }
    
    public static <T> boolean retainAll(Collection<T> original, Iterable<? extends T> c) {
        if (c instanceof Collection<? extends T> collection) {
            return original.retainAll(collection);
        }
        
        var set = new HashSet<>();
        var iterator = c.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next());
        }
        return original.retainAll(set);
    }
    
    public static <T> boolean removeAll(Collection<T> original, Iterable<? extends T> c) {
        if (c instanceof Collection<? extends T> collection) {
            return original.removeAll(collection);
        }
        
        var iterator = c.iterator();
        var size = original.size();
        while(iterator.hasNext()) {
            original.remove(iterator.next());
        }
        return size != original.size();
        
    }
}
