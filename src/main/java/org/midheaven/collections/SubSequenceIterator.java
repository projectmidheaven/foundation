package org.midheaven.collections;

import java.util.ListIterator;

class SubSequenceIterator<T> implements ListIterator<T> {

	private final int min;
	private final int max;
	private final ListIterator<T> iterator;
	
	private int index;
	
	public SubSequenceIterator(Sequence<T> original, int fromIndex, int toIndex) {
		this.index = -1;
		this.iterator = fromIndex <= toIndex ? original.iterator(): new ReversedListIterator<>(original.reverseIterator());
	
		this.min = Math.min(fromIndex, toIndex);
		this.max = Math.max(fromIndex, toIndex);

		while(index < min - 1 && iterator.hasNext()) {
			index++;
			iterator.next();
		}
	}
	
	@Override
	public boolean hasNext() {
		return index < max && iterator.hasNext();
	}

	@Override
	public T next() {
		index++;
		return iterator.next();
	}

	@Override
	public boolean hasPrevious() {
		return index > min && iterator.hasPrevious();
	}

	@Override
	public T previous() {
		index--;
		return iterator.previous();
	}

	@Override
	public int nextIndex() {
		return index + 1;
	}

	@Override
	public int previousIndex() {
		return index - 1;
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	@Override
	public void set(T e) {
		iterator.set(e);
	}

	@Override
	public void add(T e) {
		iterator.add(e);
	}

}
