# Security Basic - 0 - Solution

## Exercise

Customize default user:
- username: student
- password: secret

Configure the system to use Basic auth as authentication method.

Match the following security requirements:
- allow all the request `/message/default/open`
- restring the access to authenticated users on `/message/default/authentivated`

You know when the exercise is successfully completed when all the tests are green.

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