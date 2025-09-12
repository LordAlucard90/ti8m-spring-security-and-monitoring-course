# Security Basic - 0 - Solution

## Exercise

Customize default user:
- username: student
- password: secret

Configure the system to use Basic auth as authentication method.

Match the following security requirements:
- allow all the request `/messages/default/open`
- restring the access to authenticated users on `/messages/default/authenticated`

You know when the exercise is successfully completed when all the tests are green.

## Requests

On Windows, Powershell is not completely compatible,
I have Git Bash installed and I was able to use that:
1. Go to `File` > `Settings` > `Tools` > `Terminal`
2. Set Shell path: `C:\Program Files\Git\bin\bash.exe`

### Anonymous
```bash
curl 'http://localhost:8001/security-basic/messages/default/open' -w " %{http_code}" 
curl 'http://localhost:8001/security-basic/messages/default/authenticated' -w " %{http_code}" 
```

### Student
```bash
curl -u student:secret 'http://localhost:8001/security-basic/messages/default/open' -w " %{http_code}" 
curl -u student:secret 'http://localhost:8001/security-basic/messages/default/authenticated' -w " %{http_code}" 
```

## Solution

Default user config:
```yaml
spring:
  security:
    user:
      name: student
      password: secret
```

Security filter configuration:
```java
public class SecurityConfig {
    private final String[] whiteList = new String[]{
            "/messages/default/open",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(whiteList).permitAll()
                        // require all others authenticated
                        .anyRequest().authenticated()
                );

        // configure authentication type
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```