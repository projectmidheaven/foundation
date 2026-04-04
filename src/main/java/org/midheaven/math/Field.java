package org.midheaven.math;

/**
 * Defines the contract for Field.
 */
public interface Field<T extends Field<T>> extends AdditionGroup<T> , MultiplicationGroup<T> {

}
