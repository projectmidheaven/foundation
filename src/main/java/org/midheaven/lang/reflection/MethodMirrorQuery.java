package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Method;
import java.util.function.IntPredicate;

public class MethodMirrorQuery implements MirrorQuery<Method>{

    private final Sequence<Method> candidates;

    MethodMirrorQuery(Sequence<Method> candidates){
        this.candidates = candidates;
    }

    public MethodMirrorQuery named(String name){
        return new MethodMirrorQuery(candidates.filter(it -> it.getName().equals(name)));
    }

    public MethodMirrorQuery withModified(IntPredicate predicate){
        return new MethodMirrorQuery(candidates.filter(it -> predicate.test(it.getModifiers())));
    }

    public MethodMirrorQuery withParametersCount(int count){
        return new MethodMirrorQuery(candidates.filter(it -> it.getParameterCount() == count));
    }

    public MethodMirrorQuery withParameterTypes(Class<?> ... types){
        return new MethodMirrorQuery(candidates.filter(it -> {
            var parameterTypes = it.getParameterTypes();
            for (int i = 0; i < it.getParameterCount(); i++){
                if (!types[i].isAssignableFrom(parameterTypes[i])){
                    return false;
                }
            }
            return true;
        }));
    }

    public MethodMirrorQuery withReturnType(Class<?> type){
        return new MethodMirrorQuery(candidates.filter(it -> type.isAssignableFrom(it.getReturnType())));
    }

    @Override
    public Sequence<Method> toSequence() {
        return candidates;
    }
}
