package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.math.Int;

final class EmptySplitter implements Strings.Splitter{

	private static final EmptySplitter ME = new EmptySplitter();
	
	public static @NotNull EmptySplitter instance() {
		return ME;
	}

	@Override
	public Int count() {
		return Int.ZERO;
	}

	@Override
	public String get(int index) {
		return null;
	}

	@Override
	public Sequence<String> sequence() {
		return Sequence.builder().empty();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

}
