package kr.toy.lyricsQuizServer.config.ConfigurationProperties;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@ConfigurationProperties(prefix = "security-property")
@Component
public class SecurityProperties {


    private CookieName cookieName;

    private String jwtSecret;

    private String domain;

    private Long ACCESS_TOKEN_EXPIRE_MINUTE;

    private Long REFRESH_TOKEN_EXPIRE_MINUTE;

    public CookieName cookieName(){
        return cookieName;
    }

    public String jwtSecret(){
        return jwtSecret;
    }

    public String domain() { return  domain;}

    public Long AccessTokenExpireMinute() {
        return ACCESS_TOKEN_EXPIRE_MINUTE;
    }

    public Long RefreshTokenExpireMinute() {
        return REFRESH_TOKEN_EXPIRE_MINUTE;
    }

    @Setter
    public static class CookieName{

        String accessTokenCookieName;

        String refreshTokenCookieName;

        public String getTokenNameBy(Boolean httpOnly){
            if (httpOnly){
                return refreshTokenCookieName;
            } else {
                return accessTokenCookieName;
            }
        }

        public String accessTokenCookieName(){
            return accessTokenCookieName;
        }

        public String refreshTokenCookieName(){
            return refreshTokenCookieName;
        }
    }
}
