package org.midheaven.collections;

import java.util.HashMap;
import java.util.Map;

public class ResizableAssociationBuilder {

    public <K,V> ResizableAssociation<K,V> ofEntries(Association.Entry<K,V> ... entries){
        if (entries == null || entries.length == 0){
            return empty();
        }
        var map = new HashMap<K,V>();
        for (var entry : entries){
            map.put(entry.key(), entry.value());
        }
        return new ResizableAssociationMapWrapper<>(map);
    }

    public <K, V> ResizableAssociation<K,V> from(Association<K,V> association){
        var map = new HashMap<K,V>();
        for (var entry : association){
            map.put(entry.key(), entry.value());
        }
        return new ResizableAssociationMapWrapper<>(map);
    }

    public <K, V> ResizableAssociation<K,V> from(Map<K,V> map){
        return new ResizableAssociationMapWrapper<>(new HashMap<>(map));
    }

    public <K, V> ResizableAssociation<K,V> empty(){
        return new ResizableAssociationMapWrapper<>(new HashMap<>());
    }
}
