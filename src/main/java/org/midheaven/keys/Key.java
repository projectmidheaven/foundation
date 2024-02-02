package org.midheaven.keys;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Strings;

import java.util.Objects;
import java.util.Optional;

public abstract class Key<C extends Concept> {

    public static <K extends Concept> Key<K> of(Class<K> concept, String value){
        if (value == null) return null;
        return new DefaultKey<>(conceptNameOf(concept), value);
    }

    public static <K extends Concept> Key<K> parse(Class<K> concept, String textualRepresentation) {
        var conceptName = conceptNameOf(concept);
        var parts = Strings.Splitter.split(textualRepresentation).by("::");
        if (parts.isEmpty()){
            throw new KeyParsingException("Cannot parse '" + textualRepresentation + "' as concept " + conceptName + ". Wrong format.");
        }
        if (!parts.get(1).equalsIgnoreCase(conceptName)){
            throw new KeyParsingException("Cannot parse '" + textualRepresentation + "' as concept " + conceptName + ". Concepts do not match.");
        }
        return new DefaultKey<>(conceptName, parts.get(0));
    }

    public static <K extends Concept> Optional<Key<K>> tryParse(Class<K> concept, String textualRepresentation) {
        try {
            return Optional.ofNullable(parse(concept, textualRepresentation));
        } catch (KeyParsingException e){
            return  Optional.empty();
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

    public abstract String conceptName();
    public abstract String value();

    @Override
    public int hashCode(){
        return HashCode.asymmetric().add(value()).add(conceptName()).hashCode();
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Key key
                && this.value().equals(key.value())
                && this.conceptName().equals(key.conceptName());
    }

    @Override
    public String toString(){
        return value() + "::" + conceptName();
    }
}


final /*value*/ class DefaultKey<C extends Concept> extends Key<C>{

    private final String conceptName;
    private final String value;

    public DefaultKey(String conceptName, String value) {
        this.conceptName = conceptName;
        this.value = value;
    }

    @Override
    public String conceptName() {
        return conceptName;
    }

    @Override
    public String value() {
        return value;
    }
}