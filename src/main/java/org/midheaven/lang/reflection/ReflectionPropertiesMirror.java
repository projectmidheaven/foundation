package org.midheaven.lang.reflection;

import org.midheaven.collections.Association;
import org.midheaven.collections.Assortment;
import org.midheaven.collections.Enumerator;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.collections.Sequence;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

class ReflectionPropertiesMirror<T> implements PropertiesMirror<T> {

    private final PropertySelector<T> propertySelector;

    ReflectionPropertiesMirror(Class<T> type) {
        this.propertySelector = new PropertySelector<>(type);
    }

    @Override
    public Optional<Property> get(String name) {
        return propertySelector.getByName(name);
    }

    @Override
    public Optional<Property> get(Property property) {
        return get(property.name())
                .filter(p -> property.valueType().equals(p.valueType()));
    }

    @Override
    public <P> Optional<Property> get(Function<T, P> selector) {
        return propertySelector.property(selector);
    }

    @Override
    public Enumerator<Property> enumerator() {
        return this.propertySelector.properties().enumerator();
    }

    @Override
    public long count(){
        return this.propertySelector.properties().count();
    }

    @Override
    public boolean isEmpty(){
        return this.propertySelector.properties().isEmpty();
    }
}

class PropertySelector<T> implements InvocationHandler{

    private final T proxy;
    private final ResizableAssociation<String, Property> propertyAssociation = Association.builder().resizable().empty();
    private final Class<T> type;
    private Method selectedMethod;
    private boolean fullScan = false;

    public PropertySelector(Class<T> type) {
        this.type = type;
        this.proxy = Mirror.reflect(type).proxy(this);
    }

    Assortment<Property> properties() {
        if (!fullScan){
            Sequence.builder().of(type.getDeclaredMethods())
                .filter(m -> !m.getName().startsWith("set"))
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .map(m -> PropertyMirrorSupport.fromAccessor(m , type).orElse(null))
                .filter(Objects::nonNull)
                .associate(Property::name)
                .forEach(entry -> propertyAssociation.putValue(entry.key(), entry.value()));

            fullScan = true;
        }
        return propertyAssociation.values();
    }

    @Override
    public Object handleInvocation(Object proxy, Method method, Object[] args)  {
        this.selectedMethod = method;
        return null;
    }

    <P> Optional<Property> property(Function<T, P> selector){
        selector.apply(proxy);
        if (selectedMethod == null){
            return Optional.empty();
        }
        var name = PropertyMirrorSupport.propertyNameFromAccessor(selectedMethod);

        var possibleProperty = this.propertyAssociation.getValue(name);
        if (possibleProperty.isPresent()){
            return possibleProperty;
        }

        possibleProperty = PropertyMirrorSupport.fromAccessor(selectedMethod, type);

        possibleProperty.ifPresent(p ->  this.propertyAssociation.putValue(p.name(), p));

        return possibleProperty;
    }

    public Optional<Property> getByName(String name) {
        return propertyAssociation.getValue(name);
    }
}