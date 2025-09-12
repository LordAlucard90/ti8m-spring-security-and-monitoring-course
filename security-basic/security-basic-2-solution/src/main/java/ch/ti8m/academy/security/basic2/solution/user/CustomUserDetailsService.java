package ch.ti8m.academy.security.basic2.solution.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
