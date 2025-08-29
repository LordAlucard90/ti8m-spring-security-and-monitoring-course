# Monitoring - 2 - Solution

## Exercise

### Timer
Create a Timer that counts the total time spent on greeting.\
Every time a person is greeted add the time spent to greet him.

Register the metric in the Spring Boot Admin dashboard.

### Counter
Create a dynamic Counter for each person that counts
how many times the person has been greeted.\
Each counter must have a tag that refers to the specific person.\
Increment the person's counter whenever the person is greeted.

Register the metric in the Spring Boot Admin dashboard.

### Gauge
Create a Gauge that count downwards the known people that have not yet been greeted.\
The gauge must be initialized with the total number of known persons
and each time a person is greeted the gouge must be updated.

Register the metric in the Spring Boot Admin dashboard.

### Links
- [promethus](http://localhost:8303/monitoring/actuator/prometheus)
- [Admin dashboard](http://localhost:8303/monitoring/wallboard)
- [Greet alice](http://localhost:8303/monitoring/messages/greet?name=alice)
- [Greet bob](http://localhost:8303/monitoring/messages/greet?name=bob)
- [Greet charly](http://localhost:8303/monitoring/messages/greet?name=charly)

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

```bash
curl 'http://localhost:8303/monitoring/messages/greet?name=alice' -w " %{http_code}" 
```
```bash
curl 'http://localhost:8303/monitoring/messages/greet?name=bob' -w " %{http_code}" 
```
```bash
curl 'http://localhost:8303/monitoring/messages/greet?name=charly' -w " %{http_code}" 
```

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
Add in the Admin Board under metric:
- `greets.time`
- set `COUNT`: integer
- set `TOTAL_TIME`: duration
- set `MAX`: duration

In the metric are showed (in order):
- The number of calls
- The sum of time of all calls
- the max duration of a call

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
Add in the Admin Board under metric:
- `greets.people.count` with `NAME`: `-`
- `greets.people.count` with `NAME`: `alice`
- `greets.people.count` with `NAME`: `bob`
- `greets.people.count` with `NAME`: `charly`

In the metric are showed:
- the total calls 
- how many times each tag (name) has been called

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
Add in the Admin Board under metric:
- `greets.remaining`

Watch on each call the remaining count going down.
