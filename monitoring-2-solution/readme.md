# Monitoring - 2 - Solution

## Exercise

### Timer
Create a Timer that counts the total time spent on greeting.\
Every time a person is greeted add the time spent to greet him.

### Counter
Create a dynamic Counter for each person that counts
how many times the person has been greeted.\
Each counter must have a tag that refers to the specific person.\
Increment the person's counter whenever the person is greeted.

### Gauge
Create a Gauge that count downwards the known people that have not yet been greeted.\
The gauge must be initialized with the total number of known persons
and each time a person is greeted the gouge must be updated.

### Optional
Register the metrics in the Spring Boot Admin dashboard.
(remember that the endpoint must be called first)

### Links
- [promethus](http://localhost:8302/monitoring/actuator/prometheus)
- [Admin dashboard](http://localhost:8302/monitoring/wallboard)
- [Greet alice](http://localhost:8302/monitoring/messages/greet?name=alice)
- [Greet bob](http://localhost:8302/monitoring/messages/greet?name=bob)
- [Greet charly](http://localhost:8302/monitoring/messages/greet?name=charly)

## Solution

### Timer

Uncomment `metricService.addRandomGreetDuration()` in the controller
```java
// Creation:
Timer greetsTimer = Metrics.timer(MetricName.GREETS_TIME.getMetricName());
// Usage:
var seconds = random.nextInt(60) + 1;
var duration = Duration.of(seconds, ChronoUnit.SECONDS);
greetsTimer.record(duration);
```

### Counter

Uncomment `metricService.increaseGreetsCountFor(name)` in the controller
```java
// Creation:
var tag = Tag.of(MetricTagName.NAME.name(), name);
var counter =  Metrics.counter(
    MetricName.GREETS_COUNT.getMetricName(),
    List.of(tag)
);
// Usage:
counter.increment();
```

### Gauge

Uncomment `metricService.notifyGreetFor(name)` in the controller
```java
// Creation:
AtomicLong greetsRemainingGauge = Metrics.gauge(
    MetricName.GREETS_REMAINING.getMetricName(),
    new AtomicLong(knownPeopleGreeted.size())
);
// Usage:
greetsRemainingGauge.set(remainingToGreet);
```

### Optional

1. Call at least once the endpoint
2. go to wallboard
3. open the instance
4. go to the metrics
5. add the metrics:
   - greets.time
   - greets.people.count
   - greets.remaining
