# Monitoring - 1

## Exercise

Enable Spring Boot Admin and explore the Admin main pages
- http://localhost:8301/monitoring/wallboard
- http://localhost:8301/monitoring/applications 
- http://localhost:8301/monitoring/journal

From the wallboard navigate to the application and inspect details and metrics.
In the metrics page add the following metric:
- metric: `http.server.requests`
- uri: `/messages/greet` (it will appear only after the first call on that endpoint)

Now play with the `greet` endpoint and watch the values change live.
- http://localhost:8301/monitoring/messages/greet?name=alice
- http://localhost:8301/monitoring/messages/greet?name=bob
- http://localhost:8301/monitoring/messages/greet?name=charly

Note: on windows you may need to configure your local domain to redirect to 127.0.0.1 to make it work:
- run as administrator `notepad`
- open `c:\Windows\System32\Drivers\etc\hosts`
- add `127.0.0.1 <local_domain>`
- save
