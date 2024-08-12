package org.midheaven.lang.reflection;

import org.midheaven.collections.Enumerable;
import org.midheaven.lang.Maybe;

import java.util.function.Function;

public interface PropertiesMirror<T> extends Enumerable<Property> {

    Maybe<Property> get(String name);

    default Maybe<Property> get(Property property){
        return Maybe.of(property)
                .map(p -> p.name())
                .flatMap(name ->  get(name));
    }

    <P> Maybe<Property> get(Function<T, P> selector);
}
