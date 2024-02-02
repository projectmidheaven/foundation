package org.midheaven.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

abstract class AbstractCollectionWrapper<T> {

	protected abstract Collection<T> original();

	public void forEach(Consumer<? super T> action) {
		original().forEach(action);
	}
	
	public long count() {
		return original().size();
	}

	public Enumerator<T> enumerator() {
		return new IteratorEnumeratorAdapter<>(original().iterator(), original().size());
	}

	public Length length() {
		return Length.finite(count());
	}

	public int size() {
		return original().size();
	}

	public boolean isEmpty() {
		return original().isEmpty();
	}

	public boolean contains(Object o) {
		return original().contains(o);
	}

	public Iterator<T> iterator() {
		return original().iterator();
	}

	public boolean add(T e) {
		return original().add(e);
	}

	public boolean remove(Object o) {
		return original().remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return original().containsAll(c);
	}

	public boolean addAll(Iterable<? extends T> c) {
		if (c instanceof Collection collection) {
			return original().addAll(collection);
		} else {
			var original = original();
			var iterator = c.iterator();
			boolean changed = false; 
			while(iterator.hasNext()) {
				changed = changed | original.add(iterator.next());
			}
			return changed;
		}
	
	}
	
	public boolean addAll(Collection<? extends T> c) {
		return original().addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return original().retainAll(c);
	}
	
	public boolean retainAll(Iterable<?> c) {
		if (c instanceof Collection collection) {
			return original().retainAll(collection);
		}
		
		var set = new HashSet<>();
		var iterator = c.iterator();
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return original().retainAll(set);
	
	}

	public boolean removeAll(Collection<?> c) {
		return original().removeAll(c);
	}

	public void clear() {
		original().clear();
	}

	public boolean equals(Object o) {
		return original().equals(o);
	}

	public int hashCode() {
		return original().hashCode();
	}

	public boolean removeIf(Predicate<? super T> filter) {
		return original().removeIf(filter);
	}
	
	public boolean removeAll(Iterable<?> c) {
		if (c instanceof Collection<?> collection) {
			return original().removeAll(collection);
		} else {
			var original = original();
			var iterator = c.iterator();
			var size = original.size();
			while(iterator.hasNext()) {
				original.remove(iterator.next());
			}
			return size != original.size();
		}
	}
}
