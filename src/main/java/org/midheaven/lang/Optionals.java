package org.midheaven.lang;

import java.util.Optional;
import java.util.function.BiFunction;

public class Optionals {

	public interface Combiner<T>{
		Optional<T> by(BiFunction<T,T,T> function);	
		Optional<T> flatApply(BiFunction<T,T,Optional<T>> function);
	}
	
	public interface BiCombiner<A,B>{
		<R> Optional<R> by(BiFunction<A,B,R> function);	
		<R> Optional<R> flatApply(BiFunction<A,B,Optional<R>> function);
	}

	public static <T> Combiner<T> merge(Optional<T> a, Optional<T> b) {
		return new MergeCombiner<>(a,b);
	}
	
	public static <A,B> BiCombiner<A,B> zip(Optional<A> a, Optional<B> b) {
		return new ZipCombiner<>(a,b);
	}
}


record ZipCombiner<A,B>(Optional<A> a, Optional<B> b) implements Optionals.BiCombiner<A, B>{

	@Override
	public <R> Optional<R> by(BiFunction<A, B, R> function) {
		return a.flatMap(x -> b.map( y -> function.apply(x, y)));
	}

	@Override
	public <R> Optional<R> flatApply(BiFunction<A, B, Optional<R>> function) {
		return a.flatMap(x -> b.flatMap( y -> function.apply(x, y)));
	}
}

record MergeCombiner<T>(Optional<T> a, Optional<T> b) implements Optionals.Combiner<T>{

	@Override
	public Optional<T> by(BiFunction<T, T, T> function) {
		if (a.isPresent()) {
			if (b.isPresent()) {
				return a.flatMap(x -> b.map(y -> function.apply(x, y)));
			}
			return a;
		}
		return b;
	}

	@Override
	public Optional<T> flatApply(BiFunction<T, T, Optional<T>> function) {
		if (a.isPresent()) {
			if (b.isPresent()) {
				return a.flatMap(x -> b.flatMap(y -> function.apply(x, y)));
			}
			return a;
		}
		return b;
	}

	
}