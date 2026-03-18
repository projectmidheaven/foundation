package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.math.Int;

import java.util.function.Function;
import java.util.stream.Collector;

final class EmptySplitter implements Strings.Splitter{

	private static final EmptySplitter ME = new EmptySplitter();
	
	public static @NotNullable EmptySplitter instance() {
		return ME;
	}

	@Override
	public Int count() {
		return Int.ZERO;
	}

	@Override
	public String get(int index) {
		throw new IndexOutOfBoundsException();
	}
    
    @Override
	public Sequence<String> sequence() {
		return Sequence.builder().empty();
	}
    
    @Override
    public Strings.Splitter map(Function<String, String> transform) {
        return ME;
    }
    
    @Override
    public Maybe<String> first() {
        return Maybe.none();
    }
    
    @Override
    public Maybe<String> last() {
        return Maybe.none();
    }
    
    @Override
    public Strings.Splitter withoutFirst() {
        return ME;
    }
    
    @Override
    public Strings.Splitter withoutLast() {
        return ME;
    }
    
    @Override
    public <A> String collect(Collector<CharSequence, A, String> collector) {
        return "";
    }
    
    @Override
	public boolean isEmpty() {
		return true;
	}

}
