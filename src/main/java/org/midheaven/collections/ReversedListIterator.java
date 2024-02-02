package org.midheaven.collections;

import java.util.ListIterator;

class ReversedListIterator<T> implements ListIterator<T> {
	
	private final ListIterator<T> original;

	ReversedListIterator(ListIterator<T> original){
		this.original = original;
	}

	@Override
	public boolean hasNext() {
		return original.hasPrevious();
	}

	@Override
	public T next() {
		return original.previous();
	}

	@Override
	public boolean hasPrevious() {
		return original.hasNext();
	}

	@Override
	public T previous() {
		return original.next();
	}

	@Override
	public int nextIndex() {
		return original.previousIndex();
	}

	@Override
	public int previousIndex() {
		return original.nextIndex();
	}

	@Override
	public void remove() {
		original.remove();
	}

	@Override
	public void set(T e) {
		original.set(e);
	}

	@Override
	public void add(T e) {
		original.add(e);
	}

}
