package org.midheaven.lang;

import org.midheaven.collections.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

final class ListSplitter extends AbstractIndexedSplitter {
    
    private final List<String> list = new ArrayList<>(); // can be empty
    
    @Override
    protected String secureGetAt(int index) {
        return list.get(index);
    }
    
    @Override
    public <A> String collect(Collector<CharSequence, A, String> collector) {
        return list.stream().collect(collector);
    }
    
    @Nullable
    @Override
    public Sequence<String> sequence() {
        return Sequence.builder().from(list);
    }
    
    @Override
    public int length() {
        return list.size();
    }
    
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    void add(String word) {
        list.add(word);
    }
}
