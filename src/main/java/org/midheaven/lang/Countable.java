package org.midheaven.lang;

import org.midheaven.math.Int;

/**
 * Defines the contract for Countable.
 */
public interface Countable {

	/**
	 * @return  the count of elements
	 */
	@NotNullable Int count();
	/**
	 * Checks if there are no elements.
	 * This is the same as comparing the count with zero, but faster, since there is no need to count the elements
	 * @return {@code true} if there are no elements, {@code false} otherwise
	 */
	boolean isEmpty();
}
