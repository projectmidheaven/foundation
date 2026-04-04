package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Method;
import java.util.function.IntPredicate;

/**
 * Represents Method Mirror Query.
 */
public class MethodMirrorQuery implements MirrorQuery<Method>{

    private final Sequence<Method> candidates;

    MethodMirrorQuery(Sequence<Method> candidates){
        this.candidates = candidates;
    }

    /**
     * Returns named.
     * @param name the name value
     * @return the result of named
     */
    public MethodMirrorQuery named(String name){
        return new MethodMirrorQuery(candidates.filter(it -> it.getName().equals(name)));
    }

    /**
     * Performs withModified.
     * @param predicate the predicate value
     * @return the result of withModified
     */
    public MethodMirrorQuery withModified(IntPredicate predicate){
        return new MethodMirrorQuery(candidates.filter(it -> predicate.test(it.getModifiers())));
    }

    /**
     * Performs withParametersCount.
     * @param count the count value
     * @return the result of withParametersCount
     */
    public MethodMirrorQuery withParametersCount(int count){
        return new MethodMirrorQuery(candidates.filter(it -> it.getParameterCount() == count));
    }

    /**
     * Performs withParameterTypes.
     * @param types the types value
     * @return the result of withParameterTypes
     */
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

    /**
     * Performs withReturnType.
     * @param type the type value
     * @return the result of withReturnType
     */
    public MethodMirrorQuery withReturnType(Class<?> type){
        return new MethodMirrorQuery(candidates.filter(it -> type.isAssignableFrom(it.getReturnType())));
    }

    /**
     * Returns to Sequence.
     * @return the result of toSequence
     */
    @Override
    /**
     * Returns to Sequence.
     * @return the result of toSequence
     */
    public Sequence<Method> toSequence() {
        return candidates;
    }
}
