package org.midheaven.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashCodeTestCases {

	
	@Test
	public void hashOfIntIsItself() {
		
		assertEquals(13, HashCode.of(13));
	}
	
	@Test
	public void hashOfLongIsCorrect() {
		
		assertEquals(Long.hashCode(13), HashCode.of(13L));
	}
	
	@Test
	public void hashOfDoubleIsCorrect() {
		
		assertEquals(Double.hashCode(13D), HashCode.of(13D));
	}
	
	@Test
	public void hashOfObjectIsCorrect() {
		var text = "Some text";
		assertEquals(text.hashCode(), HashCode.of(text));
	}
	
	@Test
	public void hashSymmetricIsSymmetric() {
		var a = 21;
		var b = 42;
		assertEquals(
			HashCode.symmetric().add(a).add(b).hashCode(), 
			HashCode.symmetric().add(b).add(a).hashCode()
		);
	}
	
	@Test
	public void hashAsymmetricIsNotSymmetric() {
		var a = 21;
		var b = 42;
		assertNotEquals(
			HashCode.asymmetric().add(a).add(b).hashCode(), 
			HashCode.asymmetric().add(b).add(a).hashCode()
		);
	}
}
