package org.midheaven.lang.reflection;

import org.midheaven.lang.Maybe;

import java.util.ArrayList;
import java.util.List;

class CompositeProxyEngine implements ProxyEngine {

    private final List<ProxyEngine> engines = new ArrayList<>(2);

    public CompositeProxyEngine(){}

    public CompositeProxyEngine add(ProxyEngine engine){
        this.engines.add(engine);
        return this;
    }

    @Override
    public <T> boolean canProxy(Class<T> type) {
        return compatibleEngine(type).isPresent();
    }

    private Maybe<ProxyEngine> compatibleEngine(Class<?> type){
        for (var engine : engines){
            if (engine.canProxy(type)){
                return Maybe.of(engine);
            }
        }
        return Maybe.none();
    }

    @Override
    public <T> T proxy(Class<T> type, Class<?>[] otherTypes, InvocationHandler handler) {
        var engine = compatibleEngine(type).orElseThrow(() -> new ReflectionException("Is it not possible to proxy type " + type));

        return engine.proxy(type,otherTypes, handler);
    }
}
