package org.midheaven.lang;

import java.util.Optional;
import java.util.stream.Stream;

import org.midheaven.lang.Strings.Splitter;

public class Strings {

	public static boolean isBlank(String text) {
		return text == null || text.isBlank();
	}

	public static boolean isFilled(String text) {
		return text != null && !text.isBlank();
	}

    public interface Splitter extends Countable{
		
		public interface Condition {
			Splitter by(String regex);
			Splitter bySpaces();
			Splitter byDots();
			Splitter byEachChar();
		}
		
		public static Condition split(String text) {
			return Optional.ofNullable(text)
					.filter(it -> !it.isEmpty())
					.<Condition>map(it -> new SplitterCondition(text))
					.orElse(new EmptyCondition());
		}

		@Override
		default long count() {
			return size();
		}

		String get(int index);
		
		Stream<String> stream();
	
	}
	
	public static Optional<String> nonEmpty(String text) {
		return Optional.ofNullable(text).filter(it -> !it.isBlank());
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