package org.midheaven.lang;

import org.junit.jupiter.api.Test;
import org.midheaven.lang.reflection.ReifiedTypeReference;
import org.midheaven.lang.reflection.TypeReference;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionTestCases {

    @Test
    public void referenceTypeOfNonGenericClass() {
        var type = new ReifiedTypeReference<String>(){};

        assertEquals(String.class, type.rootClass());
        assertTrue(type.parameterTypes().isEmpty());
    }

    @Test
    public void referenceTypeOfGenericClass() {
        var type = new ReifiedTypeReference<List<String>>() {};

        assertEquals(List.class, type.rootClass());
        assertEquals(1, type.parameterTypes().size());
        assertEquals(String.class, type.parameterTypes().getAt(0).map(TypeReference::rootClass).orElse(null));
    }

    @Test
    public void referenceTypeOfDoubleGenericClass() {
        var type = new ReifiedTypeReference<List<List<String>>>() {};

        assertEquals(List.class, type.rootClass());
        assertEquals(1, type.parameterTypes().size());
        assertEquals(
                List.class,
                type.parameterTypes().getAt(0).map(TypeReference::rootClass).orElse(null)
        );
        assertEquals(
                String.class,
                type.parameterTypes().getAt(0)
                .flatMap(it -> it.parameterTypes().getAt(0))
                .map(TypeReference::rootClass).orElse(null)
        );
    }

    @Test
    public void referenceEquals() {
        var a = new ReifiedTypeReference<List<String>>() {};
        var b = new ReifiedTypeReference<List<String>>() {};

        assertEquals(a, b);

        var c = new ReifiedTypeReference<List<Integer>>() {};

        assertNotEquals(a, c);

        var d = new ReifiedTypeReference<String>() {};
        var g = TypeReference.of(String.class);

        assertEquals(d, g);
    }

    @Test
    public void referenceTypeCast() {
        var stringList = new ReifiedTypeReference<List<String>>() {};

        assertTrue(List.class.isInstance(stringList.cast(List.of("a"))));
        assertTrue(List.class.isInstance(stringList.cast(List.of(1))));// can't avoid

        var string = new ReifiedTypeReference<String>() {};

        assertTrue(String.class.isInstance(string.cast("a")));
        assertThrows(ClassCastException.class, () -> String.class.isInstance(string.cast(1)));
    }

    @Test
    public void referenceTypeAssignable() {
        var a = new ReifiedTypeReference<List<String>>() {};
        var b = new ReifiedTypeReference<List<Integer>>() {};
        var c = new ReifiedTypeReference<Integer>() {};

        assertTrue(a.isAssignableTo(List.class));
        assertTrue(b.isAssignableTo(List.class));
        assertFalse(c.isAssignableTo(List.class));
        assertTrue(c.isAssignableTo(Integer.class));
        assertFalse(c.isAssignableTo(String.class));
    }
}
