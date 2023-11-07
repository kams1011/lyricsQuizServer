package kr.toy.lyricsQuizServer.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

import static javax.management.timer.Timer.ONE_MINUTE;


@RequiredArgsConstructor
@Service
public class SecurityService {

    private final byte[] secretKeyBytes;
    private final int ACCESS_TOKEN_EXPIRE_MINUTE = 5;
    private final int REFRESH_TOKEN_EXPIRE_MINUTE = 30; //FIXME 수정
    private final UserRepository userRepository;



    public String accessTokenIssue(Long userSeq){
        User user = userRepository.getById(userSeq);

        Claims claims = Jwts.claims();
        claims.put("roles", Collections.singleton(user.getRole()));
        claims.put("id", user.getEmail());
        claims.put("nickName", user.getNickName());

        return jwtIssue(userSeq, claims, ACCESS_TOKEN_EXPIRE_MINUTE);
    }

    public String refreshTokenIssue(Long userSeq){
        return jwtIssue(userSeq, Jwts.claims(), REFRESH_TOKEN_EXPIRE_MINUTE);
    }

    public String jwtIssue(Long userSeq, Claims claims, int expireMinute){
        User user = userRepository.getById(userSeq);
        Date now = new Date();
        claims.put("userSeq", user.getUserSeq());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ONE_MINUTE * expireMinute))
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes))
                .compact();
    }

    public void setCookieWithToken(Boolean isRefreshToken, String tokenValue, HttpServletResponse response){
        SecurityProperties.CookieName cookieName = new SecurityProperties.CookieName();

        ResponseCookie cookie = ResponseCookie.from(cookieName.getTokenNameBy(isRefreshToken), tokenValue)
                .domain("localhost") //FIXME 환경변수로 관리
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


    public Long getUserSeqIn(String token){
        Claims claims = getClaimsIn(token);
        Long userSeq = (Long) claims.get("userSeq");

        return userSeq;
    }

    public Claims getClaimsIn(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKeyBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    
    //Jwt관련유틸들 추가
}
