package org.midheaven.keys;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

import java.util.Objects;
import java.util.UUID;

@ValueClass
/**
 * Represents Key.
 */
public abstract class Key<C extends Concept> {

    /**
     * Creates an instance from the provided value.
     * @param concept the concept value
     * @param value the value value
     * @return the result of of
     */
    public static <K extends Concept> Key<K> of(Class<K> concept, String value){
        return Strings.filled(value)
            .<Key<K>>map(it -> new StringKey<>(conceptNameOf(concept), it))
            .orNull();
    }

    /**
     * Creates an instance from the provided value.
     * @param concept the concept value
     * @param value the value value
     * @return the result of of
     */
    public static <K extends Concept> @Nullable Key<K> of(Class<K> concept, long value){
        return new LongKey<>(conceptNameOf(concept), value);
    }

    /**
     * Creates an instance from the provided value.
     * @param concept the concept value
     * @param value the value value
     * @return the result of of
     */
    public static <K extends Concept> Key<K> of(Class<K> concept, UUID value){
        return Maybe.of(value)
                .<Key<K>>map(it -> new UuidKey<>(conceptNameOf(concept), it))
                .orNull();
    }
    
    /**
     * Performs compose.
     * @param conceptName the conceptName value
     * @param value the value value
     * @return the result of compose
     */
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
    
    /**
     * Parses the provided value.
     * @param textualRepresentation the textualRepresentation value
     * @return the result of parse
     */
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

    /**
     * Performs tryParse.
     * @param textualRepresentation the textualRepresentation value
     * @return the result of tryParse
     */
    public static <K extends Concept> Maybe<Key<K>> tryParse(String textualRepresentation) {
        try {
            return Strings.filled(textualRepresentation).map(Key::parse);
        } catch(Exception e){
            return Maybe.none();
        }
    }

    /**
     * Performs conceptNameOf.
     * @param concept the concept value
     * @return the result of conceptNameOf
     */
    private static String conceptNameOf(Class<?> concept){
        Objects.requireNonNull(concept);
        if (concept.getInterfaces().length != 1){
            throw new IllegalArgumentException("Type " + concept + " is not a Concept");
        } else if ((Concept.class.equals(concept.getInterfaces()[0]))){
            return concept.getSimpleName();
        }
        return conceptNameOf(concept.getInterfaces()[0]);

    }

    /**
     * Performs ensureConcept.
     * @param concept the concept value
     * @return the result of ensureConcept
     */
    public <C extends Concept> Key<C> ensureConcept(Class<C> concept){
        if (!this.conceptName().equals(conceptNameOf(concept))){
            throw new KeyException("Key is not compatible with concept " + concept.getName());
        }
        return (Key<C>) this;
    }
    
    protected Key(){}

    /**
     * Performs conceptName.
     * @return the result of conceptName
     */
    public abstract String conceptName();
    /**
     * Performs stringValue.
     * @return the result of stringValue
     */
    public abstract String stringValue();
    /**
     * Performs longValue.
     * @return the result of longValue
     */
    public abstract long longValue();
    /**
     * Performs uuidValue.
     * @return the result of uuidValue
     */
    public abstract UUID uuidValue();
    /**
     * Performs value.
     * @return the result of value
     */
    public abstract Object value();
    
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    @Override
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode(){
        return HashCode.asymmetric().add(stringValue()).add(conceptName()).hashCode();
    }

    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public boolean equals(Object other){
        return other instanceof Key key
                && this.stringValue().equals(key.stringValue())
                && this.conceptName().equalsIgnoreCase(key.conceptName());
    }

    /**
     * Returns to String.
     * @return the result of toString
     */
    @Override
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString(){
        return stringValue() + "::" + conceptName();
    }
}


