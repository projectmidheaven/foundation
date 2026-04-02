package org.midheaven.collections;

 class DistinctAssortmentEnumerableView<T> extends AbstractEnumerableView<T> implements DistinctAssortment<T> {
    
     DistinctAssortmentEnumerableView(Enumerable<T> original) {
         super(original);
     }
     
     @Override
     public boolean contains(T any) {
         return original.anyMatch(it -> it.equals(any));
     }
 }
