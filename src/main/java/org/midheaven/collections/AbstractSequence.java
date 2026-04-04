package org.midheaven.collections;

abstract class AbstractSequence <T> implements Sequence<T>{
    
    @Override
    public final boolean equals(Object other){
        return other instanceof Sequence that
                   && AssortmentSupport.equals(this, that);
    }
    
    @Override
    public int hashCode (){
        return this.count().hashCode();
    }
    
    @Override
    public String toString (){
        return AssortmentSupport.toString(this, '[', ']');
    }
    
}
