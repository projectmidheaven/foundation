package org.midheaven.collections;

import java.util.function.Function;

public interface Page<T> {

    static <S> Page<S> empty(){
        return new EmptyPage<>();
    }

    int number();
    int totalPagesCount();
    int totalItemsCount();

    default <R> Page<R> map(Function<T, R> mapper){
        return new MappedPage<>(this, mapper);
    }
}


class EmptyPage<T> implements Page<T>{

    @Override
    public int number() {
        return 1;
    }

    @Override
    public int totalPagesCount() {
        return 1;
    }

    @Override
    public int totalItemsCount() {
        return 0;
    }

    @Override
    public <R> Page<R> map(Function<T, R> mapper) {
        return new EmptyPage<>();
    }
}

class MappedPage<T,O> implements Page<T> {

    private final Page<O> original;
    private final Function<O, T> mapper;

    MappedPage(Page<O> original, Function<O, T> mapper){
        this.original = original;
        this.mapper = mapper;
    }

    @Override
    public int number() {
        return original.number();
    }

    @Override
    public int totalPagesCount() {
        return original.totalPagesCount();
    }

    @Override
    public int totalItemsCount() {
        return original.totalItemsCount();
    }

    public <R> Page<R> map(Function<T, R> mapper) {
        return new MappedPage<>(this.original, this.mapper.andThen(mapper));
    }

}