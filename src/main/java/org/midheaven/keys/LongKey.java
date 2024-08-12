package org.midheaven.keys;

import org.midheaven.lang.ValueClass;

import java.util.UUID;

@ValueClass
final class LongKey<C extends Concept> extends Key<C> {

    private final String conceptName;
    private final long value;

    LongKey(String conceptName, long value) {
        this.conceptName = conceptName;
        this.value = value;
    }

    @Override
    public String conceptName() {
        return conceptName;
    }

    @Override
    public String stringValue() {
        return Long.toString(value);
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public UUID uuidValue() {
        throw new IllegalStateException("Long value is not an UUID");
    }
}
