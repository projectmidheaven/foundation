package org.midheaven.lang;

import java.util.stream.Stream;

final class ArraySplitter implements Strings.Splitter {

    private String[] parts;

	ArraySplitter(String[] parts) {
		this.parts = parts;
	}

	@Override
	public int size() {
		return parts.length;
	}

	@Override
	public String get(int index) {
		return parts[index];
	}

	@Override
	public Stream<String> stream() {
		return Stream.of(parts);
	}

	@Override
	public boolean isEmpty() {
		return parts.length  == 0;
	}

}
