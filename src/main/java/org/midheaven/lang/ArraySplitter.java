package org.midheaven.lang;

import org.midheaven.collections.Sequence;

/**
 * Represents Array Splitter.
 */
final class ArraySplitter extends AbstractIndexedSplitter {

    static Strings.Splitter fromArray(String[] array){
        if (array.length == 0){
            return EmptySplitter.instance();
        }
        return new ArraySplitter(array);
    }
    
    private final String[] parts;

	private ArraySplitter(String[] parts) {
		this.parts = parts;
	}
    
    @Override
    protected int length() {
        return parts.length;
    }
    
    @Override
	protected String secureGetAt(int index) {
		return parts[index];
	}
    
    @Override
	public Sequence<String> sequence() {
		return Sequence.builder().of(parts);
	}
 
}
