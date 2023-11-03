package kr.toy.lyricsQuizServer.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final String accessTokenCookieName = "yml로관리";

    private final String refreshTokenCookieName = "yml로관리";

    //FIXME 세개 다 환경변수로 바꾸자.

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String accessToken = resolveToken(request, CookieType.ACCESS.getCookieName());


        try {
            // 여기서 AuthenticationManager를 호출해서 AuthenticationProvider가 동작하게됨.
            // 그러면 provider에서 thrown한 에러를 filter에서 잡는게 가능할듯..

            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken("principal_을 넣으세요", accessToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 처리 로직
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } // 여기서 잡아야겟구나

        String refreshToken = resolveToken(request, CookieType.REFRESH.getCookieName());
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String cookieName) {
        String accessToken = Arrays.stream(request.getCookies())
                .filter(data -> cookieName.equals(data.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(NoSuchElementException::new);

        return accessToken;
    }


    // jwt 유틸들

}
