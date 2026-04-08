package org.midheaven.collections;

import org.midheaven.math.Int;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;


final class ResizableSetAssortment<T> extends AbstractDistinctAssortment<T> implements ResizableDistinctAssortment<T> {

	private final Set<T> original;

	ResizableSetAssortment(Set<T> original){
		this.original = original;
	}

	protected Collection<T> original() {
		return this.original;
	}

	@Override
	public boolean add(T element) {
		return original.add(element);
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
	
	@Override
	public boolean contains(T candidate) {
		return original.contains(candidate);
	}
	
	@Override
	public Set<T> toCollection() {
		return this.original;
	}
	
	@Override
	public Iterator<T> iterator() {
		return original.iterator();
	}
	
	@Override
	public Enumerator<T> enumerator() {
		return Enumerator.fromIterator(original.iterator(), count());
	}
	
	@Override
	public Int count() {
		return Int.of(original.size());
	}
	
	@Override
	public boolean isEmpty() {
		return original.isEmpty();
	}
	
	@Override
	public String toString (){
		return AssortmentSupport.toString(this, '{', '}');
	}
	
}
