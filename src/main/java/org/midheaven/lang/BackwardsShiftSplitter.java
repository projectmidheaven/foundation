package org.midheaven.lang;

import org.midheaven.collections.Sequence;

final class BackwardsShiftSplitter extends AbstractIndexedSplitter {
    
    private final AbstractIndexedSplitter original;
    
    public BackwardsShiftSplitter(AbstractIndexedSplitter original) {
        this.original = original;
    }
    
    @Override
    protected int length() {
        return original.length() - 1;
    }
    
    @Override
    protected String secureGetAt(int index) {
        return original.secureGetAt(index);
    }
    
    @Nullable
    @Override
    public Sequence<String> sequence() {
        return original.sequence().limit(original.length() - 1).toSequence();
    }
}
