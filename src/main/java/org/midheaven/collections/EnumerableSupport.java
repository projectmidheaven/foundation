package org.midheaven.collections;


import org.midheaven.math.Int;

import java.util.function.IntFunction;
import java.util.function.IntSupplier;

class EnumerableSupport {
    
    static Object[] toArray(Enumerable<?> enumerable, IntSupplier lengthSupplier) {
      
        var array = new Object[lengthSupplier.getAsInt()];
        int index = 0;
        for (var item : enumerable){
            array[index++] = item;
        }
        return array;
    }
    
    static int resolveArrayLength(Enumerable<?> enumerable){
        Int count;
        try {
            count = enumerable.count();
        } catch (InfiniteEnumerableException e){
            // change message
            throw new InfiniteEnumerableException("Infinite enumerable cannot be made into an array");
        }
        
        if (count.toLong() >= Integer.MAX_VALUE){
            // too long
            throw new InfiniteEnumerableException("Enumerable longer than Integer.MAX_VALUE cannot be made into an array");
        }
        
        return count.toInt();
    }
    
    static <T> T[] toArray(Enumerable<T> enumerable, IntSupplier lengthSupplier, IntFunction<T[]> generator){
        return copyTo(enumerable, generator.apply( lengthSupplier.getAsInt()));
   
    }
    
    private static <T> T[] copyTo(Enumerable<T> enumerable, T[] array){
        int index = 0;
        for (var item : enumerable){
            array[index++] = item;
        }
        return array;
    }
    
    static <T> T[] toArray(Enumerable<T> enumerable,IntSupplier lengthSupplier, T[] templateArray){
        return copyTo(enumerable, correctLengthArray(templateArray, lengthSupplier));
    }
    
    static <T> T[] correctLengthArray(T[] templateArray, IntSupplier lengthSupplier) {
        var length = lengthSupplier.getAsInt();
        if (templateArray.length >= length){
           return templateArray;
        }
        
        return (T[])java.lang.reflect.Array.newInstance(templateArray.getClass().getComponentType(), length);
        
    }
    
    public static <T> Length resolveNonInfiniteLength(Enumerator<T> enumerator, String exceptionMessage) throws  InfiniteEnumerableException {
        var length = enumerator.length();
        
        if (length instanceof Length.Infinite){
            throw new InfiniteEnumerableException(exceptionMessage);
        }
        
        return length;
        
    }
}
