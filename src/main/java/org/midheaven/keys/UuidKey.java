package org.midheaven.keys;

import org.midheaven.lang.ValueClass;

import java.util.UUID;

@ValueClass
final class UuidKey<C extends Concept> extends Key<C> {

    private final String conceptName;
    private final UUID value;

    UuidKey(String conceptName, UUID value) {
        this.conceptName = conceptName;
        this.value = value;
    }

    @Override
    public String conceptName() {
        return conceptName;
    }

    @Override
    public String stringValue() {
        return value.toString();
    }

    @Override
    public long longValue() {
        throw new IllegalStateException("UUID value is not a long");
    }

    @Override
    public UUID uuidValue() {
       return value;
    }
}
