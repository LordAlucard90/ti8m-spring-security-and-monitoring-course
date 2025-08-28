# Security Basic - 1

## Exercise

Create the following in memory users:
- name: `alice`, password: `password-a`, roles: `ADMIN` and `STAFF`
- name: `bob`, password: `password-b`, roles: `STAFF`
- name: `charly`, password: `password-c`, roles: `USER`

Hint: provide a bean for the password encoder.

Security requirements:
- Admin users (alice) can access `/default/admin`
- Staff users (alice, bob) can access `/default/staff`
- Authenticated users can access `/default/authenticated`
- Anyone can access `/default/open`

You know when the exercise is successfully completed when all the tests are green.

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

### Anonymous
```bash
curl 'http://localhost:8002/security-basic/messages/default/open' -w " %{http_code}" 
curl 'http://localhost:8002/security-basic/messages/default/authenticated' -w " %{http_code}" 
curl 'http://localhost:8002/security-basic/messages/default/staff' -w " %{http_code}" 
curl 'http://localhost:8002/security-basic/messages/default/admin' -w " %{http_code}" 
```

### Alice
```bash
curl -u alice:password-a 'http://localhost:8002/security-basic/messages/default/open' -w " %{http_code}" 
curl -u alice:password-a 'http://localhost:8002/security-basic/messages/default/authenticated' -w " %{http_code}" 
curl -u alice:password-a 'http://localhost:8002/security-basic/messages/default/staff' -w " %{http_code}" 
curl -u alice:password-a 'http://localhost:8002/security-basic/messages/default/admin' -w " %{http_code}" 
```

### Bob
```bash
curl -u bob:password-b 'http://localhost:8002/security-basic/messages/default/open' -w " %{http_code}" 
curl -u bob:password-b 'http://localhost:8002/security-basic/messages/default/authenticated' -w " %{http_code}" 
curl -u bob:password-b 'http://localhost:8002/security-basic/messages/default/staff' -w " %{http_code}" 
curl -u bob:password-b 'http://localhost:8002/security-basic/messages/default/admin' -w " %{http_code}" 
```

### Charly
```bash
curl -u charly:password-c 'http://localhost:8002/security-basic/messages/default/open' -w " %{http_code}" 
curl -u charly:password-c 'http://localhost:8002/security-basic/messages/default/authenticated' -w " %{http_code}" 
curl -u charly:password-c 'http://localhost:8002/security-basic/messages/default/staff' -w " %{http_code}" 
curl -u charly:password-c 'http://localhost:8002/security-basic/messages/default/admin' -w " %{http_code}" 
```
