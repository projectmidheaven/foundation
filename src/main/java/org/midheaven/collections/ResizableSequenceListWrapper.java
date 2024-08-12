package org.midheaven.collections;

import org.midheaven.lang.Maybe;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

class ResizableSequenceListWrapper<T>  extends EditableSequenceListWrapper<T> implements ResizableSequence<T> {

	ResizableSequenceListWrapper(List<T> original){
		super(original);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return original.addAll(index, c);
	}

	public void replaceAll(UnaryOperator<T> operator) {
		original.replaceAll(operator);
	}

	public void sort(Comparator<? super T> c) {
		original.sort(c);
	}

	public void add(int index, T element) {
		original.add(index, element);
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
			return original().retainAll(list);
		}
	}

	@Override
	public Maybe<T> removeAt(int index) {
		return Maybe.of(original.remove(index));
	}

	@Override
	public void addAt(int index, T element) {
		original.add(index, element);
	}

	@Override
	public ResizableSequence<T> subSequence(int fromIndex, int toIndex) {
		return new ResizableSequenceListWrapper<T>(original.subList(fromIndex, toIndex));
	}

	
}
