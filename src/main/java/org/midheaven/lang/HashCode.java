package org.midheaven.lang;

import java.util.Arrays;

/***
 * Simplifies hashCode calculations.
 * For primitives and arrays this class delegates to the hashCode static
 *  methods present in the respective wrappers of Arrays class
 */
public class HashCode {

	public interface Composition {
		
		int hashCode();

		@NotNullable
        Composition add(int value);
		
		default @NotNullable Composition add(long value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(boolean value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(double value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(float value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(Object value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable <T> Composition add(T[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(byte[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(short[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(char[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(int[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(long[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(float[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(double[] value) {
			return add(HashCode.of(value));
		}
		
		default @NotNullable Composition add(boolean[] value) {
			return add(HashCode.of(value));
		}
		
	}
	
	public static int of(int value) {
		return value;
	}
	
	public static int of(long value) {
		return Long.hashCode(value);
	}
	
	public static int of(double value) {
		return Double.hashCode(value);
	}
	
	public static int of(float value) {
		return Float.hashCode(value);
	}
	
	public static int of(boolean value) {
		return Boolean.hashCode(value);
	}
	
	public static int of(@Nullable Object value) {
		return value == null ? 0: value.hashCode();
	}
	
	public static <T> int of(@NotNullable T[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable byte[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable short[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable char[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable int[] array) {
		return Arrays.hashCode(array);
	}

	public static int of(@NotNullable long[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable float[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable double[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(@NotNullable boolean[] array) {
		return Arrays.hashCode(array);
	}
	
	/**
	 * Creates and returns a symmetric {@code HashCode.Composition} so the order of the values is not relevant
	 *
	 * @return a symmetric {@code HashCode.Composition}
	 */
	public static @NotNullable Composition symmetric() {
		return new SymmetricHashCodeComposition();
	}

	/**
	 * Creates and returns an asymmetric {@code HashCode.Composition} so the order of the values is relevant
	 *
	 * @return an asymmetric {@code HashCode.Composition}
	 */
	public static @NotNullable Composition asymmetric() {
		return new AsymmetricHashCodeComposition();
	}
	
	private HashCode() {}
	
}
