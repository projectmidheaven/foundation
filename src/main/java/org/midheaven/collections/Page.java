package org.midheaven.collections;

import java.util.function.Function;

/**
 * Defines the contract for Page.
 */
public interface Page<T>{

    /**
     * @return an empty Page.
     */
    static <S> Page<S> empty(){
        return  EmptyPage.INSTANCE;
    }
    
    /**
     * Creates an instance from the provided source.
     * @param ordinal the ordinal value
     * @param totalPagesCount the totalPagesCount value
     * @param totalItemsCount the totalItemsCount value
     * @param maximumItemsPerPageCount the maximumItemsPerPageCount value
     * @param items the items value
     * @return the result of from
     */
    static <S> Page<S> from(
        int ordinal,
        int totalPagesCount,
        int totalItemsCount,
        int maximumItemsPerPageCount,
        Sequence<S> items
    ){
        return new ItemsPage<>(
             ordinal,
             totalPagesCount,
             totalItemsCount,
             maximumItemsPerPageCount,
             items
        );
    }
    
    /**
     * Performs ordinal.
     * @return the result of ordinal
     */
    int ordinal();
    /**
     * Returns total Pages Count.
     * @return the result of totalPagesCount
     */
    int totalPagesCount();
    /**
     * Returns total Items Count.
     * @return the result of totalItemsCount
     */
    int totalItemsCount();
    /**
     * Performs maximumItemsPerPageCount.
     * @return the result of maximumItemsPerPageCount
     */
    int maximumItemsPerPageCount();
    
    /**
     * Performs items.
     * @return the result of items
     */
    Sequence<T> items();
    
    /**
     * Performs map.
     * @param mapper the mapper value
     * @return the result of map
     */
    default <R> Page<R> map(Function<T, R> mapper){
        return new MappedPage<>(this, mapper);
    }
}


class EmptyPage<T> implements Page<T>{
    
    static final EmptyPage INSTANCE = new EmptyPage();
    
    private EmptyPage(){}
    
    @Override
    public int ordinal() {
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
    public int maximumItemsPerPageCount() {
        return 0;
    }
    
    @Override
    public Sequence<T> items() {
        return Sequence.builder().empty();
    }
    
    @Override
    public <R> Page<R> map(Function<T, R> mapper) {
        return INSTANCE;
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
    public int ordinal() {
        return original.ordinal();
    }

    @Override
    public int totalPagesCount() {
        return original.totalPagesCount();
    }

    @Override
    public int totalItemsCount() {
        return original.totalItemsCount();
    }
    
    @Override
    public int maximumItemsPerPageCount() {
        return original.maximumItemsPerPageCount();
    }
    
    @Override
    public Sequence<T> items() {
        return original.items().map(mapper);
    }
    
    public <R> Page<R> map(Function<T, R> mapper) {
        return new MappedPage<>(this.original, this.mapper.andThen(mapper));
    }

}

record ItemsPage<T>(int ordinal, int totalPagesCount, int totalItemsCount, int maximumItemsPerPageCount, Sequence<T> items) implements Page<T>{

}
