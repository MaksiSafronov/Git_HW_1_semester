package IOHWClasses;

import com.mipt.maksimsafronov.IOHWClasses.FileProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @Test
    void testSplitAndMergeFile() throws IOException {
        FileProcessor processor = new FileProcessor();

        Path testFile = Files.createTempFile("test", ".dat");
        byte[] testData = new byte[1500]; // 1.5KB данных
        new Random().nextBytes(testData);
        Files.write(testFile, testData);

        String outputDir = Files.createTempDirectory("parts").toString();
        List<Path> parts = processor.splitFile(testFile.toString(), outputDir, 500);

        assertEquals(3, parts.size(), "Ожидалось 3 части при разбиении 1500 байт на блоки по 500 байт");

        for (int i = 0; i < parts.size(); i++) {
            Path part = parts.get(i);
            assertTrue(Files.exists(part), "Часть " + (i + 1) + " должна существовать");
            long expectedSize = 500; // Все три части по 500 байт (1500 / 500 = 3 ровно)
            assertEquals(expectedSize, Files.size(part), "Размер части " + (i + 1) + " должен быть " + expectedSize + " байт");
        }

        Path mergedFile = Files.createTempFile("merged", ".dat");
        processor.mergeFiles(parts, mergedFile.toString());

        assertArrayEquals(Files.readAllBytes(testFile), Files.readAllBytes(mergedFile));
    }
}
