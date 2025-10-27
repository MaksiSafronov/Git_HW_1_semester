package com.mipt.maksimsafronov.ReflectionHWClasses;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {
    public static ValidationResult validate(Object object) {
        ValidationResult result = new ValidationResult();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
            }  catch (IllegalAccessException e) {
                throw new RuntimeException("Ошибка при доступе к полю " + field.getName(), e);
            }

            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                if (value == null) {
                    result.addError(annotation.message());
                    continue;
                }
            }

            if (field.isAnnotationPresent(Size.class) && value != null) {
                Size annotation = field.getAnnotation(Size.class);
                String str = value.toString();
                if (str.length() < annotation.min() || str.length() > annotation.max()) {
                    result.addError(annotation.message());
                }
            }

            if (field.isAnnotationPresent(Range.class) && value != null) {
                Range annotation = field.getAnnotation(Range.class);
                int num = (Integer) value;
                if (num < annotation.min() || num > annotation.max()) {
                    result.addError(annotation.message());
                }
            }

            if (field.isAnnotationPresent(Email.class) && value != null) {
                Email annotation = field.getAnnotation(Email.class);
                String email = value.toString();
                if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
                    result.addError(annotation.message());
                }
            }
        }
        return result;
    }
}
