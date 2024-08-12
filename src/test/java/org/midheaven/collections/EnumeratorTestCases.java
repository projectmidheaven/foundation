package org.midheaven.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumeratorTestCases {

    Assortment<Integer> assortment = Sequence.builder().of(1,2,3);

    @Test
    public void enumerableEnds(){

        var list = new ArrayList<>();
        var enumerator = assortment.enumerator();
        while (enumerator.moveNext()){
            list.add(enumerator.current());
        }

        assertEquals(3, list.size());
    }

}
