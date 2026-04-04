package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class StringsTestCases {

	
	@Test
	public void splitIsCorrect() {
		
		var splitter = Strings.Splitter.split("some.good.text").by(".");
		
		assertEquals(3, splitter.count().toInt());
        assertEquals("some", splitter.first().orElseThrow());
        assertEquals("text", splitter.last().orElseThrow());
        
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some.good.text").by("o");
		
		assertEquals(4, splitter.count().toInt());
        assertEquals("s", splitter.first().orElseThrow());
        assertEquals("d.text", splitter.last().orElseThrow());
        
		assertFalse(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("some text with no dots").by(".");
		
		assertEquals(1, splitter.count().toInt());
        assertEquals("some text with no dots", splitter.first().orElseThrow());
        assertEquals("some text with no dots", splitter.last().orElseThrow());
        
		assertFalse(splitter.isEmpty());
  
		splitter = Strings.Splitter.split(null).by(".");
		
		assertEquals(0, splitter.count().toInt());
		assertTrue(splitter.isEmpty());
		
		splitter = Strings.Splitter.split("abcd").byEachChar();
		
		assertEquals(4, splitter.count().toInt());
        assertEquals("a", splitter.first().orElseThrow());
        assertEquals("d", splitter.last().orElseThrow());
        
		splitter = Strings.Splitter.split("a b c d  ").by("");
        assertEquals("a", splitter.first().orElseThrow());
        assertEquals(" ", splitter.last().orElseThrow());
        
		assertEquals(9, splitter.count().toInt());
        
        splitter = Strings.Splitter.split("a b c d  ").by(" ");
        
        assertEquals(4, splitter.count().toInt());
        assertEquals("a", splitter.first().orElseThrow());
        assertEquals("d", splitter.last().orElseThrow());
        
        splitter = splitter.withoutFirst();
        
        assertEquals(3, splitter.count().toInt());
        assertEquals("b", splitter.first().orElseThrow());
        assertEquals("d", splitter.last().orElseThrow());
        
        splitter = splitter.withoutLast();
        
        assertEquals(2, splitter.count().toInt());
        assertEquals("b", splitter.first().orElseThrow());
        assertEquals("c", splitter.last().orElseThrow());
        
        splitter = splitter.withoutFirst();
        
        assertEquals(1, splitter.count().toInt());
        assertEquals("c", splitter.first().orElseThrow());
        assertEquals("c", splitter.last().orElseThrow());
        
        splitter = splitter.withoutLast();
        
        assertEquals(0, splitter.count().toInt());
        
	}
	
	@Test
	public void casingTransformationIsCorrect(){
		
		record TestCase(String a, Strings.Casing aCasing,  String b, Strings.Casing bCasing){}
		
		var list = List.of(
			new TestCase("SomeField", Strings.Casing.PASCAL, "some-field" , Strings.Casing.URL),
			new TestCase("AB", Strings.Casing.PASCAL, "a-b" , Strings.Casing.URL),
			new TestCase("someField", Strings.Casing.CAMEL, "SomeField" , Strings.Casing.PASCAL),
			new TestCase("Some Field", Strings.Casing.PLAIN, "SomeField" , Strings.Casing.PASCAL)
		);
		
		for (var testCase : list){
			assertEquals(testCase.b, Strings.transform(testCase.a, testCase.aCasing, testCase.bCasing), "Wrong transformation from " + testCase.aCasing + " to " + testCase.bCasing);
			assertEquals(testCase.a, Strings.transform(testCase.b, testCase.bCasing, testCase.aCasing), "Wrong transformation from " + testCase.bCasing + " to " + testCase.aCasing);
		}
	
	}
    
    @Test
    public void blankFilledDetectionIsCorrect(){
        
        var filled = List.of("a", " a ", "  a", "a  ");
        var blank = List.of("", " ", "  ", "    ");
        
        for (var text : filled){
            assertTrue(Strings.isFilled(text));
            assertTrue(Strings.isFilled(new StringBuilder(text)));
            
            assertFalse(Strings.isBlank(text));
            assertFalse(Strings.isBlank(new StringBuilder(text)));
        }
        
        assertTrue(Strings.areFilled(filled.get(0), filled.get(1)));
        assertTrue(Strings.areFilled(filled.get(0), filled.get(1), filled.toArray(new String[0])));
        assertFalse(Strings.areBlank(filled.get(0), filled.get(1)));
        assertFalse(Strings.areBlank(filled.get(0), filled.get(1), filled.toArray(new String[0])));
        
        for (var text : blank){
            assertTrue(Strings.isBlank(text));
            assertTrue(Strings.isBlank(new StringBuilder(text)));
            
            assertFalse(Strings.isFilled(text));
            assertFalse(Strings.isFilled(new StringBuilder(text)));
        }
        
        assertTrue(Strings.areBlank(blank.get(0), blank.get(1)));
        assertTrue(Strings.areBlank(blank.get(0), blank.get(1), blank.toArray(new String[0])));
        assertFalse(Strings.areFilled(blank.get(0), blank.get(1)));
        assertFalse(Strings.areFilled(blank.get(0), blank.get(1), blank.toArray(new String[0])));
        
        
    }
    
}
