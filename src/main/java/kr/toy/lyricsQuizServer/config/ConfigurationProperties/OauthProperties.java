package kr.toy.lyricsQuizServer.config.ConfigurationProperties;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;


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
                    .redirect_uri("https://lyricsquizkaams.site/login/callback") //FIXME 환경변수로 관리
                    .code(code)
                    .grant_type("authorization_code")
                    .build();

            return accessTokenRequest;
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class AccessTokenResponse{

        String access_token;

        String scope;

        String token_type;

        String refresh_token;

        Integer expires_in;

        Integer refresh_token_expires_in;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class UserLoginInfoDTO{

        Long id;

        String email; //GOOGLE

        String login; //GITHUB

        Map<String, Object> response; //NAVER

        Map<String, Object> kakao_account; //KAKAO


        public String getUserInfoInResponse(LoginType loginType){
            Object variable = getVariableBy(loginType);
            if (variable instanceof Map){
                return ((Map<?, ?>) variable).get(loginType.getScope()) + "";
            } else if (variable instanceof String) {
                return variable + "";
            }
            return null;
        }

        public Object getVariableBy(LoginType loginType) {
            switch (loginType) {
                case GOOGLE:
                    return email;
                case KAKAO:
                    return kakao_account;
                case NAVER:
                    return response;
                case GITHUB:
                    return login;
                case INSTAGRAM:
                    return "NOTYET";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


}
