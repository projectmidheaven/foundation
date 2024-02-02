package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

public class StringRandomGenerator implements RandomGenerator<String> {

    private final Supplier<Random> source;
    private final StringType type;
    private final int minLength;
    private final int maxLength;

    StringRandomGenerator(Supplier<Random> source, int minLength, int maxLength, StringType type) {
        this.source = source;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.type = type;
    }

    @Override
    public String next() {
        var base = source.get();
        var length = base.nextInt(minLength, maxLength + 1);

        var builder = new StringBuilder(length);
        var block  = type.block();
        var blockLength = block.length();

        for (int i = 0; i < length; i++){
            builder.append(block.charAt(base.nextInt(blockLength)));
        }

        return builder.toString();
    }
}
