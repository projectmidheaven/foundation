package org.midheaven.lang;

import java.util.stream.Stream;

class CharSplitter implements Strings.Splitter {

	private char[] text;

	CharSplitter(char[] text){
		this.text = text;
	}



	@Override
	public int size() {
		return text.length;
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
	public Stream<String> stream() {
		return Stream.of(text).map(it -> String.valueOf(it));
	}

}
