package org.midheaven.collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ConcurrentAssociationBuilder extends ResizableAssociationBuilder{
    
    protected <K,V> Map<K,V> baseMap(){
        return new ConcurrentHashMap<>();
    }
    
    protected <K,V> Map<K,V> baseMap(Map<K,V> original){
        return new ConcurrentHashMap<>(original);
    }
}
