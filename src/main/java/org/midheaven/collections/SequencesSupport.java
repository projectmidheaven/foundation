package org.midheaven.collections;

import java.util.Objects;

class SequencesSupport {

    static <T> boolean equals(Sequence<T> a, Sequence<T> b){
        if (a.isEmpty() && b.isEmpty()){
            return true;
        } else if (a.size() != b.size()){
            return false;
        }
        return a.zip(b, Objects::equals).allMatch(it -> it);
    }
}
