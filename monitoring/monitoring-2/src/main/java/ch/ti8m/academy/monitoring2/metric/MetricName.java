package ch.ti8m.academy.monitoring2.metric;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetricName {
    GREETS_COUNT("greets.people.count"),
    GREETS_TIME("greets.time"),
    GREETS_REMAINING("greets.remaining");

    @Getter
    private final String metricName;
}
