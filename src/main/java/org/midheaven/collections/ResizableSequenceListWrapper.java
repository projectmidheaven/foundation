package org.midheaven.collections;

import org.midheaven.lang.Maybe;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;


class ResizableSequenceListWrapper<T>  extends EditableListSequence<T> implements ResizableSequence<T> {

	ResizableSequenceListWrapper(List<T> original){
		super(original);
	}
	
	public void replaceAll(UnaryOperator<T> operator) {
		original.replaceAll(operator);
	}

	public void sort(Comparator<? super T> c) {
		original.sort(c);
	}
	
	public void addFirst(T e) {
		original.addFirst(e);
	}

	public void addLast(T e) {
		original.addLast(e);
	}

	public ResizableSequence<T> reversed() {
		return new ResizableSequenceListWrapper<T>(original.reversed());
	}

	@Override
	public boolean addAllAt(int index, Iterable<? extends T> c) {
		if (c instanceof Collection collection) {
			return original.addAll(index, collection);
		} else {
			var list = new LinkedList<>();
			var iterator = c.iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
			return original.retainAll(list);
		}
	}

	@Override
	public Maybe<T> removeAt(int index) {
		return Maybe.of((T)original.remove(index));
	}

	@Override
	public void addAt(int index, T element) {
		original.add(index, element);
	}

	@Override
	public ResizableSequence<T> subSequence(int fromIndex, int toIndex) {
		return new ResizableSequenceListWrapper<T>(original.subList(fromIndex, toIndex));
	}
	
	@Override
	public boolean remove(Object other) {
		return original.remove(other);
	}
	
	@Override
	public boolean addAll(Iterable<? extends T> c) {
		return JavaCollectionsSupport.addAll(original, c);
	}
	
	@Override
	public boolean retainAll(Iterable<? extends T> c) {
		return JavaCollectionsSupport.retainAll(original, c);
	}
	
	@Override
	public boolean removeAll(Iterable<? extends T> c) {
		return JavaCollectionsSupport.removeAll(original, c);
	}
	
	@Override
	public void clear() {
		original.clear();
	}
	
	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return original.removeIf(filter);
	}
	
	
}