package com.mipt.maksimsafronov.ReflectionHWClasses;

import java.lang.annotation.*;
import java.util.*;


public class ValidationResult {
    private boolean isValid = true;
    private final List<String> errors = new ArrayList<>();

    public void addError(String error) {
        isValid = false;
        errors.add(error);
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getErrors() {
        return errors;
    }
}
