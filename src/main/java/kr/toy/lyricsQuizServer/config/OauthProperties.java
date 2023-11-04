package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Setter
@ConfigurationProperties(prefix = "oauth")
@Component
public class OauthProperties {

    private Element GOOGLE;
    private Element KAKAO;
    private Element NAVER;
    private Element GITHUB;
    private Element INSTAGRAM;


    // 가독성을 위해 Getter를 따로 만듬.
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

        // 가독성을 위해 Getter를 따로 만듬.
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

        public String scope(LoginType loginType){

            if (loginType.equals(LoginType.KAKAO) == true || loginType.equals(LoginType.NAVER)) {
                throw new IllegalArgumentException("Scope 파라미터가 존재하지 않는 로그인 타입입니다.");
            }

            return scope;
        }

    }


}
