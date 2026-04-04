package org.midheaven.collections;

 class DistinctAssortmentEnumerableView<T> extends AbstractEnumerableView<T> implements DistinctAssortment<T> {
    
     DistinctAssortmentEnumerableView(Enumerable<T> original) {
         super(original);
     }
     
     @Override
     public boolean contains(T candidate) {
         return original.anyMatch(it -> it.equals(candidate));
     }
     @Override
     public boolean equals(Object other){
         return other instanceof DistinctAssortment that
                    && AssortmentSupport.equals(this, that);
     }
     
     @Override
     public int hashCode (){
         return this.count().hashCode();
     }
     
     @Override
     public String toString (){
         return AssortmentSupport.toString(this, '{', '}');
     }
 }
