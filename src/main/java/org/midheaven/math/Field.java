package org.midheaven.math;

/**
 * Represents a mathematical Field. A Field is a AdditionGroup that is also a MultiplicationGroup
 * @param <T> the type of element in the Field
 */
public interface Field<T extends Field<T>> extends AdditionGroup<T> , MultiplicationGroup<T> {

}
