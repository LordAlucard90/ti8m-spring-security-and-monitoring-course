# Security Basic - 1 - Solution

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

## Solution

Security filter configuration:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // define roles
    private static final String USER_ROLE = "USER";
    private static final String STAFF_ROLE = "STAFF";
    private static final String ADMIN_ROLE = "ADMIN";
    // define endpoints categories
    private static final String[] WHITE_LIST = new String[]{
            "/messages/default/open",
    };
    private static final String[] STAFF_ENDPOINTS = new String[]{
            "/messages/default/staff",
    };
    private static final String[] ADMIN_ENDPOINTS = new String[]{
            "/messages/default/admin",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // open white list endpoints
                        .requestMatchers(WHITE_LIST).permitAll()
                        // define protected endpoints
                        .requestMatchers(STAFF_ENDPOINTS).hasRole(STAFF_ROLE)
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole(ADMIN_ROLE)
                        // require all others authenticated
                        .anyRequest().authenticated()
                );

        // configure authentication type
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // use default password encoder configuration
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUsers(PasswordEncoder encoder) {
        // define the users
        var alice = User.withUsername("alice")
                .password(encoder.encode("password-a"))
                .roles(ADMIN_ROLE, STAFF_ROLE)
                .build();
        var bob = User.withUsername("bob")
                .password(encoder.encode("password-b"))
                .roles(STAFF_ROLE)
                .build();
        var charly = User.withUsername("charly")
                .password(encoder.encode("password-c"))
                .roles(USER_ROLE)
                .build();
        var users = List.of(alice, bob, charly);
        // configure and provide the UserDerailsManager
        return new InMemoryUserDetailsManager(users);
    }
}
```