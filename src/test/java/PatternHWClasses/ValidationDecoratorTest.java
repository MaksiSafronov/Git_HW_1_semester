package PatternHWClasses;

import com.mipt.maksimsafronov.PatternHWClasses.SimpleDataService;
import com.mipt.maksimsafronov.PatternHWClasses.ValidationDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationDecoratorTest {

    @Test
    void shouldDelegateWhenValid() {
        SimpleDataService base = new SimpleDataService();
        ValidationDecorator validation = new ValidationDecorator(base);

        validation.saveData("key", "value");
        assertEquals("value", validation.findDataByKey("key").orElseThrow());
        assertTrue(validation.deleteData("key"));
    }

    @Test
    void shouldThrowOnInvalidKey() {
        SimpleDataService base = new SimpleDataService();
        ValidationDecorator validation = new ValidationDecorator(base);

        assertThrows(IllegalArgumentException.class, () -> validation.findDataByKey(null));
        assertThrows(IllegalArgumentException.class, () -> validation.findDataByKey("   "));
        assertThrows(IllegalArgumentException.class, () -> validation.deleteData(""));
    }

    @Test
    void shouldThrowOnInvalidData() {
        SimpleDataService base = new SimpleDataService();
        ValidationDecorator validation = new ValidationDecorator(base);

        assertThrows(IllegalArgumentException.class, () -> validation.saveData("key", null));
    }
}
