package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Setter
@ConfigurationProperties(prefix = "oauth")
@Component
public class OauthConfig {

    private Element GOOGLE;
    private Element KAKAO;
    private Element NAVER;
    private Element GITHUB;
    private Element INSTAGRAM;



    public Element GOOGLE(){
        return GOOGLE;
    }

    public Element KAKAO(){
        return KAKAO;
    }

    public Element NAVER(){
        return NAVER;
    }

    public Element GITHUB(){
        return GITHUB;
    }

    public Element INSTAGRAM(){
        return INSTAGRAM;
    }

    @Setter
    public static class Element{
        String client_id;
        String client_secret;
        String tokenUrl;
        String infoUrl;
        String scope;

        public String client_id(){
            return client_id;
        }

        public String client_secret(){
            return client_secret;
        }

        public String tokenUrl(){
            return tokenUrl;
        }

        public String infoUrl(){
            return infoUrl;
        }

        public String getScope(LoginType loginType){

            if (loginType.equals(LoginType.KAKAO) == true || loginType.equals(LoginType.NAVER)) {
                throw new IllegalArgumentException("");
            }

            return scope;
        }

    }


}
