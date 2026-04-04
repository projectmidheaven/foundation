package org.midheaven.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Builder for Association instances.
 */
public class AssociationBuilder {
    
    static final AssociationBuilder INSTANCE = new AssociationBuilder();
    
    private AssociationBuilder(){}
    
    /**
     * Creates an instance from the provided value.
     * @param key the key
     * @param value the value
     * @return the created {@link Association}
     */
    public <K,V> Association<K,V> of(K key, V value){
       
        var map = new HashMap<K,V>();
        map.put(key, value);
        return new ImmutableAssociationMapWrapper<>(map);
    }
    
    /**
     * Returns of Entries.
     * @param entries the entries value
     * @return the result of ofEntries
     */
    @SafeVarargs
    public final <K,V> Association<K,V> ofEntries(Association.Entry<K, V>... entries){
        if (entries == null || entries.length == 0){
            return empty();
        }
        var map = new HashMap<K,V>();
        for (var entry : entries){
            map.put(entry.key(), entry.value());
        }
        return new ImmutableAssociationMapWrapper<>(map);
    }

    /**
     * Performs immutable.
     * @param association the association value
     * @return the result of immutable
     */
    public <K, V> Association<K,V> immutable(EditableAssociation<K,V> association){
        if (association == null || association.isEmpty()){
            return empty();
        } else if (association instanceof EditableAssociationMapWrapper<K,V> editable){
            return new ImmutableAssociationMapWrapper<>(editable.original);
        }

        return from(association);
    }

    /**
     * Creates an instance from the provided source.
     * @param map the map value
     * @return the result of from
     */
    public <K, V> Association<K,V> from(Map<K,V> map){
        if (map == null || map.isEmpty()){
            return empty();
        }
        // copy the original map
        if (map instanceof ConcurrentMap<K,V>){
            return new ImmutableAssociationMapWrapper<>(new ConcurrentHashMap<>(map));
        }
        return new ImmutableAssociationMapWrapper<>(new HashMap<>(map));
    }

    /**
     * Creates an instance from the provided source.
     * @param association the association value
     * @return the result of from
     */
    public <K, V> Association<K,V> from(Association<K,V> association){
        if (association == null || association.isEmpty()){
            return empty();
        } else if (association instanceof EditableAssociationMapWrapper<K,V> editable){
            return new ImmutableAssociationMapWrapper<>(new HashMap<>(editable.original));
        }
        var map = new HashMap<K,V>();
        for (var entry : association){
            map.put(entry.key(), entry.value());
        }
        return new ImmutableAssociationMapWrapper<>(map);
    }

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public <K, V> Association<K,V> empty(){
        return ImmutableEmptyAssociation.instance();
    }

    /**
     * Performs resizable.
     * @return the result of resizable
     */
    public ResizableAssociationBuilder resizable(){
        return new ResizableAssociationBuilder();
    }
    
    /**
     * Performs concurrent.
     * @return the result of concurrent
     */
    public ResizableAssociationBuilder concurrent(){
        return new ConcurrentAssociationBuilder();
    }
}
