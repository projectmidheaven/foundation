package org.midheaven.collections;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

class ResizableAssociationMapWrapper<K, V> extends EditableAssociationMapWrapper<K,V> implements ResizableAssociation<K,V> {

    ResizableAssociationMapWrapper(Map<K,V> original){
        super(original);
    }

    @Override
    public void putValue(K key, V value) {
        original.put(key, value);
    }

    @Override
    public void clear() {
        original.clear();
    }

    @Override
    public Optional<V> removeKey(K key) {
        return Optional.ofNullable(original.remove(key));
    }

    @Override
    public V computeValueIfAbsent(K key, Function<K, V> computation) {
        return this.getValue(key).orElseGet(() -> computation.apply(key));
    }

    @Override
    public void unionWith(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        for (var entry : other){
            if (containsKey(entry.key())){
                putValue(entry.key(), valueSelector.apply(this.getValue(entry.key()).orElse(null), entry.value()));
            } else {
                putValue(entry.key(), entry.value());
            }
        }
    }

    @Override
    public void intersectWith(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
        var set = DistinctAssortment.builder().resizable().<K>empty();
        for (var entry : this){
            if (other.containsKey(entry.key())){
                putValue(entry.key(), valueSelector.apply(this.getValue(entry.key()).orElse(null), entry.value()));
            } else {
                set.add(entry.key());
            }
        }
        for (var key : set){
            this.removeKey(key);
        }

    }
}
