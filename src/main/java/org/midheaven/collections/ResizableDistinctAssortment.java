package org.midheaven.collections;

import java.util.Set;
import java.util.function.Predicate;

public interface ResizableDistinctAssortment<T> extends DistinctAssortment<T> {

	boolean add(T e);

	boolean remove(Object o);

	boolean addAll(Iterable<? extends T> c);

	boolean removeAll(Iterable<?> c);

	boolean retainAll(Iterable<?> c);

	void clear();

	boolean removeIf(Predicate<? super T> filter);

	Set<T> asCollection();
	
}
