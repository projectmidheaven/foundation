package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.math.Int;

class CharSplitter implements Strings.Splitter {

	private char[] text;

	CharSplitter(char[] text){
		this.text = text;
	}

	@Override
	public Int count() {
		return Int.of(text.length);
	}

	@Override
	public boolean isEmpty() {
		return text.length == 0;
	}

	@Override
	public String get(int index) {
		return String.valueOf(text[index]);
	}

	@Override
	public Sequence<String> sequence() {
		return Sequence.builder().of(text).map(String::valueOf);
	}

}
