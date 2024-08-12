package org.midheaven.lang.reflection;

public enum TypeKind {

    ANNOTATION,
    ARRAY,
    CLASS,
    ENUM,
    PRIMITIVE,
    INTERFACE,
    RECORD
    ;

    public boolean isPrimitive() {
        return this == PRIMITIVE;
    }

    public boolean isInterface() {
        return this == INTERFACE;
    }

    public boolean isEnum() {
        return this == ENUM;
    }

    public boolean isRecord() {
        return this == RECORD;
    }

    public boolean isArray() {
        return this == ARRAY;
    }

    public boolean isAnnotation() {
        return this == ANNOTATION;
    }

    public boolean isClass() {
        return this == CLASS;
    }

}
