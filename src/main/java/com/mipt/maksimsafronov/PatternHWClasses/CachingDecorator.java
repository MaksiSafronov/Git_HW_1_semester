package com.mipt.maksimsafronov.PatternHWClasses;

import java.util.*;

public class CachingDecorator implements DataService {

    private final DataService delegate;
    private final Map<String, Optional<String>> cache = new HashMap<>();

    public CachingDecorator(DataService delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        Optional<String> result = delegate.findDataByKey(key);

        cache.put(key, result);
        return result;
    }

    @Override
    public void saveData(String key, String data) {
        delegate.saveData(key, data);
        cache.put(key, Optional.ofNullable(data));
    }

    @Override
    public boolean deleteData(String key) {
        boolean deleted = delegate.deleteData(key);
        cache.remove(key);
        return deleted;
    }
}


