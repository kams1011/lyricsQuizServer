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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization"); //FIXME 쿠키로 변경.

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", ""); //FIXME 쿠키로 변경.

        try {
            Authentication authentication = authenticationManager.authenticate(getAuthentication(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // FIXME 직접 setAuthentication을 해주는 부분을 수정하고싶음.
        } catch (ExpiredJwtException e) {
            // 만료된 토큰 처리 로직
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        // JWT 검증 로직
        // FIXME 서명 검증, 클레임 추출, 사용자 정보 로딩 등의 작업이 필요

        return new JwtAuthenticationToken(token); // FIXME token을 decode해서 userName과 권한을 넣는다.
    }


    // jwt 유틸들

}
