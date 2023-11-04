package kr.toy.lyricsQuizServer.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

import static javax.management.timer.Timer.ONE_MINUTE;


@RequiredArgsConstructor
@Service
public class SecurityService {

    private final byte[] secretKeyBytes;
    private final int accessTokenExpireMinute;

    private final int refreshTokenExpireMinute;
    private final UserRepository userRepository;


    public String accessTokenIssue(Long userSeq){
        User user = userRepository.getById(userSeq);
        Date now = new Date();
        Claims claims = Jwts.claims();
        claims.put("roles", Collections.singleton(user.getRole()));
        claims.put("id", user.getEmail());
        claims.put("nickName", user.getNickName());
        claims.put("userSeq", userSeq);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ONE_MINUTE * accessTokenExpireMinute))
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes))
                .compact();
    }


    public void makeCookie(String tokenName, String tokenValue, Boolean httpOnly, int maxAge, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(tokenName, tokenValue)
                .domain("localhost")
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .httpOnly(httpOnly).build();
        response.addHeader("Set-Cookie", cookie.toString());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-expose-headers", "Set-Cookie");
    }
}
