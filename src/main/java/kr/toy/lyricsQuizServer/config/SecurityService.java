package kr.toy.lyricsQuizServer.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final SecurityProperties securityProperties;

    public void setCookieWithToken(Boolean isRefreshToken, String tokenValue, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(securityProperties.cookieName().getTokenNameBy(isRefreshToken), tokenValue)
                .domain(securityProperties.domain())
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(55555555)//FIXME 시간 변경
                .httpOnly(isRefreshToken)
                .build();
        response.addHeader("Set-Cookie", String.valueOf(cookie));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-expose-headers", "Set-Cookie");
    }

    public String resolveToken(HttpServletRequest request, String cookieName) {
        String token = Arrays.stream(request.getCookies())
                .filter(data -> cookieName.equals(data.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(NoSuchElementException::new);
        return token;
    }
}
