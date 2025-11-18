package com.mipt.maksimsafronov.PatternHWClasses;

import java.util.*;

public class LoggingDecorator implements DataService {

    private final DataService delegate;

    public LoggingDecorator(DataService delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        System.out.println("[LOG] findDataByKey(" + key + ")");
        Optional<String> result = delegate.findDataByKey(key);
        System.out.println("[LOG] findDataByKey(" + key + ") -> " + result);
        return result;
    }

    @Override
    public void saveData(String key, String data) {
        System.out.println("[LOG] saveData(" + key + ", " + data + ")");
        delegate.saveData(key, data);
        System.out.println("[LOG] saveData(" + key + ", " + data + ") done");
    }

    @Override
    public boolean deleteData(String key) {
        System.out.println("[LOG] deleteData(" + key + ")");
        boolean result = delegate.deleteData(key);
        System.out.println("[LOG] deleteData(" + key + ") -> " + result);
        return result;
    }
}

