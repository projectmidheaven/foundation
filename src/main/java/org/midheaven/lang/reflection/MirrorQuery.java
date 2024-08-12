package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Method;

public interface MirrorQuery<T> {

    Sequence<Method> toSequence();
}
