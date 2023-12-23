package kr.toy.lyricsQuizServer.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {


    private final SecurityProperties securityProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        // FIXME 실제로 JWT를 검증하는 로직 수행.
//        JWT 토큰 검증: 주어진 JWT 토큰이 유효한지 확인합니다. 토큰 서명이 올바른지, 만료되지 않았는지 등을 검증합니다.
//        사용자 정보 추출: JWT 토큰에서 사용자 식별 정보를 추출합니다. 대표적으로는 사용자 아이디, 권한 등이 있습니다.
//        사용자 생성 및 반환: 조회한 사용자 정보를 사용하여 Authentication 객체를 생성하고 반환합니다. 보통은 UsernamePasswordAuthenticationToken이나 그의 서브클래스를 사용합니다.

        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(securityProperties.jwtSecret().getBytes())).build()
                    .parseClaimsJws(authenticationToken.getJwt())
                    .getBody();

            authenticationToken.setAuthenticated(true);
            authenticationToken.setDetails(claims.getSubject());
            //FIXME 이부분 한번 더 확인해보기.
            //FIXME AuthenticationException을 상속받은 CustomException 을 생성해야겠다.
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }


//    1. 쿠키가 존재하지 않는다.
//2. 쿠키가 존재하는데 내부의 Jwt는 유효하지 않다.


}

