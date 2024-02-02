package org.midheaven.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AssociationBuilder {

    public <K,V> Association<K,V> ofEntries(Association.Entry<K,V> ... entries){
        if (entries == null || entries.length == 0){
            return empty();
        }
        var map = new HashMap<K,V>();
        for (var entry : entries){
            map.put(entry.key(), entry.value());
        }
        return new ImmutableAssociationMapWrapper<>(map);
    }

    public <K, V> Association<K,V> immutable(EditableAssociation<K,V> association){
        if (association == null || association.isEmpty()){
            return empty();
        } else if (association instanceof EditableAssociationMapWrapper<K,V> editable){
            return new ImmutableAssociationMapWrapper<>(editable.original);
        }

        return from(association);
    }

    public <K, V> Association<K,V> from(Map<K,V> map){
        if (map == null || map.isEmpty()){
            return empty();
        }
        // copy the original map
        return new ImmutableAssociationMapWrapper<>(new HashMap<>(map));
    }

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

    public <K, V> Association<K,V> empty(){
        return new ImmutableAssociationMapWrapper<>(Collections.emptyMap());
    }

    public ResizableAssociationBuilder resizable(){
        return new ResizableAssociationBuilder();
    }
}
