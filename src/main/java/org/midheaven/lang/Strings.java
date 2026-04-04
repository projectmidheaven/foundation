package org.midheaven.lang;

import org.midheaven.collections.Sequence;
import org.midheaven.lang.Strings.Splitter;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Represents Strings.
 */
public class Strings {
	
    public interface Transform {
        
        /**
         * Performs create.
         * @return the result of create
         */
        static @NotNullable StringTransformBuilder create(){
            return new ChainStringTransformBuilder();
        }
        
        /**
         * Performs apply.
         * @param text the text value
         * @return the result of apply
         */
        String apply(CharSequence text);
    }
    
	public interface Splitter extends Countable {
		
		interface Condition {
			@NotNullable Splitter by(@NotNullable String delimiter);
			@NotNullable Splitter by(char delimiter);
			@NotNullable Splitter by(@NotNullable Pattern pattern);
			@NotNullable Splitter byEachChar();
		}
		
		/**
		 * Performs split.
		 * @param text the text value
		 * @return the result of split
		 */
		static @NotNullable Condition split(@NotNullable CharSequence text) {
			return Maybe.of(text)
					   .filter(it -> !it.isEmpty())
					   .<Condition>map(it -> new SplitterCondition(text.toString()))
					   .orElse(new EmptyCondition());
		}
		
		@NotNullable String get(int index);
		
		@NotNullable Sequence<String> sequence();
		
		@NotNullable Splitter map(@NotNullable Function<String, String> transform);
		
		@NotNullable Maybe<String> first();
		@NotNullable Maybe<String> last();
		
		@NotNullable  Splitter withoutFirst();
		@NotNullable Splitter withoutLast();
        
        /**
         * Performs join.
         * @param delimiter the delimiter value
         * @return the result of join
         */
        default @NotNullable String join(char delimiter){
            return collect(Collectors.joining(Character.toString(delimiter)));
        }
        
        /**
         * Performs join.
         * @param delimiter the delimiter value
         * @return the result of join
         */
        default @NotNullable String join(@NotNullable String delimiter){
            return collect(Collectors.joining(delimiter));
        }
        
        <A> @NotNullable String collect(@NotNullable Collector<CharSequence, A, String> collector);
	}
	
	public enum Casing {
		UNKNOWN(null),
		/**
		 * Any text separated by spaces
		 */
		PLAIN(" "),
		/**
		 * all in upper case letters: "Hello world" is  "HELLO_WORLD"
		 */
		UPPER("_"),
		/**
		 * Also known as snake case
		 * all in lower case letters: "Hello world" is  "hello_world"
		 */
		LOWER("_"),
		/**
		 * First letter of word is upper case, the rest is lowercase. New words have uppercase initial: "Hello world" is  "HelloWorld"
		 */
		PASCAL(""),
		/**
		 * First letter of word is lower case, the rest is lowercase. New words have uppercase initial: "Hello world" is  "helloWorld"
		 */
		CAMEL(""),
		/**
		 * also known as kebab case
		 * all in lower case letters: "Hello world" is  "hello-world"
		 */
		URL("-");
		
		private final String separation;
		
		Casing(String separation){
			this.separation = separation;
		}
		
		/**
		 * Performs splitWords.
		 * @param text the text value
		 * @return the result of splitWords
		 */
		public Splitter splitWords(CharSequence text){
			var str = text.toString();
			return switch (this){
                case UNKNOWN -> {
					if (str.contains(" ")){
						yield  PLAIN.splitWords(text);
					} else if (str.contains("-")){
						yield URL.splitWords(text);
					} else if (str.substring(0,1).toUpperCase().equals(str.substring(0,1))){
						if (str.substring(1,2).toUpperCase().equals(str.substring(1,2))){
							yield UPPER.splitWords(text);
						} else {
							yield PASCAL.splitWords(text);
						}
					} else if (str.contains("_")) {
						yield LOWER.splitWords(text);
					}
					yield CAMEL.splitWords(text);
				}
                case PASCAL, CAMEL -> {
					var words = new ListSplitter();
					var previous = 0;
					for (int i = 1; i < text.length(); i++){
						if (Character.isUpperCase(text.charAt(i))){
							var word = str.substring(previous, i);
							previous = i;
							words.add(word);
						}
					}
					words.add(str.substring(previous));
					yield words;
				}
				case PLAIN ->  Splitter.split(text.toString()).by(" ");
                case LOWER, UPPER, URL ->  Splitter.split(text.toString()).by(this.separation);
            };
		}
		
		/**
		 * Performs joinWords.
		 * @param splitter the splitter value
		 * @return the result of joinWords
		 */
		public String joinWords(Splitter splitter){
			return splitter.sequence().limit(1).map(it -> operateFirstWord(it))
					   .concat(splitter.sequence().skip(1).map(it -> operateOtherWords(it)))
					   .collect(Collectors.joining(this.separation));
		}
		
		/**
		 * Performs operateOtherWords.
		 * @param word the word value
		 * @return the result of operateOtherWords
		 */
		private CharSequence operateOtherWords(String word) {
			return switch (this){
                case UNKNOWN, PLAIN -> word;
                case UPPER -> word.toUpperCase();
                case LOWER, URL -> word.toLowerCase();
                case PASCAL, CAMEL -> word.substring(0,1).toUpperCase() + word.substring(1);
            };
		}
		
		/**
		 * Performs operateFirstWord.
		 * @param word the word value
		 * @return the result of operateFirstWord
		 */
		private CharSequence operateFirstWord(String word) {
			return switch (this){
				case UNKNOWN, PLAIN -> word;
				case UPPER -> word.toUpperCase();
				case LOWER, URL -> word.toLowerCase();
				case PASCAL -> word.substring(0,1).toUpperCase() + word.substring(1);
				case CAMEL -> word.substring(0,1).toLowerCase() + word.substring(1);
			};
		}
	}
	
	/**
	 * Checks whether is Blank.
	 * @param text the text value
	 * @return the result of isBlank
	 */
	public static boolean isBlank(@Nullable CharSequence text) {
        return switch (text){
            case null -> false;
            case String s -> isBlank(s);
            default -> isBlank(text.toString());
        };
	}

	/**
	 * Checks whether is Filled.
	 * @param text the text value
	 * @return the result of isFilled
	 */
	public static boolean isFilled(@Nullable CharSequence text) {
        return switch (text){
            case null -> false;
            case String s -> isFilled(s);
            default -> isFilled(text.toString());
        };
	}

	/**
	 * Checks whether is Blank.
	 * @param text the text value
	 * @return the result of isBlank
	 */
	public static boolean isBlank(@Nullable String text) {
		return text == null || text.isBlank();
	}
    
    /**
     * Performs areBlank.
     * @param a the a value
     * @param b the b value
     * @return the result of areBlank
     */
    public static boolean areBlank(@Nullable CharSequence a, @Nullable CharSequence b) {
        return isBlank(a) && isBlank(b);
    }
    
    /**
     * Performs areBlank.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of areBlank
     */
    public static boolean areBlank(@Nullable CharSequence a, @Nullable CharSequence b, @Nullable CharSequence ... others) {
        var blank = isBlank(a) && isBlank(b);
        
        for (var text : others){
            blank &= isBlank(text);
            if (!blank){
                return false;
            }
        }
        
        return blank;
    }
    
	/**
	 * Checks whether is Filled.
	 * @param text the text value
	 * @return the result of isFilled
	 */
	public static boolean isFilled(@Nullable String text) {
		return text != null && !text.isBlank();
	}
    
    /**
     * Performs areFilled.
     * @param a the a value
     * @param b the b value
     * @return the result of areFilled
     */
    public static boolean areFilled(@Nullable CharSequence a, @Nullable CharSequence b) {
        return isFilled(a) && isFilled(b);
    }
    
    /**
     * Performs areFilled.
     * @param a the a value
     * @param b the b value
     * @param others the others value
     * @return the result of areFilled
     */
    public static boolean areFilled(@Nullable CharSequence a, @Nullable CharSequence b,@Nullable CharSequence ... others) {
        var filled = isFilled(a) && isFilled(b);
        
        for (var text : others){
            filled &= isFilled(text);
            if (!filled){
                return false;
            }
        }
        
        return filled;
    }
    
	/**
	 * Performs filled.
	 * @param text the text value
	 * @return the result of filled
	 */
	public static @NotNullable Maybe<String> filled(@Nullable String text) {
		return Maybe.of(text).filter(it -> !it.isBlank());
	}
 
	/**
	 * Performs transform.
	 * @param text the text value
	 * @param original the original value
	 * @param target the target value
	 * @return the result of transform
	 */
	public static @Nullable String transform(@Nullable String text,@NotNullable Casing original, @NotNullable Casing target){
		if (text == null){
			return null;
		}
		return target.joinWords(original.splitWords(text));
	}
	
	/**
	 * Performs Strings.
	 * @return the result of Strings
	 */
	private Strings() {}
}

final class EmptyCondition implements Splitter.Condition {

	@Override
	public Splitter by(String regex) {
		return EmptySplitter.instance();
	}
	
	@Nullable
	@Override
	public Splitter by(char delimiter) {
		return EmptySplitter.instance();
	}
	
	@Nullable
	@Override
	public Splitter by(Pattern pattern) {
		return EmptySplitter.instance();
	}
	
	@Override
	public Splitter byEachChar() {
		return EmptySplitter.instance();
	}
	
}

record SplitterCondition(String text) implements Splitter.Condition {

	@Override
	public Splitter by(String delimiter) {
		if (delimiter.isEmpty()){
			return byEachChar();
		} else if (delimiter.length() == 1){
			return by(delimiter.charAt(0));
		}
		return ArraySplitter.fromArray(text.split(delimiter));
	}
	
	@Override
	public Splitter by(char delimiter) {
		return  ArraySplitter.fromArray(regexEscape(delimiter));
	}
	
	@Override
	public Splitter by(Pattern pattern) {
		return ArraySplitter.fromArray(pattern.split(text));
	}
	
	private String[] regexEscape(char regex) {
		return switch (regex){
			case '.' -> text.split("\\.");
			case '$' -> text.split("\\$");
			case '|' -> text.split("\\|");
			case '(' -> text.split("\\(");
			case ')' -> text.split("\\)");
			case '[' -> text.split("\\[");
			case ']' -> text.split("]");
			case '{' -> text.split("\\{");
			case '}' -> text.split("\\}");
			case '^' -> text.split("\\^");
			case '?' -> text.split("\\?");
			case '*' -> text.split("\\*");
			case '+' -> text.split("\\+");
			case '\\' -> text.split("\\\\");
			default -> text.split(Character.toString(regex));
		};
	}

	@Override
	public Splitter byEachChar() {
		return new CharSplitter(text.toCharArray());
	}
	
}

