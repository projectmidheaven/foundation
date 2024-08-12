package org.midheaven.collections;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

final class ResizableSetWrapper<T> extends AbstractCollectionWrapper<T> implements ResizableDistinctAssortment<T> {

	private final Set<T> original;

	ResizableSetWrapper(Set<T> original){
		this.original = original;
	}

	protected Collection<T> original() {
		return this.original;
	}

	@Override
	public boolean add(T e) {
		return original.add(e);
	}

	@Override
	public boolean addAll(Iterable<? extends T> c) {
		boolean changed = false;
		for (var item : c){
			changed = changed | this.original.add(item);
		}
		return changed;
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return original.removeIf(filter);
	}

	@Override
	public boolean containsAll(Iterable<? extends T> all) {
		for (var item : all){
			if (!this.original.contains(item)){
				return false;
			}
		}
		return true;
	}

	@Override
	public Set<T> asCollection() {
		return this.original;
	}

}
