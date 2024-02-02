package org.midheaven.math;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class RandomGeneratorBuilder implements AvailableRandomGenerators{

    final Supplier<Random> source;

    RandomGeneratorBuilder(Supplier<Random> source){
        this.source = source;
    }

    public BooleansRandomGenerator booleans(){
        return new BooleansRandomGenerator(source);
    }

    public BytesRandomGenerator bytes(){
        return new BytesRandomGenerator(source);
    }

    public UuidsRandomGenerator uuids(){
        return new UuidsRandomGenerator(source);
    }

    public LongsRandomGenerator longs(){
        return new LongsRandomGenerator(source);
    }

    public DoublesRandomGenerator doubles(){
        return new DoublesRandomGenerator(source);
    }

    public StringRandomGeneratorBuilder strings() {
        return new StringRandomGeneratorBuilder(this);
    }

    public BigIntegersRandomGenerator bigIntegers(){
        return new BigIntegersRandomGenerator(source);
    }

    public BigDecimalsRandomGenerator bigDecimals(){
        return new BigDecimalsRandomGenerator(source);
    }

    public LocalDatesRandomGenerator localDates(){
        return new LocalDatesRandomGenerator(source);
    }

    public IntegersRandomGenerator integers(){
        return new IntegersRandomGenerator(source);
    }

    public <T> RandomGenerator<T> generate(Function<AvailableRandomGenerators, T> generator) {
        return () -> generator.apply(RandomGeneratorBuilder.this);
    }

}
