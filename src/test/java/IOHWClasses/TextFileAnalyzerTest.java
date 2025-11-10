package IOHWClasses;

import com.mipt.maksimsafronov.IOHWClasses.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextFileAnalyzerTest {

    @Test
    void testAnalyzeFile() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();

        // Создаем временный тестовый файл
        Path testFile = Files.createTempFile("test", ".txt");
        Files.write(testFile, Arrays.asList("Hello world!", "This is test."));

        TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());

        // TODO: пропиши тут все проверки на `result`
        assertEquals(2, result.getLineCount(), "Количество строк должно быть 2");
        assertEquals(5, result.getWordCount(), "Количество слов должно юыть 5");
        assertEquals(25, result.getCharCount(), "Количество слов должно быть 25");

        Files.deleteIfExists(testFile);
    }

    @Test
    void testSaveAnalysisResult() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();

        // Создаем тестовый результат
        TextFileAnalyzer.AnalysisResult result = new TextFileAnalyzer.AnalysisResult(2, 5, 20);

        // Сохранить в файл
        Path outputFile = Files.createTempFile("analysis", ".txt");
        analyzer.saveAnalysisResult(result, outputFile.toString());

        assertTrue(Files.exists(outputFile), "Файл должен существовать");
        assertTrue(Files.size(outputFile) > 0, "Размер файла должен быть больше 0");

        List<String> lines = Files.readAllLines(outputFile);
        String fileContent = String.join("\n", lines).trim();

        String expected = result.toString();
        assertEquals(expected, fileContent, "Содержимое файла должно совпадать с toString() результата");

        Files.deleteIfExists(outputFile);
    }
}
