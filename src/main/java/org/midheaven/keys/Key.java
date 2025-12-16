package org.midheaven.keys;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

import java.util.Objects;
import java.util.UUID;

@ValueClass
public abstract class Key<C extends Concept> {

    public static <K extends Concept> Key<K> of(Class<K> concept, String value){
        return Strings.filled(value)
            .<Key<K>>map(it -> new StringKey<>(conceptNameOf(concept), it))
            .orNull();
    }

    public static <K extends Concept> @Nullable Key<K> of(Class<K> concept, long value){
        return new LongKey<>(conceptNameOf(concept), value);
    }

    public static <K extends Concept> Key<K> of(Class<K> concept, UUID value){
        return Maybe.of(value)
                .<Key<K>>map(it -> new UuidKey<>(conceptNameOf(concept), it))
                .orNull();
    }
    
    public static <K extends Concept> Key<K> compose(String conceptName, Object value) {
        if (conceptName == null || value == null){
            return null;
        } else if (value instanceof String id){
            return new StringKey<>(conceptName, id);
        } else if (value instanceof Long id){
            return new LongKey<>(conceptName, id);
        } else if (value instanceof Integer id){
            return new LongKey<>(conceptName, id);
        } else if (value instanceof UUID id){
            return new UuidKey<>(conceptName, id);
        }
        throw new IllegalArgumentException(value.getClass() + " is not a recognized key type");
    }
    
    public static <K extends Concept> Key<K> parse(String textualRepresentation) {
        if (Strings.isBlank(textualRepresentation)){
            return null;
        }

        var parts = Strings.Splitter.split(textualRepresentation).by("::");
        if (parts.isEmpty()){
            throw new KeyParsingException("Cannot parse '" + textualRepresentation + "'. Wrong format.");
        }
        return new StringKey<>(parts.get(1), parts.get(0));
    }

    public static <K extends Concept> Maybe<Key<K>> tryParse(String textualRepresentation) {
        try {
            return Strings.filled(textualRepresentation).map(Key::parse);
        } catch(Exception e){
            return Maybe.none();
        }
    }

    private static String conceptNameOf(Class<?> concept){
        Objects.requireNonNull(concept);
        if (concept.getInterfaces().length != 1){
            throw new IllegalArgumentException("Type " + concept + " is not a Concept");
        } else if ((Concept.class.equals(concept.getInterfaces()[0]))){
            return concept.getSimpleName();
        }
        return conceptNameOf(concept.getInterfaces()[0]);

    }

    public <C extends Concept> Key<C> ensureConcept(Class<C> concept){
        if (!this.conceptName().equals(conceptNameOf(concept))){
            throw new KeyException("Key is not compatible with concept " + concept.getName());
        }
        return (Key<C>) this;
    }

    protected Key(){}

    public abstract String conceptName();
    public abstract String stringValue();
    public abstract long longValue();
    public abstract UUID uuidValue();
    public abstract Object value();
    
    @Override
    public int hashCode(){
        return HashCode.asymmetric().add(stringValue()).add(conceptName()).hashCode();
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Key key
                && this.stringValue().equals(key.stringValue())
                && this.conceptName().equalsIgnoreCase(key.conceptName());
    }

    @Override
    public String toString(){
        return stringValue() + "::" + conceptName();
    }
}


