package org.midheaven.lang.reflection;

import org.midheaven.collections.Association;
import org.midheaven.collections.Assortment;
import org.midheaven.collections.Enumerator;
import org.midheaven.collections.ResizableAssociation;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Maybe;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.function.Function;

class ReflectionPropertiesMirror<T> implements PropertiesMirror<T> {

    private final PropertySelector<T> propertySelector;

    ReflectionPropertiesMirror(Class<T> type) {
        this.propertySelector = new PropertySelector<>(type);
    }

    @Override
    public Maybe<Property> get(String name) {
        return propertySelector.getByName(name);
    }

    @Override
    public Maybe<Property> get(Property property) {
        return get(property.name())
                .filter(p -> property.valueType().equals(p.valueType()));
    }

    @Override
    public <P> Maybe<Property> get(Function<T, P> selector) {
        return propertySelector.property(selector);
    }

    @Override
    public Enumerator<Property> enumerator() {
        return this.propertySelector.properties().enumerator();
    }

    @Override
    public boolean isEmpty(){
        return this.propertySelector.properties().isEmpty();
    }
}

class PropertySelector<T> implements InvocationHandler{

    private final T proxy;
    private final Class<T> type;
    private Method selectedMethod;
    private ResizableAssociation<String, Property> propertyAssociation;

    public PropertySelector(Class<T> type) {
        this.type = type;
        this.proxy = Mirror.reflect(type).proxy(this);
    }

    Assortment<Property> properties() {
        return propertiesAssociation().values();
    }

    ResizableAssociation<String, Property> propertiesAssociation() {
        if (propertyAssociation == null){
            propertyAssociation = Association.builder().resizable().empty();
            Sequence.builder().of(type.getDeclaredMethods())
                    .filter(m -> !m.getName().startsWith("set"))
                    .filter(m -> Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()))
                    .map(m -> PropertyMirrorSupport.fromAccessor(m , type).orElse(null))
                    .filter(Objects::nonNull)
                    .associate(Property::name)
                    .forEach(entry -> propertyAssociation.putValue(entry.key(), entry.value()));
        }
        return propertyAssociation;
    }

    @Override
    public Object handleInvocation(Object proxy, Method method, Object[] args)  {
        this.selectedMethod = method;
        return null;
    }

    <P> Maybe<Property> property(Function<T, P> selector){
        selector.apply(proxy);
        if (selectedMethod == null){
            return Maybe.none();
        }
        var name = PropertyMirrorSupport.propertyNameFromAccessor(selectedMethod);

        var possibleProperty = this.propertiesAssociation().getValue(name);
        if (possibleProperty.isPresent()){
            return possibleProperty;
        }

        possibleProperty = PropertyMirrorSupport.fromAccessor(selectedMethod, type);

        possibleProperty.ifPresent(p ->  this.propertiesAssociation().putValue(p.name(), p));

        return possibleProperty;
    }

    public Maybe<Property> getByName(String name) {
        return propertiesAssociation().getValue(name);
    }
}