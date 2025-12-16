package org.midheaven.collections;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TraversableTestCases {

    Traversable<Integer> traversable = Traversable.from(1,2,3);
    
    @Test
    public void traversableToSequence(){
        try (var t = traversable.map(it -> it * 5)){
            var sequence = t.collect(org.midheaven.collections.Collectors.toSequence());
            
            assertFalse(sequence.isEmpty());
            assertEquals(3, sequence.count().toInt());
            assertEquals(5, sequence.getAt(0).orElseThrow());
            assertEquals(10, sequence.getAt(1).orElseThrow());
            assertEquals(15, sequence.getAt(2).orElseThrow());
        }
  
        assertIsClosed(traversable);
    }
    
    private void assertIsClosed(Traversable<?> traversable) {
        assertThrows(IllegalStateException.class, traversable::iterator);
    }
    
    @Test
    public void traversableMap(){
        try (var t = traversable.map(it -> it * 5)){
            var list = t.collect(Collectors.toList());
            
            assertFalse(list.isEmpty());
            assertEquals(3, list.size());
            assertEquals(5, list.get(0));
            assertEquals(10, list.get(1));
            assertEquals(15, list.get(2));
        }
        
        assertIsClosed(traversable);
    }

    @Test
    public void traversableFlatMap(){
        try (var t =  traversable.flatMap(it -> Traversable.from(Sequence.builder().of(it, it + 1)))){
            var list = t.collect(Collectors.toList());
            
            assertFalse(list.isEmpty());
            assertEquals(6, list.size());
            assertEquals(1, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(2, list.get(2));
            assertEquals(3, list.get(3));
            assertEquals(3, list.get(4));
            assertEquals(4, list.get(5));
        }
        
        assertIsClosed(traversable);
    }

    @Test
    public void traversableConcat(){
   
        try ( var a = Traversable.from(1,2,3)){
            try (var b = Traversable.<Integer>empty()){
                try (var c = Traversable.from(4,5,6)){
                    try ( var t = a.concat(b).concat(c)){
                        var list = t.collect(Collectors.toList());
                        
                        assertFalse(list.isEmpty());
                        assertEquals(6, list.size());
                        assertEquals(1, list.get(0));
                        assertEquals(2, list.get(1));
                        assertEquals(3, list.get(2));
                        assertEquals(4, list.get(3));
                        assertEquals(5, list.get(4));
                        assertEquals(6, list.get(5));
                    }
                }
            }
        }
        
        try ( var a = Traversable.<Integer>empty()){
            try (var b = Traversable.from(1,2,3)){
                try ( var t = a.concat(b)){
                    var list = t.collect(Collectors.toList());
                    
                    assertFalse(list.isEmpty());
                    assertEquals(3, list.size());
                    assertEquals(1, list.get(0));
                    assertEquals(2, list.get(1));
                    assertEquals(3, list.get(2));
                }
            }
        }
        
        try ( var a = Traversable.from(1,2,3) ){
            try (var b = Traversable.<Integer>empty()){
                try ( var t = a.concat(b)){
                    var list = t.collect(Collectors.toList());
                    
                    assertFalse(list.isEmpty());
                    assertEquals(3, list.size());
                    assertEquals(1, list.get(0));
                    assertEquals(2, list.get(1));
                    assertEquals(3, list.get(2));
                }
            }
        }
        
        try ( var a = Traversable.<Integer>empty()){
            try (var b = Traversable.<Integer>empty()){
                try ( var t = a.concat(b)){
                    var list = t.collect(Collectors.toList());
                    
                    assertEquals(0, list.size());
                    assertTrue(list.isEmpty());
                  
                }
            }
        }
    }
    
    @Test
    public void traversableZip(){
        var a = Traversable.from(Enumerable.iterate(0, it -> it + 1).limit(10));
        var b = Traversable.from(Enumerable.iterate(0, it -> it + 1).limit(15));

        try (var t = a.zip(b, (x,y) -> x * y)){
            var list = t.collect(Collectors.toList());
         
            assertFalse(list.isEmpty());
            assertEquals(10, list.size());
            
            assertThrows(IllegalStateException.class, () -> t.collect(Collectors.toList()));
    
        }
    }

}
