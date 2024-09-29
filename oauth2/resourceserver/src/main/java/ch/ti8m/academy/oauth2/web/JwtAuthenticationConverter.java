package ch.ti8m.academy.oauth2.web;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtAuthenticationConverter implements Converter<Jwt, CustomAuthenticationToken> {

    @Override
    public CustomAuthenticationToken convert(Jwt in) {
        List<GrantedAuthority> authorities = List.of(() -> "read");

        var priority = String.valueOf(in.getClaims().get("priority"));
        log.info("Priority: {}", priority);
        return new CustomAuthenticationToken(in, authorities, priority);
    }

}