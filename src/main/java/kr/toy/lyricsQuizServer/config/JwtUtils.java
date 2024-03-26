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

import java.util.Collections;
import java.util.Date;

import static javax.management.timer.Timer.*;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;

    public String accessTokenIssue(Long userSeq){
        User user = userRepository.getById(userSeq);

        Claims claims = Jwts.claims();
        claims.put("roles", Collections.singleton(user.getRole()));
        claims.put("id", user.getEmail());
        claims.put("nickName", user.getNickName());

        return jwtIssue(userSeq, claims, securityProperties.AccessTokenExpireMinute());
    }

    public String refreshTokenIssue(Long userSeq){
        return jwtIssue(userSeq, Jwts.claims(), securityProperties.RefreshTokenExpireMinute());
    }

    public String jwtIssue(Long userSeq, Claims claims, long expireMinute){
        User user = userRepository.getById(userSeq);
        Date now = new Date();
        claims.put("userSeq", user.getUserSeq());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + ONE_MINUTE * expireMinute))
                .setExpiration(new Date(now.getTime() + ONE_SECOND * expireMinute))
                .signWith(Keys.hmacShaKeyFor(securityProperties.jwtSecret().getBytes()))
                .compact();
    }

    public Long getUserSeqIn(String token){
        Claims claims = getClaimsIn(token);
        Long userSeq = Long.parseLong(String.valueOf(claims.get("userSeq")));
        return userSeq;
    }

    public User getUserBy(String token){
        Claims claims = getClaimsIn(token);
        Long userSeq = Long.parseLong(String.valueOf(claims.get("userSeq")));
        User user = userRepository.getById(userSeq);
        return user;
    }

    public String getEmailIn(String token){
        Claims claims = getClaimsIn(token);
        String email = String.valueOf(claims.get("id"));
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
