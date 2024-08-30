package ch.ti8m.academy.monitoring2.metric;

import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
    // TODO: create a timer using the Metrics class, with:
    //  - name: MetricName.GREETS_TIME
    //  - tag: no tag
    // private final Timer greetsTimer = ...
    // TODO: create a gauge using the Metrics class, with:
    //  - name: MetricName.GREETS_REMAINING
    //  - tag: no tag
    //  - number: AtomicLong (initial value: number of knownPeople)
    // private final AtomicLong greetsRemainingGauge = ...

    /*
     * Add a random duration of time spent for greeting a person
     */
    public void addRandomGreetDuration() {
        // generate a random duration
        var seconds = random.nextInt(60) + 1;
        // TODO: increment the timer of the given duration (seconds)
        // greetsTimer....
        log.info("Increased greets duration of: {} s", seconds);
    }

    /*
     * Measures how many times each person has been greeted
     */
    public void increaseGreetsCountFor(final String name) {
        // crete new counter for the specific name if necessary
        greetsCountersMap.computeIfAbsent(name, this::createGreetsCounterFor);
        // TODO: increment the counter of one
        // greetsCountersMap.get(name)....
        log.info("Increased greets count for: {}", name);
    }

    private Counter createGreetsCounterFor(final String name) {
        log.info("Created greets counter for: {}", name);
        // TODO: create a Tag with
        //  - key: MetricTagName.NAME
        //  - value: the person name
        // var tag = ...
        // TODO: create a Counter using the Metrics class, with
        //  - name: MetricName.GREETS_COUNT
        //  - tag: a list with the previous created tag
        return null;
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
        // TODO: update the gauge value
        // greetsRemainingGauge....
        log.info("Updated remaining greets to: {} people", remainingToGreet);
    }
}
