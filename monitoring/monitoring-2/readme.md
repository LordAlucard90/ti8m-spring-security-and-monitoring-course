# Monitoring - 2

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
