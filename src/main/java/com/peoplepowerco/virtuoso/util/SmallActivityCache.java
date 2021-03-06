package com.peoplepowerco.virtuoso.util;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class SmallActivityCache<T> extends LinkedHashMap<Long, SoftReference<T>> {

    private static int MAXIMUM_CACHE_SIZE = 5;
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean removeEldestEntry(Entry<Long, SoftReference<T>> eldest) {
        return (size() > MAXIMUM_CACHE_SIZE);
    }

    public long put(T value) {
        long key = System.currentTimeMillis();
        super.put(key, new SoftReference<T>(value));
        return key;
    }

    public long put(long key, T value) {
        super.put(key, new SoftReference<T>(value));
        return key;
    }

    public T get(Long key) {
        if (containsKey(key)) {
            SoftReference<T> softRef = super.get(key);
            T value = softRef.get();
            if (value != null) {
                // push this value to the front
                remove(key);
                super.put(key, softRef);
            }
            return value;
        }

        return null;
    }

}