package org.midheaven.lang;

import org.midheaven.lang.HashCode.Composition;

final class AsymmetricHashCodeComposition implements Composition {
	
	private static final int BASE_PRIME = 7;
	private static final int PRODUCT_PRIME = 31;
	
	int hashCode = BASE_PRIME;
	
	@Override
	public Composition add(int value) {
		if (value != 0 ) {
			hashCode = hashCode * PRODUCT_PRIME + value;
		}
		return this;
	}
	
	@Override 
	public int hashCode() {
		return hashCode;
	}

}
