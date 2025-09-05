# Api Key - 0 - Solution

## Exercise

...

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

### Anonymous
```bash
curl 'http://localhost:8100/security-basic/messages/default/open-who-am-i' -w " %{http_code}" 
curl 'http://localhost:8100/security-basic/messages/default/who-am-i' -w " %{http_code}" 
```

### Alice
```bash
curl --header "X-API-KEY: api-key-a" 'http://localhost:8100/security-basic/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-a" 'http://localhost:8100/security-basic/messages/default/who-am-i' -w " %{http_code}" 
```

### Bob
```bash
curl --header "X-API-KEY: api-key-b" 'http://localhost:8100/security-basic/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-b" 'http://localhost:8100/security-basic/messages/default/who-am-i' -w " %{http_code}" 
```

### Charly
```bash
curl --header "X-API-KEY: api-key-c" 'http://localhost:8100/security-basic/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-c" 'http://localhost:8100/security-basic/messages/default/who-am-i' -w " %{http_code}" 
```

### Daniel
```bash
curl --header "X-API-KEY: api-key-d" 'http://localhost:8100/security-basic/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-d" 'http://localhost:8100/security-basic/messages/default/who-am-i' -w " %{http_code}" 
```

## Solution

...