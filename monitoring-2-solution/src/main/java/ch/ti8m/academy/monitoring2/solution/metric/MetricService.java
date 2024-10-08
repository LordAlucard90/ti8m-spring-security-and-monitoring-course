package ch.ti8m.academy.monitoring2.solution.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class MetricService {
    private final Random random = new SecureRandom();
    private final Map<String, Boolean> knownPeopleGreeted = new ConcurrentHashMap<>(Map.of(
            "alice", Boolean.FALSE,
            "bob", Boolean.FALSE,
            "charly", Boolean.FALSE,
            "donna", Boolean.FALSE,
            "francis", Boolean.FALSE
    ));
    // Counters used to measure how many time each person has been greeted
    private final Map<String, Counter> greetsCountersMap = new ConcurrentHashMap<>();
    // Timer used to measure how much time has been spent greeting people
    private final Timer greetsTimer = Metrics.timer(MetricName.GREETS_TIME.getMetricName());
    // Downgrading Gauge used to measure how many known people remain to greet
    private final AtomicLong greetsRemainingGauge = Metrics.gauge(
            MetricName.GREETS_REMAINING.getMetricName(),
            new AtomicLong(knownPeopleGreeted.size())
    );

    /*
     * Add a random duration of time spent for greeting a person
     */
    public void addRandomGreetDuration() {
        // generate a random duration
        var seconds = random.nextInt(60) + 1;
        var duration = Duration.of(seconds, ChronoUnit.SECONDS);
        // register new duration in the timer
        greetsTimer.record(duration);
        log.info("Increased greets duration of: {} s", seconds);
    }

    /*
     * Measures how many times each person has been greeted
     */
    public void increaseGreetsCountFor(final String name) {
        // crete new counter for the specific name if necessary
        greetsCountersMap.computeIfAbsent(name, this::createGreetsCounterFor);
        // increase the specific counter
        greetsCountersMap.get(name).increment();
        log.info("Increased greets count for: {}", name);
    }

    private Counter createGreetsCounterFor(final String name) {
        log.info("Created greets counter for: {}", name);
        var tag = Tag.of(MetricTagName.NAME.name(), name);
        return Metrics.counter(
                // name
                MetricName.GREETS_COUNT.getMetricName(),
                // optional list of associated tags
                List.of(tag)
        );
    }

    /*
     * Measure how many people have not yet been greeted
     */
    public void notifyGreetFor(final String name) {
        // update greeted people
        knownPeopleGreeted.computeIfPresent(
                name.toLowerCase(),
                (k, v) -> Boolean.TRUE
        );
        // calculate people still to greet
        var remainingToGreet = knownPeopleGreeted.values()
                .stream()
                .filter(isGreeted -> !isGreeted)
                .count();
        // update gauge
        assert greetsRemainingGauge != null;
        greetsRemainingGauge.set(remainingToGreet);
        log.info("Updated remaining greets to: {} people", remainingToGreet);
    }
}
