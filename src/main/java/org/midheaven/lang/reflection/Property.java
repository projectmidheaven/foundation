package org.midheaven.lang.reflection;

import org.midheaven.lang.Maybe;

/**
 * Defines the contract for Property.
 */
public interface Property {

    /**
     * Performs name.
     * @return the result of name
     */
    String name();
    /**
     * Returns value Type.
     * @return the result of valueType
     */
    Class<?> valueType();
    /**
     * Checks whether is Optional.
     * @return the result of isOptional
     */
    boolean isOptional();

    /**
     * Checks whether can Read.
     * @return the result of canRead
     */
    boolean canRead();
    /**
     * Checks whether can Write.
     * @return the result of canWrite
     */
    boolean canWrite();

    /**
     * Returns get Value.
     * @param instance the instance value
     * @return the result of getValue
     */
    Maybe<Object> getValue(Object instance);
    /**
     * Performs set Value.
     * @param instance the instance value
     * @param value the value value
     */
    void setValue(Object instance, Object value);
}
