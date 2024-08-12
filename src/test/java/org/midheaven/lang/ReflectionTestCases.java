package org.midheaven.lang;

import org.junit.jupiter.api.Test;
import org.midheaven.lang.model.TestPojo;
import org.midheaven.lang.reflection.Mirror;
import org.midheaven.lang.reflection.ParametricTypeReference;
import org.midheaven.lang.reflection.ReifiedTypeReference;
import org.midheaven.lang.reflection.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertEquals(1, type.parameterTypes().count().toInt());
        assertEquals(String.class, type.parameterTypes().getAt(0).map(TypeReference::rootClass).orElse(null));
    }

    @Test
    public void referenceTypeOfDoubleGenericClass() {
        var type = new ReifiedTypeReference<List<List<String>>>() {};

        assertEquals(List.class, type.rootClass());
        assertEquals(1, type.parameterTypes().count().toInt());
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

        ParametricTypeReference<List<Integer>> d = ParametricTypeReference.collection(List.class, Integer.class);
        assertEquals(b, d);

        ParametricTypeReference<Map<String, Integer>> g = ParametricTypeReference.map(Map.class, String.class, Integer.class);
        assertEquals(new ReifiedTypeReference<Map<String, Integer>>() {}, g);
    }

    @Test
    public void propertiesAreReadCorrectly() {
        var properties = Mirror.reflect(TestPojo.class).properties();

        assertNotNull(properties);
        assertEquals(4, properties.count().toLong());
        assertNotNull(properties.get("Age"));
        assertNotNull(properties.get("Name"));
        assertNotNull(properties.get("Height"));
        assertNotNull(properties.get("Width"));

        TestPojo pojo = new TestPojo();
        properties.get("Height").orElseThrow().setValue(pojo, 3.14);
        assertEquals(3.14,pojo.getHeight().get());

        Optional<Double> optional = (Optional<Double>) properties.get("Height").orElseThrow().getValue(pojo).orElseThrow();
        assertNotNull(optional);
        assertEquals(3.14,optional.get());

        properties.get("Width").orElseThrow().setValue(pojo, 3.14);
        assertEquals(3.14,pojo.getWidth().get());
        Maybe maybe = properties.get("Width").orElseThrow().getValue(pojo);
        assertNotNull(maybe);
    }
}
