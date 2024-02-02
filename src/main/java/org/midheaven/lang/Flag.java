package org.midheaven.lang;

public interface Flag<E extends FlagElement<E>> {

    boolean isSet(FlagElement<E> candidate);

    Flag<E> set(FlagElement<E> element);

    Flag<E> clear(FlagElement<E> element);

    Flag<E> flip(FlagElement<E> element);

}