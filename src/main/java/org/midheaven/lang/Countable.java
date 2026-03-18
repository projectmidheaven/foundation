package org.midheaven.lang;

import org.midheaven.math.Int;

public interface Countable {

	@NotNullable Int count();
	boolean isEmpty();
}
