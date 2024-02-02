package org.midheaven.collections;

import java.util.Set;

public interface DistinctAssortment<T> extends Assortment<T> {

    static DistinctAssortmentBuilder builder(){
        return new DistinctAssortmentBuilder();
    }

    Set<T> asCollection();
}
