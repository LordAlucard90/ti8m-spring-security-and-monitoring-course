# Security Basic - 2 - Solution

## Exercise

Implement the logic to load the user from the database in the `CustomUserDetailsService`.

Enable one or more security annotation methods.

Use the annotations to configure the following security requirements:
- Admin users can access `/default/admin`
- Staff users can access `/default/staff`
- User users can access `/default/user`
- Authenticated users can access `/default/authenticated`
- Anyone can access `/default/open`

You know when the exercise is successfully completed when all the tests are green.

## Solution

Enable security annotation methods:
```java
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    // ...
}
```
Custom user details service:
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var errorMessage = String.format("User(username=%s) not found.", username);
        return userRepository.findById(username)
                .map(this::generateUserDetails)
                // used for 401 exception
                .orElseThrow(() -> new UsernameNotFoundException(errorMessage));
    }

    private UserDetails generateUserDetails(UserEntity userEntity) {
        log.info("Generating details for: " + userEntity.describe());

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().getRole())
                // or .authorities(userEntity.getRole().getAuthority())
                // used for 401 exception
                .disabled(userEntity.isDisabled())
                .build();
    }
}
```