package org.midheaven.keys;

import org.midheaven.lang.ValueClass;

import java.util.UUID;

@ValueClass
final class StringKey<C extends Concept> extends Key<C> {

    private final String conceptName;
    private final String value;

    StringKey(String conceptName, String value) {
        this.conceptName = conceptName;
        this.value = value;
    }

    @Override
    public String conceptName() {
        return conceptName;
    }

    @Override
    public String stringValue() {
        return value;
    }

    @Override
    public long longValue() {
        return Long.parseLong(value);
    }

    @Override
    public UUID uuidValue() {
        return UUID.fromString(value);
    }
}
