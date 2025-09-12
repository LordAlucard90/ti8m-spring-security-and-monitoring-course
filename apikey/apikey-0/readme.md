# Api Key - 0

## Exercise

In the `SecurityConfig`add the `ApiKeiFilter`to the chain.

In the `ApiKeiFilter` retrieve the user from the api key located in the request header
and fill the `SecurityContext` with the `ApiKeyAuthentication` created from that user.

In the `ApiKeyAuthentication` finish the implementation of the `Authorization` interface.

In the `WhoAmIDto` retrieve the principal information.

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

### Anonymous
```bash
curl 'http://localhost:8100/api-key/messages/default/open-who-am-i' -w " %{http_code}" 
curl 'http://localhost:8100/api-key/messages/default/who-am-i' -w " %{http_code}" 
```

### Alice
```bash
curl --header "X-API-KEY: api-key-a" 'http://localhost:8100/api-key/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-a" 'http://localhost:8100/api-key/messages/default/who-am-i' -w " %{http_code}" 
```

### Bob
```bash
curl --header "X-API-KEY: api-key-b" 'http://localhost:8100/api-key/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-b" 'http://localhost:8100/api-key/messages/default/who-am-i' -w " %{http_code}" 
```

### Charly
```bash
curl --header "X-API-KEY: api-key-c" 'http://localhost:8100/api-key/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-c" 'http://localhost:8100/api-key/messages/default/who-am-i' -w " %{http_code}" 
```

### Daniel
```bash
curl --header "X-API-KEY: api-key-d" 'http://localhost:8100/api-key/messages/default/open-who-am-i' -w " %{http_code}" 
curl --header "X-API-KEY: api-key-d" 'http://localhost:8100/api-key/messages/default/who-am-i' -w " %{http_code}" 
```
