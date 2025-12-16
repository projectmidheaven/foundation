package org.midheaven.lang;

import org.midheaven.collections.Sequence;

final class CharSplitter extends  AbstractIndexedSplitter {

	private final char[] text; // ech char is an item in the splitter

	CharSplitter(char[] text){
		this.text = text;
	}

	@Override
	public int length() {
		return text.length;
	}
 
	@Override
	protected String secureGetAt(int index) {
		return Character.toString(text[index]);
	}

	@Override
	public Sequence<String> sequence() {
		return Sequence.builder().of(text).map(String::valueOf);
	}
    

    
}
