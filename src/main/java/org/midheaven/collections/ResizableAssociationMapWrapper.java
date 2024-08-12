package org.midheaven.collections;

import org.midheaven.lang.Maybe;

import java.util.Iterator;
import java.util.Map;
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
    public Maybe<V> removeKey(K key) {
        return Maybe.of(original.remove(key));
    }

    @Override
    public V computeValueIfAbsent(K key, Function<K, V> computation) {
        return original.computeIfAbsent(key, computation);
    }

    public Iterator<Entry<K,V>> iterator(){
        return new TransformIterator<>(this.original.entrySet().iterator(), Entry::from);
    }
//
//    @Override
//    public void intersectWith(Association<K, V> other, BiFunction<V, V, V> valueSelector) {
//        var set = DistinctAssortment.builder().resizable().<K>empty();
//        for (var entry : this){
//            if (other.containsKey(entry.key())){
//                putValue(entry.key(), valueSelector.apply(this.getValue(entry.key()).orElse(null), entry.value()));
//            } else {
//                set.add(entry.key());
//            }
//        }
//        for (var key : set){
//            this.removeKey(key);
//        }
//
//    }
}
