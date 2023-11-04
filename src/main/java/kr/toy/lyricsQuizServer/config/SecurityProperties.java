package kr.toy.lyricsQuizServer.config;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@ConfigurationProperties(prefix = "security-property")
@Component
public class SecurityProperties {


    private CookieName cookieName;

    private String jwtSecret;


    public CookieName cookieName(){
        return cookieName;
    }

    public String jwtSecret(){
        return jwtSecret;
    }

    @Setter
    public static class CookieName{

        String accessTokenCookieName;

        String refreshTokenCookieName;

        public String accessTokenCookieName(){
            return accessTokenCookieName;
        }

        public String refreshTokenCookieName(){
            return refreshTokenCookieName;
        }
    }
}
