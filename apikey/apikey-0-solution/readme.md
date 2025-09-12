# Api Key - 0 - Solution

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

## Solution

In the `SecurityConfig`add the `ApiKeiFilter`to the chain.
```java
// add aki key authorization filter
http.addFilterBefore(apiKeiFilter, AnonymousAuthenticationFilter.class);
```

In the `ApiKeiFilter` retrieve the user from the api key located in the request header
and fill the `SecurityContext` with the `ApiKeyAuthentication` created from that user.
```java
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiKeiFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get api key from header
        var apiKey = request.getHeader("X-API-KEY");
        log.debug("ApiKey: {}", apiKey);

        var optionalUser = userRepository.findByApiKey(apiKey);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            log.debug("User: {}", user.getUsername());

            // configure authentication context
            var apiKeyAuth = ApiKeyAuthentication.from(user);
            var securityContext = securityContextHolderStrategy.createEmptyContext();
            securityContext.setAuthentication(apiKeyAuth);
            securityContextHolderStrategy.setContext(securityContext);
        }

        // forward request with to the next filter.
        // In case the authorization is required 
        // it is also possible to throw and AccessDeniedException
        // instead of forwarding the request to the next chain element
        filterChain.doFilter(request, response);
    }
}
```

In the `ApiKeyAuthentication` finish the implementation of the `Authorization` interface.
```java
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiKeyAuthentication implements Authentication {
    // the principal in this example is represented by the whole entity for convenience, 
    // it should be a dto with the needed information only
    private UserEntity principal;
    private boolean authenticated;

    public static ApiKeyAuthentication from(UserEntity user) {
        return new ApiKeyAuthentication(
                user,
                // with this logic a disable user is still able to access to open endpoints
                // it is possible to do not allow this by throwing an exception in the filter
                !user.isDisabled()
        );
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        // the name in our case is the username
        return this.principal.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // the authorities are provided by the entity for convenience
        return this.principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        // the credentials should always be null after the authentication phase
        return null;
    }

    @Override
    public Object getDetails() {
        // other optional details as ip, sessionId, etc.
        return null;
    }
}
```

In the `WhoAmIDto` retrieve the principal information.
```java
public record WhoAmIDto(
        String user,
        UserRole role
) {
    public static WhoAmIDto fromSecurityContext() {
        // the current security context can be retrieved by the security context holder
        var securityContext = SecurityContextHolder.getContext();
        // the authentication is available from the current security context
        var authentication = securityContext.getAuthentication();
        // the principal is accessible from the Authentication
        if (authentication.getPrincipal() instanceof UserEntity user) {
            return new WhoAmIDto(
                    user.getUsername(),
                    user.getRole()
            );
        }
        return new WhoAmIDto("Anonymous", null);
    }
}
```
