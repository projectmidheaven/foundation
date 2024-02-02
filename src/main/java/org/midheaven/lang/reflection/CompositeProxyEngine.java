package org.midheaven.lang.reflection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private Optional<ProxyEngine> compatibleEngine(Class<?> type){
        for (var engine : engines){
            if (engine.canProxy(type)){
                return Optional.of(engine);
            }
        }
        return Optional.empty();
    }

    @Override
    public <T> T proxy(Class<T> type, Class<?>[] otherTypes, InvocationHandler handler) {
        var engine = compatibleEngine(type).orElseThrow(() -> new ReflectionException("Is it not possible to proxy type " + type));

        return engine.proxy(type,otherTypes, handler);
    }
}
