package kr.toy.lyricsQuizServer.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

import static javax.management.timer.Timer.ONE_MINUTE;
import static javax.management.timer.Timer.ONE_WEEK;


//@RequiredArgsConstructor
@Service
public class SecurityService {

    private final SecurityProperties securityProperties;
    private final int ACCESS_TOKEN_EXPIRE_MINUTE = 5;
    private final long REFRESH_TOKEN_EXPIRE_MINUTE = ONE_WEEK; //FIXME 수정

    public SecurityService(SecurityProperties securityProperties){
        this.securityProperties = securityProperties;
    }

    public void setCookieWithToken(Boolean isRefreshToken, String tokenValue, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(securityProperties.cookieName().getTokenNameBy(isRefreshToken), tokenValue)
                .domain(securityProperties.domain())
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(55555555)//FIXME 시간 변경
                .httpOnly(isRefreshToken)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
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



    
    //Jwt관련유틸들 추가
}
