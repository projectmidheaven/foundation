package org.midheaven.lang;

import org.midheaven.lang.HashCode.Composition;

final class SymmetricHashCodeComposition implements Composition {

	int hashCode = 0;
	
	@Override
	public Composition add(int value) {
		this.hashCode = this.hashCode ^ value;
		return this;
	}
	
	@Override 
	public int hashCode() {
		return hashCode;
	}

}
