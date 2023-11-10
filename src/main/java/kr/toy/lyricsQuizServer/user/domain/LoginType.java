package kr.toy.lyricsQuizServer.user.domain;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public enum LoginType {

    GOOGLE("email"),
    KAKAO("email"),
    NAVER("email"),
    GITHUB("login"),
    INSTAGRAM("notyet");

    private String type;

    private String scope;

    LoginType(String scope){
        this.scope = scope;
    }


    public void isNotScope(){

    }


}
