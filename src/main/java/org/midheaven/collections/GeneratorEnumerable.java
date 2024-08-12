package org.midheaven.collections;

final class GeneratorEnumerable<T, S> implements Enumerable<T>{

    private final Pipe<T, T, S> pipe;

    GeneratorEnumerable(Pipe<T, T, S> pipe){
        this.pipe = pipe;
    }

    @Override
    public Enumerator<T> enumerator() {
        return new Enumerator<>(){
            private PipeMoveResult<T> move = PipeMoveResult.notMoved();
            S state = pipe.newState(null,this.length());

            @Override
            public boolean moveNext() {
                this.move = pipe.move(null, state);
                return move.wasSuccessful();
            }

            @Override
            public T current() {
                return move.get();
            }

            @Override
            public Length length() {
                return Length.Infinite.INSTANCE;
            }
        };
    }
}
