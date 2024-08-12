package org.midheaven.lang;


import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaybeTestCases {

	@Test
	public void zipIsCorrect() {
		
		BiFunction<Integer, String, Integer> zipper = (x, y) -> x + y.length();
		var a = Maybe.of(2);
		var b = Maybe.of("Hi!");

		assertEquals(Maybe.some(5), a.zip(b, zipper));
		assertEquals(Maybe.none(), a.zip(Maybe.none(), zipper));
		assertEquals(Maybe.none(), Maybe.<Integer>none().zip(b, zipper));

	}
	
	@Test
	public void mergeIsCorrect() {
		
		BiFunction<Integer, Integer, Integer> merger = (x, y) -> x + y;
		var a = Maybe.of(2);
		var b = Maybe.of(4);

		assertEquals(Maybe.some(6), a.merge(b, merger));
		assertEquals(Maybe.some(2),a.merge(Maybe.none(), merger));
		assertEquals(Maybe.some(4), Maybe.<Integer>none().merge(b, merger));
	}
}
