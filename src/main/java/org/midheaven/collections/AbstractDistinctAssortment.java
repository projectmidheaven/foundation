package org.midheaven.collections;

abstract class AbstractDistinctAssortment<T> implements DistinctAssortment<T> {
    
    @Override
    public final boolean equals(Object other){
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
