package org.midheaven.io;

import java.util.concurrent.locks.ReentrantLock;

abstract class AbstractReadOnceByteContent extends ByteContent{
    
    protected AbstractReadOnceByteContent(ByteContentFormat format) {
        super(format);
    }
    
    ByteContent loadedContent;
    ReentrantLock lock = new ReentrantLock();
    
    private ByteContent load(){
        if (loadedContent == null){
            lock.lock();
            try {
                var bytes = loadAllBytes();
                loadedContent = new ArrayByteContent(bytes, format());
            } finally {
                lock.unlock();
            }
        }
        return loadedContent;
    }
    
    protected abstract byte[] loadAllBytes();
    
    @Override
    public byte[] readAllBytes() {
        return load().readAllBytes();
    }
    
    @Override
    public boolean isEmpty() {
        return load().isEmpty();
    }
    
    @Override
    public ByteContentSize size() {
        return load().size();
    }
}
