package org.midheaven.lang;

import org.midheaven.collections.Sequence;

final class ForwardShiftSplitter extends AbstractIndexedSplitter {
    
    private final AbstractIndexedSplitter original;
    
    public ForwardShiftSplitter(AbstractIndexedSplitter original) {
        this.original = original;
    }
    
    @Override
    protected int length() {
        return original.length() - 1;
    }
    
    @Override
    protected String secureGetAt(int index) {
        return original.secureGetAt(index + 1);
    }
    
    @Nullable
    @Override
    public Sequence<String> sequence() {
        return original.sequence().skip(1).toSequence();
    }
}
