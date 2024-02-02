package org.midheaven.collections;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface ResizableSequence<T> extends EditableSequence<T>  {

	default ResizableSequence<T> toSequence() {
		return this;
	}

	boolean add(T e);

	boolean remove(Object o);

	boolean addAll(Iterable<? extends T> c);

	boolean addAllAt(int index, Iterable<? extends T> c);

	boolean removeAll(Iterable<?> c);

	boolean retainAll(Iterable<?> c);

	void replaceAll(UnaryOperator<T> operator);

	void sort(Comparator<? super T> c);

	void clear();

	Optional<T> removeAt(int index);

	void addAt(int index, T element);

	boolean removeIf(Predicate<? super T> filter);


	int indexOf(Object o);

	int lastIndexOf(Object o);


	ResizableSequence<T> subSequence(int fromIndex, int toIndex);

	void addFirst(T e);

	void addLast(T e);

	Optional<T> first();

	Optional<T> last();

	ResizableSequence<T> reversed();
	
}
