package org.midheaven.math;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provider for Random Generator instances.
 */
public class RandomGeneratorProvider implements AvailableRandomGenerators{

    /**
     * Creates an instance from the provided source.
     * @param random the random value
     * @return the result of from
     */
    public static RandomGeneratorProvider from(Random random){
        return new RandomGeneratorProvider(() ->random);
    }

    /**
     * Performs standard.
     * @return the result of standard
     */
    public static RandomGeneratorProvider standard(){
        return new RandomGeneratorProvider(ThreadLocalRandom::current);
    }

    /**
     * Performs secure.
     * @return the result of secure
     */
    public static RandomGeneratorProvider secure(){
        return from(new SecureRandom());
    }

    /**
     * Performs seedable.
     * @param seed the seed value
     * @return the result of seedable
     */
    public static RandomGeneratorProvider seedable(long seed){
        return from(new Random(seed));
    }

    final Supplier<Random> source;

    RandomGeneratorProvider(Supplier<Random> source){
        this.source = source;
    }

    /**
     * Performs booleans.
     * @return the result of booleans
     */
    public BooleansRandomGenerator booleans(){
        return new BooleansRandomGenerator(source);
    }

    /**
     * Performs bytes.
     * @return the result of bytes
     */
    public BytesRandomGenerator bytes(){
        return new BytesRandomGenerator(source);
    }

    /**
     * Performs uuids.
     * @return the result of uuids
     */
    public UuidsRandomGenerator uuids(){
        return new UuidsRandomGenerator(source);
    }

    /**
     * Performs longs.
     * @return the result of longs
     */
    public LongsRandomGenerator longs(){
        return new LongsRandomGenerator(source);
    }

    /**
     * Performs doubles.
     * @return the result of doubles
     */
    public DoublesRandomGenerator doubles(){
        return new DoublesRandomGenerator(source);
    }

    /**
     * Performs rationals.
     * @return the result of rationals
     */
    public RationalRandomGenerator rationals(){
        return new RationalRandomGenerator(source);
    }

    /**
     * Performs strings.
     * @return the result of strings
     */
    public StringRandomGeneratorBuilder strings() {
        return new StringRandomGeneratorBuilder(this);
    }

    /**
     * Performs bigIntegers.
     * @return the result of bigIntegers
     */
    public BigIntegersRandomGenerator bigIntegers(){
        return new BigIntegersRandomGenerator(source);
    }

    /**
     * Performs bigDecimals.
     * @return the result of bigDecimals
     */
    public BigDecimalsRandomGenerator bigDecimals(){
        return new BigDecimalsRandomGenerator(source);
    }

    /**
     * Performs localDates.
     * @return the result of localDates
     */
    public LocalDatesRandomGenerator localDates(){
        return new LocalDatesRandomGenerator(source);
    }

    /**
     * Performs integers.
     * @return the result of integers
     */
    public IntegersRandomGenerator integers(){
        return new IntegersRandomGenerator(source);
    }

    /**
     * Performs generate.
     * @param generator the generator value
     * @return the result of generate
     */
    public <T> RandomGenerator<T> generate(Function<AvailableRandomGenerators, T> generator) {
        return () -> generator.apply(RandomGeneratorProvider.this);
    }

}
