# Monitoring - Solution

## Exercise 1

Explore the following actuator resources:
- http://localhost:8300/monitoring/actuator/health
- http://localhost:8300/monitoring/actuator/info
- http://localhost:8300/monitoring/actuator/metrics
- http://localhost:8300/monitoring/actuator/prometheus
Others:
- http://localhost:8300/monitoring/actuator/env
- http://localhost:8300/monitoring/actuator/beans
- http://localhost:8300/monitoring/actuator/mappings

Note: the metrics listed in the `metrics` endpoint can be found in the prometheus after substituting `.` with `_`.

Tip: use your knowledge on security configuration to access the protected pages

### Solution

The credentials are:
```yaml
spring:
  security:
    user:
      name: student
      password: secret
```

## Exercise 2

Call the custom (secured) endpoint with multiple names
- http://localhost:8300/monitoring/messages/greet?name=alice
- http://localhost:8300/monitoring/messages/greet?name=bob
- http://localhost:8300/monitoring/messages/greet?name=charly

Search in the [prometheus endpoint](http://localhost:8300/monitoring/actuator/prometheus)
the uri `/messages/greet` and see how the following metrics change after each request:
- `http_server_requests_seconds_count`
- `http_server_requests_seconds_sum`
- `http_server_requests_seconds_max`

### Solution

`http_server_requests_seconds_count` holds the number of requests

`http_server_requests_seconds_sum` holds the sum of all the request execution times

`http_server_requests_seconds_max` holds the max execution time


## Exercise 3

Enable Spring Admin and visit the Admin main pages
- http://localhost:8300/monitoring/wallboard
- http://localhost:8300/monitoring/applications 
- http://localhost:8300/monitoring/journal

From the wallboard navigate to the application and inspect details and metrics.
In the metrics page add the following metric:
- metric: `http:server.requests`
- uri: `/messages/greet`

Now play with the `greet` endpoint and watch the values change in real time.

Note: you may need to configure your local domain to redirect to 127.0.0.1 to make it work.

## Solution

Add the `@EnableAdminServer` annotation to the main class.
