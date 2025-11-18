package PatternHWClasses;

import com.mipt.maksimsafronov.PatternHWClasses.LoggingDecorator;
import com.mipt.maksimsafronov.PatternHWClasses.SimpleDataService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoggingDecoratorTest {

    @Test
    void shouldLogAndDelegateMethods() {
        SimpleDataService base = new SimpleDataService();
        LoggingDecorator logging = new LoggingDecorator(base);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            logging.saveData("key", "value");
            Optional<String> data = logging.findDataByKey("key");
            boolean deleted = logging.deleteData("key");

            assertEquals("value", data.orElseThrow());
            assertTrue(deleted);

            String log = outContent.toString();
            assertTrue(log.contains("saveData("));
            assertTrue(log.contains("findDataByKey("));
            assertTrue(log.contains("deleteData("));
        } finally {
            System.setOut(originalOut);
        }
    }
}

