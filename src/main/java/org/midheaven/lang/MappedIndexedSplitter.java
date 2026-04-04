package org.midheaven.lang;

import org.midheaven.collections.Sequence;

import java.util.function.Function;

/**
 * Represents Mapped Indexed Splitter.
 */
final class MappedIndexedSplitter extends AbstractIndexedSplitter {
    
    private final Function<String, String> transform;
    private final AbstractIndexedSplitter original;
    
    public MappedIndexedSplitter(AbstractIndexedSplitter original, Function<String, String> transform) {
        this.original = original;
        this.transform = transform;
    }
    
    @Override
    protected int length() {
        return original.length();
    }
    
    @Override
    protected String secureGetAt(int index) {
        return transform.apply(original.secureGetAt(index));
    }
    
    @Nullable
    @Override
    public Sequence<String> sequence() {
        return original.sequence().map(transform);
    }
    
    @Override
    public Strings.Splitter map(Function<String, String> transform) {
        return new MappedIndexedSplitter(original, this.transform.andThen(transform));
    }
}
