package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.math.Int;

final class ArraySplitter implements Strings.Splitter {

    private String[] parts;

	ArraySplitter(String[] parts) {
		this.parts = parts;
	}

	@Override
	public Int count() {
		return Int.of(parts.length);
	}

	@Override
	public String get(int index) {
		return parts[index];
	}

	@Override
	public Sequence<String> sequence() {
		return Sequence.builder().of(parts);
	}


	@Override
	public boolean isEmpty() {
		return parts.length  == 0;
	}

}
