package ReflectionHWClasses;

import com.mipt.maksimsafronov.ReflectionHWClasses.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    @Test
    void testValidUser() {
        User user = new User();
        user.setName("Иван");
        user.setEmail("ivan@example.com");
        user.setAge(30);
        user.setPassword("strongpass");

        ValidationResult result = Validator.validate(user);

        assertTrue(result.isValid(), "Ожидается, что объект валиден");
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void testNullFields() {
        User user = new User();
        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Имя не может быть null")));
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Email не может быть null")));
    }

    @Test
    void testInvalidEmailFormat() {
        User user = new User();
        user.setName("Анна");
        user.setEmail("invalid-email");
        user.setAge(25);
        user.setPassword("password123");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Некорректный email")));
    }

    @Test
    void testInvalidNameLength() {
        User user = new User();
        user.setName("A");
        user.setEmail("anna@example.com");
        user.setAge(25);
        user.setPassword("123456");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Имя должно быть от 2 до 50 символов")));
    }

    @Test
    void testRangeOutOfBounds() {
        User user = new User();
        user.setName("Иван");
        user.setEmail("ivan@example.com");
        user.setAge(200);
        user.setPassword("password");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Возраст должен быть от 0 до 150")));
    }

    @Test
    void testPasswordTooShort() {
        User user = new User();
        user.setName("Иван");
        user.setEmail("ivan@example.com");
        user.setAge(30);
        user.setPassword("123");

        ValidationResult result = Validator.validate(user);

        assertFalse(result.isValid());
        assertTrue(result.getErrors().stream().anyMatch(e -> e.contains("Пароль должен быть от 6 до 20 символов")));
    }

    @Test
    void testBoundaryValues() {
        User user = new User();
        user.setName("AB");
        user.setEmail("a@b.com");
        user.setAge(0);
        user.setPassword("123456");

        ValidationResult result = Validator.validate(user);

        assertTrue(result.isValid(), "Граничные значения должны быть допустимыми");
    }
}
