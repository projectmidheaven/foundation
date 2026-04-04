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
	public Set<T> toCollection() {
		return this.original;
	}
	
	@Override
	public int hashCode (){
		return this.count().hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		return other instanceof DistinctAssortment that
				   && AssortmentSupport.equals(this, that);
	}
	
	@Override
	public String toString (){
		return AssortmentSupport.toString(this, '{', '}');
	}

}
