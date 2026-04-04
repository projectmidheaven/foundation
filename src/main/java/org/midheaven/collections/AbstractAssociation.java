package org.midheaven.collections;

abstract class AbstractAssociation<K,V> implements Association<K, V> {
    
    @Override
    public final boolean equals(Object other){
        return other instanceof Association that
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
