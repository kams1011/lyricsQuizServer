package kr.toy.lyricsQuizServer.config;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.Builder;
import lombok.Getter;
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


    public Element getElementBy(LoginType loginType){
        switch (loginType) {
            case GOOGLE: return GOOGLE;
            case KAKAO: return KAKAO;
            case NAVER: return NAVER;
            case GITHUB: return GITHUB;
            case INSTAGRAM: return INSTAGRAM;
            default:throw new IllegalArgumentException();
        }
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


    @Getter
    @Builder
    public static class AccessTokenRequest{

        String client_id;

        String client_secret;

        String code;

        String redirect_uri;

        String grant_type;

        public static AccessTokenRequest from(Element element, String code){
            AccessTokenRequest accessTokenRequest = AccessTokenRequest.builder()
                    .client_id(element.client_id)
                    .client_secret(element.client_secret)
                    .redirect_uri("https://localhost:8080/login/callback")
                    .code(code)
                    .grant_type("authorization_code")
                    .build();

            return accessTokenRequest;
        }

        @Override
        public String toString() {
            return
                    "code=" + code + '&' +
                            "client_id=" + client_id + '&' +
                            "client_secret=" + client_secret + '&' +
                            "redirect_uri=" + redirect_uri + '&' +
                            "grant_type=" + grant_type;
        }



    }



    @Getter
    @Setter
    public static class AccessTokenResponse{

        String access_token;

        String scope;

        String token_type;
    }


}
