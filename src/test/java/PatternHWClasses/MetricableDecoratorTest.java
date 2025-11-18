package PatternHWClasses;

import com.mipt.maksimsafronov.PatternHWClasses.MetricableDecorator;
import com.mipt.maksimsafronov.PatternHWClasses.SimpleDataService;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricableDecoratorTest {

    static class TestMetricService extends MetricableDecorator.MetricService {
        List<Duration> durations = new ArrayList<>();

        @Override
        public void sendMetric(Duration duration) {
            durations.add(duration);
        }
    }

    @Test
    void shouldSendMetricForEachMethod() {
        SimpleDataService base = new SimpleDataService();
        TestMetricService metricService = new TestMetricService();
        MetricableDecorator metricable = new MetricableDecorator(base, metricService);

        metricable.saveData("key", "value");
        metricable.findDataByKey("key");
        metricable.deleteData("key");

        assertEquals(3, metricService.durations.size());
        assertTrue(metricService.durations.stream().noneMatch(Duration::isNegative));
    }
}
