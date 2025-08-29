# Monitoring - 1 - Solution

## Exercise

Start monitoring-admin-board service and explore the Admin main pages
- http://localhost:8300/monitoring-admin/wallboard
- http://localhost:8300/monitoring-admin/applications
- http://localhost:8300/monitoring-admin/journal

Where are the credentials to access the admin board?

Can you see something? Is the monitored service is running?

From the wallboard navigate to the application and inspect details and metrics.
In the metrics tab (on the left) add the following metric:
- metric: `http.server.requests`
- uri: `/messages/greet` (it will appear only after the first call on that endpoint)

Now play with the `greet` endpoint and watch the values change live.
- http://localhost:8302/monitoring/messages/greet?name=alice
- http://localhost:8302/monitoring/messages/greet?name=bob
- http://localhost:8302/monitoring/messages/greet?name=charly

Note: on windows you may need to configure your local domain to redirect to 127.0.0.1 to make it work:
- run as administrator `notepad`
- open `c:\Windows\System32\Drivers\etc\hosts`
- add `127.0.0.1 <local_domain>`
- save

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

```bash
curl 'http://localhost:8302/monitoring/messages/greet?name=alice' -w " %{http_code}" 
```
```bash
curl 'http://localhost:8302/monitoring/messages/greet?name=bob' -w " %{http_code}" 
```
```bash
curl 'http://localhost:8302/monitoring/messages/greet?name=charly' -w " %{http_code}" 
```

## Solution

The credentials of the admin board are in the `monitoring-admin-board`'s `application.yaml` file
```yaml
spring:
  security:
    user:
      name: monitoring
      password: secret
```

It is needed to start the monitored server, it will register itself automatically
to the admin board and send regularly its data.

On each call on the greet endpoint it is updated the metric created in the dashboard,
this is useful to avoid searching for the metrics manually in the `actuator/prometheus`.
