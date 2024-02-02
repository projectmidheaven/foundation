package org.midheaven.collections;

import org.junit.jupiter.api.Test;
import org.midheaven.collections.Assortment;
import org.midheaven.collections.EditableSequence;
import org.midheaven.collections.Sequence;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssortmentTestCases {

	@Test
	public void reversed_of_reverse_is_itself() {
		Sequence<Integer> sequence = Sequence.builder().empty();

		assertSame(sequence , sequence.reversed().reversed());

	}

	@Test
	public void build_empty_sequence() {
		Sequence<Integer> sequence = Sequence.builder().empty();
		
		assertEquals(0L, sequence.count());
		assertTrue(sequence.getAt(0).isEmpty());
		
		assertTrue(sequence.isEmpty());
		assertFalse(sequence.contains(2));
		assertFalse(sequence.contains(4));
	}
	
	@Test
	public void build_immutable_sequence() {
		Sequence<Integer> sequence = Sequence.builder().of(1,2,3);
		
		assertEquals(3L, sequence.count());
		assertEquals(2, sequence.getAt(1).orElse(-1));
		
		assertFalse(sequence.isEmpty());
		assertTrue(sequence.contains(2));
		assertFalse(sequence.contains(4));
		
		assertFalse(sequence instanceof EditableSequence<Integer>);
		assertFalse(sequence instanceof ResizableSequence<Integer>);
	}
	
	@Test
	public void build_immutable_subsequence() {
		Sequence<Integer> sequence = Sequence.builder().of(1,2,3,4,5);
		
		assertEquals(5L, sequence.count());
	
		var subSequence = sequence.subSequence(1, 3);
		
		assertEquals(3, subSequence.count());
		assertEquals(2, subSequence.first().orElse(-1));
		assertEquals(4, subSequence.last().orElse(-1));
		assertEquals(2, subSequence.getAt(0).orElse(-1));
		assertEquals(3, subSequence.getAt(1).orElse(-1));
		assertEquals(4, subSequence.getAt(2).orElse(-1));
		assertTrue(subSequence.getAt(3).isEmpty());
		
		var iterator = subSequence.iterator();
		
		assertNotNull(iterator);
		var subList = toList(iterator);

		assertEquals(3, subList.size());
		assertEquals(2, subList.getFirst());
		assertEquals(4, subList.getLast());
		assertEquals(2, subList.get(0));
		assertEquals(3, subList.get(1));
		assertEquals(4, subList.get(2));
		
		var reverseSubSequence = subSequence.reversed();
		
		var reverseIterator = reverseSubSequence.iterator();
		assertNotNull(reverseIterator);

		subList = toList(reverseIterator);

		assertEquals(3, reverseSubSequence.count());
		assertEquals(4, reverseSubSequence.first().orElse(-1));
		assertEquals(2, reverseSubSequence.last().orElse(-1));
		assertEquals(4, reverseSubSequence.getAt(0).orElse(-1));
		assertEquals(3, reverseSubSequence.getAt(1).orElse(-1));
		assertEquals(2, reverseSubSequence.getAt(2).orElse(-1));
		assertTrue(subSequence.getAt(3).isEmpty());
		
	}

	private <T> List<T> toList(Iterator<T> iterator){
		var list = new LinkedList<T>();

		while(iterator.hasNext()) {
			list.add(iterator.next());
		}

		return list;
	}
	
	@Test
	public void build_editable_sequence() {
		EditableSequence<Integer> sequence = Sequence.builder().editable().of(1,2,3);
		
		assertEquals(3L, sequence.count());
		assertEquals(2, sequence.getAt(1).orElse(-1));
		
		assertFalse(sequence.isEmpty());
		assertTrue(sequence.contains(2));
		assertFalse(sequence.contains(4));
		
		assertFalse(sequence instanceof ResizableSequence<Integer>);

		var reverseSequence = sequence.reversed();

		var reverseIterator = reverseSequence.iterator();
		assertNotNull(reverseIterator);

		assertEquals(sequence.count(), reverseSequence.count());
		assertEquals(3, reverseSequence.first().orElse(-1));
		assertEquals(1, reverseSequence.last().orElse(-1));
		assertEquals(3, reverseSequence.getAt(0).orElse(-1));
		assertEquals(2, reverseSequence.getAt(1).orElse(-1));
		assertEquals(1, reverseSequence.getAt(2).orElse(-1));


		sequence.setAt(1, 4); // change 2 to 4

		assertFalse(sequence.contains(2));
		assertTrue(sequence.contains(4));

		reverseSequence.setAt(0 ,5);

		assertTrue(sequence.contains(5));
		assertEquals(5, sequence.last().orElse(-1));

		assertSame(sequence , reverseSequence.reversed());
	}

	@Test
	public void build_editable_zero_size_empty_sequence() {
		EditableSequence<Integer> sequence = Sequence.builder().editable().empty();

		assertEquals(0, sequence.count());
		assertFalse(sequence.getAt(1).isPresent());

		assertTrue(sequence.isEmpty());
		assertFalse(sequence.contains(2));
		assertFalse(sequence.contains(4));

		assertFalse(sequence instanceof ResizableSequence<Integer>);


		assertThrows(IndexOutOfBoundsException.class , () -> sequence.setAt(1, 4)); // change 2 to 4

	}

	@Test
	public void build_editable_non_zero_size_empty_sequence() {
		EditableSequence<Integer> sequence = Sequence.builder().withSize(3).editable().empty();

		assertEquals(3, sequence.count());
		assertFalse(sequence.getAt(1).isPresent());

		assertFalse(sequence.isEmpty());
		assertFalse(sequence.contains(2));
		assertFalse(sequence.contains(4));

		assertFalse(sequence instanceof ResizableSequence<Integer>);

	 	sequence.setAt(1, 4); // set position 1 to 4

		assertTrue(sequence.contains(4));
	}
	
	@Test
	public void build_resizable_sequence() {
		ResizableSequence<Integer> sequence = Sequence.builder().resizable().of(1,2,3);
		
		assertEquals(3L, sequence.count());
		assertEquals(2, sequence.getAt(1).orElse(-1));
		
		assertFalse(sequence.isEmpty());
		assertTrue(sequence.contains(2));
		assertFalse(sequence.contains(4));
		
		sequence.setAt(1, 4); // change 2 to 4
		
		assertFalse(sequence.contains(2));
		assertTrue(sequence.contains(4));
		
		sequence.add(5);
		
		assertEquals(4L, sequence.count());
		
	}
}
