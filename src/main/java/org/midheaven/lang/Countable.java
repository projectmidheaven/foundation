package org.midheaven.lang;

import org.midheaven.math.Int;

/**
 * Defines the contract for Countable.
 */
public interface Countable {

	/**
	 * Checks whether is Empty.
	 * @return the result of isEmpty
	 */
	@NotNullable Int count();
	/**
	 * Checks whether is Empty.
	 * @return the result of isEmpty
	 */
	boolean isEmpty();
}
