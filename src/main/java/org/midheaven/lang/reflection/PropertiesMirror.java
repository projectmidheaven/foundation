package org.midheaven.lang.reflection;

import org.midheaven.collections.Enumerable;

import java.util.Optional;
import java.util.function.Function;

public interface PropertiesMirror<T> extends Enumerable<Property> {

    Optional<Property> get(String name);

    default Optional<Property> get(Property property){
        return Optional.ofNullable(property)
                .map(p -> p.name())
                .flatMap(name ->  get(name));
    }

    <P> Optional<Property> get(Function<T, P> selector);
}
