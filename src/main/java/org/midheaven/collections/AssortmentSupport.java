package org.midheaven.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

class AssortmentSupport {

    static <T, C extends Collection<T>, A extends Assortment<T>> A from(
            Iterable<T> origin,
            Function<Collection<T> , C> factory,
            Function<C, A> wrapper
    ) {
        if (origin instanceof Collection<T> collection) {
            return wrapper.apply(factory.apply(collection)); // copy
        } else {
            var set = factory.apply(Collections.emptySet());
            for (T t : origin) {
                set.add(t);
            }
            return wrapper.apply(set);
        }
    }
    
    static <T> boolean equals(Assortment<T> a, Assortment<T> b){
        if (a.isEmpty() && b.isEmpty()){
            return true;
        } else if (a.isEmpty() != b.isEmpty()){
            return false;
        } else if (!a.count().equals(b.count())){
            return false;
        }
        if (a instanceof DistinctAssortment<T> set){
            return set.containsAll(b);
        } else  if (b instanceof DistinctAssortment<T> set){
            return set.containsAll(a);
        }
        return a.zip(b, Objects::equals).allMatch(it -> it);
    }
}
