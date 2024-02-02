package org.midheaven.collections;

public interface EnumerableExtension<T, E extends Enumerable<T>>{

    E extend(Enumerable<T> previous);

}
