package org.midheaven.lang;

import org.midheaven.lang.Iterables.CombinerResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Represents Iterables.
 */
public class Iterables {

	public interface BiCombiner<A,B>{
		<R> CombinerResult<R> by(BiFunction<A,B,R> function);	
	}
	
	public interface CombinerResult<T> extends Iterable<T>{
		/**
		 * Performs size.
		 * @return the result of size
		 */
		int size();
		/**
		 * Checks whether is Empty.
		 * @return the result of isEmpty
		 */
		boolean isEmpty();
	
		/**
		 * Returns to List.
		 * @return the result of toList
		 */
		default List<T> toList() {
			if (isEmpty()) {
				return Collections.emptyList();
			}
			var result = new ArrayList<T>();
			var iterator = this.iterator();
			while(iterator.hasNext()) {
				result.add(iterator.next());
			}
			return result;
		}

		/**
		 * Returns to Set.
		 * @return the result of toSet
		 */
		default Set<T> toSet() {
			if (isEmpty()) {
				return Collections.emptySet();
			}
			var result = new HashSet<T>();
			var iterator = this.iterator();
			while(iterator.hasNext()) {
				result.add(iterator.next());
			}
			return result;
		}
	}
	
	/**
	 * Performs zip.
	 * @param a the a value
	 * @param b the b value
	 * @return the result of zip
	 */
	public static <A , B> BiCombiner<A,B> zip(Iterable<A> a, Iterable<B> b){
		if (a instanceof Collection<A> aa && b instanceof Collection<B> bb) {
			return new CollectionZipCollectionsCombiner<>(aa,bb);
		}
		return new IterableZipAssortementsCombiner<>(a,b);
	}

	/**
	 * Performs streamValuesOf.
	 * @param enumType the enumType value
	 * @return the result of streamValuesOf
	 */
	public static <T extends Enum<T>> Stream<T> streamValuesOf(Class<T> enumType){
		return Arrays.stream(enumType.getEnumConstants());
	}

	/**
	 * Performs stream.
	 * @param iterable the iterable value
	 * @return the result of stream
	 */
	public static <T> Stream<T> stream(Iterable<T> iterable){
		if (iterable== null){
			return Stream.empty();
		} else if (iterable instanceof Collection<T> collection){
			return collection.stream();
		} else {
			return stream(iterable.iterator());
		}
	}

	/**
	 * Performs stream.
	 * @param iterator the iterator value
	 * @return the result of stream
	 */
	public static <T> Stream<T> stream(Iterator<T> iterator){
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
				false
		);
	}
}


record CollectionZipCollectionsCombiner<A, B>(Collection<A> a, Collection<B> b) implements Iterables.BiCombiner<A,B> {

	@Override
	public <R> CombinerResult<R> by(BiFunction<A, B, R> function) {
		return new CombinerResult<>() {

			@Override
			public Iterator<R> iterator() {
				return new ZipIterator<>(a.iterator(),b.iterator(),function);
			}

			@Override
			public int size() {
				return Math.min(a.size(), b.size());
			}

			@Override
			public boolean isEmpty() {
				return a.isEmpty() || b.isEmpty();
			}
	
			public List<R> toList() {
				if (isEmpty()) {
					return Collections.emptyList();
				}
				var result = new ArrayList<R>(this.size());
				var iterator = this.iterator();
				while(iterator.hasNext()) {
					result.add(iterator.next());
				}
				return result;
			}

			public Set<R> toSet() {
				if (isEmpty()) {
					return Collections.emptySet();
				}
				var result = HashSet.<R>newHashSet(this.size());
				var iterator = this.iterator();
				while(iterator.hasNext()) {
					result.add(iterator.next());
				}
				return result;
			}
		};
	}

	
}

record IterableZipAssortementsCombiner<A, B>(Iterable<A> a, Iterable<B> b) implements Iterables.BiCombiner<A,B> {

	@Override
	public <R> CombinerResult<R> by(BiFunction<A, B, R> function) {
		return new CombinerResult<R>() {

			Integer size;
			@Override
			public Iterator<R> iterator() {
				return new ZipIterator<>(a.iterator(),b.iterator(),function);
			}

			@Override
			public int size() {
				if (this.size != null) {
					return size;
				}
			
				var ait = a.iterator();
				var bit = a.iterator();
				var count = 0;
				while(ait.hasNext() && bit.hasNext()) {
					count++;
					ait.next();
					bit.next();
				}
				return count;
			}

			@Override
			public boolean isEmpty() {
				return !a.iterator().hasNext() || !b.iterator().hasNext();
			}
		};
	}	
}

record ZipIterator<A,B, R>(Iterator<A> a, Iterator<B> b, BiFunction<A, B, R> function) implements Iterator<R>{

	@Override
	public boolean hasNext() {
		return a.hasNext() && b.hasNext();
	}

	@Override
	public R next() {
		return function.apply(a.next(), b.next());
	}
	
}
