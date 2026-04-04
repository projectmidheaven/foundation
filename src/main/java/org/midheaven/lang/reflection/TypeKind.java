package org.midheaven.lang.reflection;

/**
 * Enumerates Type Kind values.
 */
public enum TypeKind {

    ANNOTATION,
    ARRAY,
    CLASS,
    ENUM,
    PRIMITIVE,
    INTERFACE,
    RECORD
    ;

    /**
     * Checks whether is Primitive.
     * @return the result of isPrimitive
     */
    public boolean isPrimitive() {
        return this == PRIMITIVE;
    }

    /**
     * Checks whether is Interface.
     * @return the result of isInterface
     */
    public boolean isInterface() {
        return this == INTERFACE;
    }

    /**
     * Checks whether is Enum.
     * @return the result of isEnum
     */
    public boolean isEnum() {
        return this == ENUM;
    }

    /**
     * Checks whether is Record.
     * @return the result of isRecord
     */
    public boolean isRecord() {
        return this == RECORD;
    }

    /**
     * Checks whether is Array.
     * @return the result of isArray
     */
    public boolean isArray() {
        return this == ARRAY;
    }

    /**
     * Checks whether is Annotation.
     * @return the result of isAnnotation
     */
    public boolean isAnnotation() {
        return this == ANNOTATION;
    }

    /**
     * Checks whether is Class.
     * @return the result of isClass
     */
    public boolean isClass() {
        return this == CLASS;
    }

}
