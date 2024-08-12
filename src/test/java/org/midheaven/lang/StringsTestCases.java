package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringsTestCases {

	
	@Test
	public void SplitIsCorrect() {
		
		var splitter = Strings.Splitter.split("some.good.text").byDots();
		
		assertEquals(3, splitter.count().toInt());
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some.good.text").by("o");
		
		assertEquals(4, splitter.count().toInt());
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some text with no dots").byDots();
		
		assertEquals(1, splitter.count().toInt());
		assertFalse(splitter.isEmpty());
		
		
		splitter = Strings.Splitter.split(null).byDots();
		
		assertEquals(0, splitter.count().toInt());
		assertTrue(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("abcd").byEachChar();
		
		assertEquals(4, splitter.count().toInt());
		
		splitter = Strings.Splitter.split("a b c d  ").bySpaces();
		
		assertEquals(4, splitter.count().toInt());
		
	

	}
}
