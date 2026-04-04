package org.midheaven.lang;

import org.midheaven.collections.DistinctAssortment;
import org.midheaven.collections.Enumerable;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Defines the contract for Maybe.
 */
public sealed interface Maybe<T> permits Some, None{

    /**
     * Performs none.
     * @return the result of none
     */
    @SuppressWarnings("unchecked")
    /**
     * Performs none.
     * @return the result of none
     */
    static <X> @NotNullable Maybe<X> none(){
        return None.INSTANCE;
    }

    /**
     * Creates an instance from the provided value.
     * @param value the value value
     * @return the result of of
     */
    static <X> @NotNullable Maybe<X> of(X value){
        if (value == null){
            return none();
        }
        return new Some<>(value);
    }

    /**
     * Performs some.
     * @param value the value value
     * @return the result of some
     */
    static <X> @NotNullable Maybe<X> some(X value){
        Objects.requireNonNull(value);
        return new Some<>(value);
    }

    /**
     * Creates an instance from the provided source.
     * @param optional the optional value
     * @return the result of from
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    /**
     * Creates an instance from the provided source.
     * @param optional the optional value
     * @return the result of from
     */
    static <X> @NotNullable Maybe<X> from(Optional<X> optional){
        if (optional.isPresent()){
            return some(optional.orElseThrow());
        }
        return none();
    }
    
    @NotNullable Enumerable<T> enumerable();

    @NotNullable T get();

    /**
     * Checks whether is Present.
     * @return the result of isPresent
     */
    boolean isPresent();
    /**
     * Checks whether is Absent.
     * @return the result of isAbsent
     */
    boolean isAbsent();
    
    /**
     * Transforms the value T to type R. If the value is not of type T return {@link Maybe#none()}
     * @param type
     * @return
     * @param <R>
     */
    @NotNullable <R> Maybe<R> ofType(Class<R> type);

    @NotNullable Maybe<T> filter(@NotNullable Predicate<T> predicate);
    @NotNullable <R> Maybe<R> map(@NotNullable Function<T, R> transform);
    @NotNullable <R> Maybe<R> flatMap(@NotNullable Function<T, Maybe<R>> transform);

    @Nullable T orNull();
    @NotNullable T orElseThrow();
    <E extends Throwable> @NotNullable T orElseThrow(Supplier<@NotNullable E> supplier) throws E;
    @NotNullable T orElse(@NotNullable T defaultValue);
    @Nullable T orElseGet(@NotNullable Supplier<@Nullable T> supplier);
    @NotNullable Maybe<T> or(@NotNullable Maybe<T> alternative);
    @NotNullable Maybe<T> orGet(@NotNullable Supplier<@NotNullable Maybe<T>> alternative);
    @NotNullable <R,S> Maybe<R> zip(@NotNullable Maybe<S> other,@NotNullable  BiFunction<T, S, R> calculation);
    @NotNullable
    <R,S> Maybe<R> flatZip(@NotNullable Maybe<S> other,@NotNullable BiFunction<T, S, Maybe<R>> calculation);
    @NotNullable Maybe<T> merge(@NotNullable Maybe<T> other, @NotNullable BiFunction<T, T, T> calculation);
    @NotNullable Maybe<T> flatMerge(@NotNullable Maybe<T> other, @NotNullable BiFunction<T, T, Maybe<T>> calculation);

    /**
     * Performs ifPresent.
     */
    @NotNullable Optional<T> toOptional();
    /**
     * Performs ifPresent.
     * @param consumer the consumer value
     */
    void ifPresent(@NotNullable Consumer<T> consumer);
    /**
     * Performs ifPresentOrElse.
     * @param consumer the consumer value
     * @param action the action value
     */
    void ifPresentOrElse(@NotNullable Consumer<T> consumer, @NotNullable Runnable action);
}


record Some<T>(T value) implements Maybe<T> {

    @Override
    public Enumerable<T> enumerable() {
        return DistinctAssortment.builder().of(value);
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isAbsent() {
        return false;
    }
    
    @Override
    public @NotNullable <R> Maybe<R> ofType(Class<R> type) {
        if (type.isInstance(value)){
            return new Some<R>(type.cast(value));
        }
        return Maybe.none();
    }
    
    @Override
    public Maybe<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        if (predicate.test(value)){
            return this;
        }
        return Maybe.none();
    }

    @Override
    public <R> Maybe<R> map(Function<T, R> transform) {
        Objects.requireNonNull(transform);
        var result = transform.apply(value);
        if (result == null){
            return Maybe.none();
        }
        return Maybe.some(result);
    }

    @Override
    public <R> Maybe<R> flatMap(Function<T, Maybe<R>> transform) {
        Objects.requireNonNull(transform);
        return transform.apply(value);
    }

    @Override
    public T orNull() {
        return value;
    }

    @Override
    public T orElseThrow() {
        return value;
    }

    @Override
    public <E extends Throwable> T orElseThrow(Supplier<E> supplier) throws E {
        return value;
    }

    @Override
    public T orElse(T defaultValue) {
        return value;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        return value;
    }

    @Override
    public Maybe<T> or(Maybe<T> alternative) {
        return this;
    }

    @Override
    public Maybe<T> orGet(Supplier<Maybe<T>> alternative) {
        return this;
    }


    @Override
    public <R, S> Maybe<R> zip(Maybe<S> other, BiFunction<T, S, R> calculation) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(calculation);
        return other.flatMap(b -> Maybe.of(calculation.apply(value, b)));
    }

    @Override
    public <R, S> Maybe<R> flatZip(Maybe<S> other, BiFunction<T, S, Maybe<R>> calculation) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(calculation);
        return other.flatMap(b -> calculation.apply(value, b));
    }

    @Override
    public Maybe<T> merge(Maybe<T> other, BiFunction<T, T, T> calculation) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(calculation);
        return other.map(b -> calculation.apply(value, b)).or(this);
    }

    @Override
    public Maybe<T> flatMerge(Maybe<T> other, BiFunction<T, T, Maybe<T>> calculation) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(calculation);
        return other.flatMap(b -> calculation.apply(value, b)).or(this);
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        consumer.accept(value);
    }

    @Override
    public void ifPresentOrElse(Consumer<T> consumer, Runnable action) {
        Objects.requireNonNull(consumer);
        consumer.accept(value);
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Some<?>(Object otherValue)
                && this.value.equals(otherValue);
    }

    @Override
    public String toString(){
        return "Some[" + value + "]";
    }
}

record None<T>() implements Maybe<T> {

    @SuppressWarnings("rawtypes")
    static final None INSTANCE= new None();

    @Override
    public Enumerable<T> enumerable() {
        return Enumerable.empty();
    }

    @Override
    public T get() {
       return orElseThrow();
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public boolean isAbsent() {
        return true;
    }
    
    @Override
    public @NotNullable <R> Maybe<R> ofType(Class<R> type) {
        return Maybe.none();
    }
    
    @Override
    public Maybe<T> filter(Predicate<T> predicate) {
        return Maybe.none();
    }

    @Override
    public <R> Maybe<R> map(Function<T, R> transform) {
        return Maybe.none();
    }

    @Override
    public <R> Maybe<R> flatMap(Function<T, Maybe<R>> transform) {
        return Maybe.none();
    }

    @Override
    public T orNull() {
        return null;
    }

    @Override
    public T orElseThrow() {
        throw new NoSuchElementException("None contains no elements");
    }

    @Override
    public <E extends Throwable> T orElseThrow(Supplier<E> supplier) throws E {
        Objects.requireNonNull(supplier);
        throw supplier.get();
    }

    @Override
    public T orElse(T defaultValue) {
        if (defaultValue == null){
            throw new IllegalArgumentException("orElse does not receive null. Use orNull instead");
        }
        return defaultValue;
    }

    @Override
    public T orElseGet(Supplier<T> supplier) {
        Check.argumentIsNotNull(supplier, "supplier");
        return supplier.get();
    }

    @Override
    public Maybe<T> or(Maybe<T> alternative) {
        Check.argumentIsNotNull(alternative, "alternative");
        return alternative;
    }

    @Override
    public Maybe<T> orGet(Supplier<Maybe<T>> alternative) {
        Check.argumentIsNotNull(alternative, "alternative");
        return alternative.get();
    }

    @Override
    public <R, S> Maybe<R> zip(Maybe<S> other, BiFunction<T, S, R> calculation) {
        return Maybe.none();
    }

    @Override
    public <R, S> Maybe<R> flatZip(Maybe<S> other, BiFunction<T, S, Maybe<R>> calculation) {
        return Maybe.none();
    }

    @Override
    public Maybe<T> merge(Maybe<T> other, BiFunction<T, T, T> calculation) {
        Objects.requireNonNull(other);
        return other;
    }

    @Override
    public Maybe<T> flatMerge(Maybe<T> other, BiFunction<T, T, Maybe<T>> calculation) {
        Objects.requireNonNull(other);
        return other;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public void ifPresent(Consumer<T> consumer) {
        //NO-OP
    }

    @Override
    public void ifPresentOrElse(Consumer<T> consumer, Runnable action) {
        Check.argumentIsNotNull(action);
        action.run();
    }

    @Override
    public int hashCode(){
        return 0;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof None<?>;
    }

    @Override
    public String toString(){
        return "Maybe.none";
    }

}
