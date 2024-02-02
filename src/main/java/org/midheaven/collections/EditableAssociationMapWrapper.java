package org.midheaven.collections;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

class EditableAssociationMapWrapper<K, V> extends ImmutableAssociationMapWrapper<K,V> implements EditableAssociation<K,V> {

    EditableAssociationMapWrapper(Map<K,V> original){
        super(original);
    }

    @Override
    public final boolean setValue(K key, V value) {
        if(containsKey(key)){
            original.put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public V computeValue(K key, BiFunction<K, V, V> computation) {
        return original.compute(key, computation);
    }

}
