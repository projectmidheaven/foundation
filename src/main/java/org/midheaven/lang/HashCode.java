package org.midheaven.lang;

import java.util.Arrays;

/***
 * Simplifies hashCode calculations.
 * 
 * For primitives and arrays this class delegates to the hashCode static
 *  methods present in the respective wrappers of Arrays class
 */
public class HashCode {

	public interface Composition {
		
		public int hashCode();
		
		public Composition add(int value);
		
		default public Composition add(long value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(boolean value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(double value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(float value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(Object value) {
			return add(HashCode.of(value));
		}
		
		default public <T> Composition add(T[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(byte[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(short[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(char[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(int[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(long[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(float[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(double[] value) {
			return add(HashCode.of(value));
		}
		
		default public Composition add(boolean[] value) {
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
	
	public static int of(Object value) {
		return value == null ? 0: value.hashCode();
	}
	
	public static <T> int of(T[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(byte[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(short[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(char[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(int[] array) {
		return Arrays.hashCode(array);
	}

	public static int of(long[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(float[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(double[] array) {
		return Arrays.hashCode(array);
	}
	
	public static int of(boolean[] array) {
		return Arrays.hashCode(array);
	}
	
	/**
	 * Creates and returns a symmetric {@code HashCode.Composition} so the order of the values is not relevant
	 *
	 * @return a symmetric {@code HashCode.Composition}
	 */
	public static Composition symmetric() {
		return new SymmetricHashCodeComposition();
	}

	/**
	 * Creates and returns an asymmetric {@code HashCode.Composition} so the order of the values is relevant
	 *
	 * @return an asymmetric {@code HashCode.Composition}
	 */
	public static Composition asymmetric() {
		return new AsymmetricHashCodeComposition();
	}
	
	private HashCode() {}
	
}
