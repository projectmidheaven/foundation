package org.midheaven.collections;

import java.util.HashMap;
import java.util.Map;


public class ResizableAssociationBuilder {
    
    /**
     * Performs baseMap.
     * @return the result of baseMap
     */
    protected <K,V> Map<K,V> baseMap(){
        return new HashMap<K,V>();
    }
    
    /**
     * Performs baseMap.
     * @param original the original value
     * @return the result of baseMap
     */
    protected <K,V> Map<K,V> baseMap(Map<K,V> original){
        return new HashMap<K,V>(original);
    }
    
    /**
     * Returns of Entries.
     * @param entries the entries value
     * @return the result of ofEntries
     */
    public <K,V> ResizableAssociation<K,V> ofEntries(Association.Entry<K,V> ... entries){
        if (entries == null || entries.length == 0){
            return empty();
        }
        var map = this.<K,V>baseMap();
        for (var entry : entries){
            map.put(entry.key(), entry.value());
        }
        return new ResizableAssociationMapWrapper<>(map);
    }

    /**
     * Creates an instance from the provided source.
     * @param association the association value
     * @return the result of from
     */
    public <K, V> ResizableAssociation<K,V> from(Association<K,V> association){
        var map = this.<K,V>baseMap();
        for (var entry : association){
            map.put(entry.key(), entry.value());
        }
        return new ResizableAssociationMapWrapper<>(map);
    }

    /**
     * Creates an instance from the provided source.
     * @param map the map value
     * @return the result of from
     */
    public <K, V> ResizableAssociation<K,V> from(Map<K,V> map){
        return new ResizableAssociationMapWrapper<>(baseMap(map));
    }

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public <K, V> ResizableAssociation<K,V> empty(){
        return new ResizableAssociationMapWrapper<>(baseMap());
    }
}
