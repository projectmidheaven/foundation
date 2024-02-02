package org.midheaven.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssociationTestCases {

	@Test
	public void association() {
		var association = Association.builder().ofEntries(
				Association.Entry.entry("A", 1),
				Association.Entry.entry("B", 2),
				Association.Entry.entry("C", 3),
				Association.Entry.entry("D", 3)
		);

		assertEquals(4L, association.count());
		assertEquals(Optional.of(1), association.getValue("A"));
		assertEquals(Optional.of(2), association.getValue("B"));
		assertEquals(Optional.of(3), association.getValue("C"));
		
	}

	@Test
	public void resizableAssociation() {
		var association = Association.builder().resizable().ofEntries(
				Association.Entry.entry("A", 1),
				Association.Entry.entry("B", 2),
				Association.Entry.entry("C", 3)
		);

		association.putValue("D", 3);
		assertEquals(4L, association.count());
		assertEquals(Optional.of(1), association.getValue("A"));
		assertEquals(Optional.of(2), association.getValue("B"));
		assertEquals(Optional.of(3), association.getValue("C"));

	}

	@Test
	public void resizableAssociationCopy() {
		var original = Association.builder().resizable().ofEntries(
				Association.Entry.entry("A", 1),
				Association.Entry.entry("B", 2),
				Association.Entry.entry("C", 3)
		);


		original.putValue("D", 3);

		var association = Association.builder().resizable().from(original);

		assertEquals(4L, association.count());
		assertEquals(Optional.of(1), association.getValue("A"));
		assertEquals(Optional.of(2), association.getValue("B"));
		assertEquals(Optional.of(3), association.getValue("C"));

		var list = new ArrayList<>(4);
		var enumerator = association.values().enumerator();
		while(enumerator.tryNext(v -> list.add(v)));

		assertEquals(4L, list.size());

		list.clear();

		var iterator = association.values().enumerator().toIterator();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}

		assertEquals(4L, list.size());
	}

	@Test
	public void resizableAssociationAddAfter() {
		var association = Association.builder().resizable().empty();

		association.putValue("A", 1);
		association.putValue("B", 2);
		association.putValue("C", 3);
		association.putValue("D", 3);

		assertEquals(4L, association.count());
		assertEquals(Optional.of(1), association.getValue("A"));
		assertEquals(Optional.of(2), association.getValue("B"));
		assertEquals(Optional.of(3), association.getValue("C"));

	}
}
