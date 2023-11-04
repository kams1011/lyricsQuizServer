package kr.toy.lyricsQuizServer.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    private final SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String accessToken = resolveToken(request, CookieType.ACCESS.getCookieName());
        // 여기서 AuthenticationManager를 호출해서 AuthenticationProvider가 동작하게됨.
        // 그러면 provider에서 thrown한 에러를 filter에서 잡는게 가능할듯..
        String refreshToken;
        try {
            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken("principal_을 넣으세요", accessToken));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            refreshTokenCheck(request);
            //만약 통과한다면?
//            AccessToken을 재발급해주는 로직을 넣어야함.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e){
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request, String cookieName) {
        String token = Arrays.stream(request.getCookies())
                .filter(data -> cookieName.equals(data.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(NoSuchElementException::new);



        return token;
    }


    private String refreshTokenCheck(HttpServletRequest request){
        String refreshToken = "";
        try {
            refreshToken = resolveToken(request, CookieType.REFRESH.getCookieName());
        } catch (JwtInvalidException | NullPointerException | NoSuchElementException e){
            SecurityContextHolder.clearContext();
            //1. Cookie가 없을 때,  -> NullPoint
            //2. Cookie는 있는데 안에 Jwt가 없을때, - NoSuchElementException
            //3. Cookie도 있고 Jwt도 있는데 Jwt관련 에러들이 발생할때. - JwtInvalidException
            //FIXME 로그인을 새로 하게 만드는 로직 추가.
        }
        return refreshToken;
    }


    // jwt 유틸들

}
