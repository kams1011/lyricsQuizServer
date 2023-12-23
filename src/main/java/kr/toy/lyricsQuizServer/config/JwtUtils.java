package kr.toy.lyricsQuizServer.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.SecurityProperties;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

import static javax.management.timer.Timer.ONE_MINUTE;
import static javax.management.timer.Timer.ONE_WEEK;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final SecurityProperties securityProperties;
    private final int ACCESS_TOKEN_EXPIRE_MINUTE = 5;
    private final long REFRESH_TOKEN_EXPIRE_MINUTE = ONE_WEEK; //FIXME 수정
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

    public String jwtIssue(Long userSeq, Claims claims, long expireMinute){
        User user = userRepository.getById(userSeq);
        Date now = new Date();
        claims.put("userSeq", user.getUserSeq());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ONE_MINUTE * expireMinute))
                .signWith(Keys.hmacShaKeyFor(securityProperties.jwtSecret().getBytes()))
                .compact();
    }

    public Long getUserSeqIn(String token){
        Claims claims = getClaimsIn(token);
        Long userSeq = Long.parseLong(claims.get("userSeq").toString());
        return userSeq;
    }

    public User getUserBy(String token){
        Claims claims = getClaimsIn(token);
        Long userSeq = Long.parseLong(claims.get("userSeq").toString());
        User user = userRepository.getById(userSeq);
        return user;
    }

    public String getEmailIn(String token){
        Claims claims = getClaimsIn(token);
        String email = claims.get("id").toString();
        return email;
    }

    public Claims getClaimsIn(String token) {
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(securityProperties.jwtSecret().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }

        return claims;
    }


}
