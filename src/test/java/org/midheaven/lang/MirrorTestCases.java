package org.midheaven.lang;

import org.junit.jupiter.api.Test;
import org.midheaven.lang.model.TestPojo;
import org.midheaven.lang.model.TestInterface;
import org.midheaven.lang.model.TestRecord;
import org.midheaven.lang.reflection.Mirror;

import static org.junit.jupiter.api.Assertions.*;

public class MirrorTestCases {

    @Test
    public void newInstance(){
        var instance = Mirror.reflect(TestPojo.class).newInstance();

        assertNotNull(instance);
    }

    @Test
    public void properties(){
        var properties = Mirror.reflect(TestPojo.class).properties();

        assertNotNull(properties);
        assertEquals(2, properties.count());
        assertTrue(properties.get("Age").isPresent());
        assertNotNull(properties.get("Name").isPresent());

    }

    @Test
    public void newRecordInstance(){
        var instance = Mirror.reflect(TestRecord.class).newInstance("Hello", 23);

        assertNotNull(instance);
        assertEquals("Hello", instance.name());
        assertEquals(23, instance.age());
    }

    @Test
    public void newInstanceWithParameters(){
        var instance = Mirror.reflect(TestPojo.class).newInstance("Hello");

        assertNotNull(instance);
        assertEquals("Hello", instance.getName());

        instance = Mirror.reflect(TestPojo.class).newInstance(2);

        assertNotNull(instance);
        assertEquals(2, instance.getAge());
    }

    @Test
    public void proxyInterface(){
        var instance = Mirror.reflect(TestInterface.class)
                .proxy((proxy, method, args) ->
                    switch (method.getName()){
                        case "sum" -> (Object)(((Integer)args[0]) + ((Integer) args[1]));
                        case "toString" -> "Hello";
                        default -> "";
                    }
                );

        assertNotNull(instance);
        assertEquals(3, instance.sum(1, 2));
        assertEquals("Hello", instance.toString());
    }

    @Test
    public void proxyClasses(){
        var instance = Mirror.reflect(TestPojo.class)
                .proxy((proxy, method, args) ->
                        switch (method.getName()){
                            case "getName" -> "Proxy";
                            case "getAge" -> 42;
                            default -> "";
                        }
                );

        assertNotNull(instance);
        assertEquals(42, instance.getAge());
        assertEquals("Proxy", instance.getName());
    }
}

