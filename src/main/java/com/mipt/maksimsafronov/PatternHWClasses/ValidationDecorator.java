package com.mipt.maksimsafronov.PatternHWClasses;

import java.util.*;

public class ValidationDecorator implements DataService {

    private final DataService delegate;

    public ValidationDecorator(DataService delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        validateKey(key);
        return delegate.findDataByKey(key);
    }

    @Override
    public void saveData(String key, String data) {
        validateKey(key);
        validateData(data);
        delegate.saveData(key, data);
    }

    @Override
    public boolean deleteData(String key) {
        validateKey(key);
        return delegate.deleteData(key);
    }

    private void validateKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Key must not be null or empty");
        }
    }

    private void validateData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null");
        }
    }
}
