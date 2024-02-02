package org.midheaven.lang;

import java.util.stream.Stream;

final class EmptySplitter implements Strings.Splitter{

	private static final EmptySplitter ME = new EmptySplitter();
	
	public static EmptySplitter instance() {
		return ME;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String get(int index) {
		return null;
	}

	@Override
	public Stream<String> stream() {
		return Stream.empty();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

}
