package PatternHWClasses;

import com.mipt.maksimsafronov.PatternHWClasses.CachingDecorator;
import com.mipt.maksimsafronov.PatternHWClasses.SimpleDataService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CachingDecoratorTest {

    @Test
    void shouldCacheFindResults() {
        SimpleDataService base = new SimpleDataService();
        base.saveData("key", "value");

        CachingDecorator caching = new CachingDecorator(base);

        Optional<String> first = caching.findDataByKey("key");
        assertTrue(first.isPresent());
        assertEquals("value", first.get());

        base.deleteData("key");

        Optional<String> second = caching.findDataByKey("key");
        assertTrue(second.isPresent());
        assertEquals("value", second.get());
    }

    @Test
    void saveShouldUpdateCache() {
        SimpleDataService base = new SimpleDataService();
        CachingDecorator caching = new CachingDecorator(base);

        caching.saveData("key", "value1");
        assertEquals("value1", caching.findDataByKey("key").orElseThrow());

        caching.saveData("key", "value2");
        assertEquals("value2", caching.findDataByKey("key").orElseThrow());
    }

    @Test
    void deleteShouldInvalidateCache() {
        SimpleDataService base = new SimpleDataService();
        CachingDecorator caching = new CachingDecorator(base);

        caching.saveData("key", "value");
        assertTrue(caching.findDataByKey("key").isPresent());

        caching.deleteData("key");
        assertTrue(caching.findDataByKey("key").isEmpty());
    }
}
