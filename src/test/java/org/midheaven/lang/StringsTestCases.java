package org.midheaven.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringsTestCases {

	
	@Test
	public void SplitIsCorrect() {
		
		var splitter = Strings.Splitter.split("some.good.text").byDots();
		
		assertEquals(3, splitter.size());
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some.good.text").by("o");
		
		assertEquals(4, splitter.size());
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some text with no dots").byDots();
		
		assertEquals(1, splitter.size());
		assertFalse(splitter.isEmpty());
		
		
		splitter = Strings.Splitter.split(null).byDots();
		
		assertEquals(0, splitter.size());
		assertTrue(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("abcd").byEachChar();
		
		assertEquals(4, splitter.size());
		
		splitter = Strings.Splitter.split("a b c d  ").bySpaces();
		
		assertEquals(4, splitter.size());
		
	

	}
}
