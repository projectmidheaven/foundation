package org.midheaven.collections;

import org.junit.jupiter.api.Test;
import org.midheaven.collections.Assortment;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumeratorTestCases {

    Assortment<Integer> assortment = Sequence.builder().of(1,2,3);

    @Test
    public void enumerableEnds(){

        var list = new ArrayList<>();
        var enumerator = assortment.enumerator();
        while(enumerator.tryNext(it -> list.add(it)));

        assertEquals(3, list.size());
    }

}
