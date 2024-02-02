package org.midheaven.lang;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

public class OptionalTestCases {

	@Test
	public void optionalsZipIsCorrect() {
		
		BiFunction<Integer, String, Integer> zipper = (x, y) -> x + y.length();
		var a = Optional.of(2);
		var b = Optional.of("Hi!");
		
		assertEquals(Optional.of(5), Optionals.zip(a,b).by(zipper));

		assertEquals(Optional.empty(), Optionals.zip(a, Optional.<String>empty()).by(zipper));
		assertEquals(Optional.empty(), Optionals.zip(Optional.<Integer>empty(),b).by(zipper));
	}
	
	@Test
	public void optionalsMergeIsCorrect() {
		
		BiFunction<Integer, Integer, Integer> merger = (x, y) -> x + y;
		var a = Optional.of(2);
		var b = Optional.of(4);
		
		assertEquals(Optional.of(6), Optionals.merge(a,b).by(merger));

		assertEquals(a, Optionals.merge(a, Optional.empty()).by(merger));
		assertEquals(b, Optionals.merge(Optional.<Integer>empty(), b).by(merger));
	}
}
