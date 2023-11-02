package kr.toy.lyricsQuizServer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // FIXME 실제로 JWT를 검증하는 로직 수행됩
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        authenticationToken.setAuthenticated(true);
        authenticationToken.setDetails("jwt에서 추출한 이름.");
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

