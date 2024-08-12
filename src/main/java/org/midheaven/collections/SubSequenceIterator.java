package org.midheaven.collections;

import org.midheaven.lang.Ordered;
import org.midheaven.math.Int;
import org.midheaven.math.IntAccumulator;

import java.util.Iterator;

class SubSequenceIterator<T> implements Iterator<T> {

	private final Int min;
	private final Int max;
	private final Iterator<T> iterator;

	private final IntAccumulator index;

	public SubSequenceIterator(Sequence<T> original, Int fromIndex, Int toIndex) {
		this.index = new IntAccumulator(Int.MINUS_ONE);
		this.iterator = fromIndex.isLessThanOrEqualTo(toIndex) ? original.iterator(): original.reverseIterator();
	
		this.min = Ordered.min(fromIndex, toIndex);
		this.max = Ordered.max(fromIndex, toIndex);

		while(index.isLessThan(min.minus(1)) && iterator.hasNext()) {
			index.increment();
			iterator.next();
		}
	}
	
	@Override
	public boolean hasNext() {
		return index.isLessThan(max) && iterator.hasNext();
	}

	@Override
	public T next() {
		index.increment();
		return iterator.next();
	}

}
