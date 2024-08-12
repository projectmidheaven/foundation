package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Strings.Splitter;

public class Strings {

	public static boolean isBlank(String text) {
		return text == null || text.isBlank();
	}

	public static boolean isFilled(String text) {
		return text != null && !text.isBlank();
	}

    public interface Splitter extends Countable{
		
		interface Condition {
			@NotNull Splitter by(String regex);
			@NotNull Splitter bySpaces();
			@NotNull Splitter byDots();
			@NotNull Splitter byEachChar();
		}
		
		static @NotNull Condition split(String text) {
			return Maybe.of(text)
					.filter(it -> !it.isEmpty())
					.<Condition>map(it -> new SplitterCondition(text))
					.orElse(new EmptyCondition());
		}

		String get(int index);

		@NotNull Sequence<String> sequence();
	
	}
	
	public static @NotNull Maybe<String> filled(String text) {
		return Maybe.of(text).filter(it -> !it.isBlank());
	}

	private Strings() {}
}

final class EmptyCondition implements Strings.Splitter.Condition {

	@Override
	public Splitter by(String regex) {
		return EmptySplitter.instance();
	}

	@Override
	public Splitter bySpaces() {
		return EmptySplitter.instance();
	}

	@Override
	public Splitter byDots() {
		return EmptySplitter.instance();
	}

	@Override
	public Splitter byEachChar() {
		return EmptySplitter.instance();
	}
	
}
final record SplitterCondition(String text) implements Strings.Splitter.Condition {

	@Override
	public Splitter by(String regex) {
		return new ArraySplitter(text.split(regex));
	}

	@Override
	public Splitter bySpaces() {
		return by("\\s");
	}

	@Override
	public Splitter byDots() {
		return by("\\.");
	}

	@Override
	public Splitter byEachChar() {
		return new CharSplitter(text.toCharArray());
	}
	
}