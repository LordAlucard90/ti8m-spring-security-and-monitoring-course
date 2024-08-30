# Monitoring - 2

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

### Links
- [promethus](http://localhost:8302/monitoring/actuator/prometheus)
- [Admin dashboard](http://localhost:8302/monitoring/wallboard)
- [Greet alice](http://localhost:8302/monitoring/messages/greet?name=alice)
- [Greet bob](http://localhost:8302/monitoring/messages/greet?name=bob)
- [Greet charly](http://localhost:8302/monitoring/messages/greet?name=charly)
