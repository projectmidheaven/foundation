package org.midheaven.math;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

public class RandomGeneratorProvider implements AvailableRandomGenerators{

    public static RandomGeneratorProvider from(Random random){
        return new RandomGeneratorProvider(() ->random);
    }

    public static RandomGeneratorProvider standard(){
        return new RandomGeneratorProvider(ThreadLocalRandom::current);
    }

    public static RandomGeneratorProvider secure(){
        return from(new SecureRandom());
    }

    public static RandomGeneratorProvider seedable(long seed){
        return from(new Random(seed));
    }

    final Supplier<Random> source;

    RandomGeneratorProvider(Supplier<Random> source){
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

    public RationalRandomGenerator rationals(){
        return new RationalRandomGenerator(source);
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
        return () -> generator.apply(RandomGeneratorProvider.this);
    }

}
