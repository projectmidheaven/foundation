package org.midheaven.lang.reflection;

import org.midheaven.collections.Enumerable;
import org.midheaven.lang.Maybe;

import java.util.function.Function;

/**
 * Defines the contract for Properties Mirror.
 * @param <T> the type with properties
 */
public interface PropertiesMirror<T> extends Enumerable<Property> {

    /**
     * Performs get.
     * @param name the name value
     * @return the result of get
     */
    Maybe<Property> get(String name);

    /**
     * Performs get.
     * @param property the property value
     * @return the result of get
     */
    default Maybe<Property> get(Property property){
        return Maybe.of(property)
                .map(p -> p.name())
                .flatMap(name ->  get(name));
    }

    <P> Maybe<Property> get(Function<T, P> selector);
}
