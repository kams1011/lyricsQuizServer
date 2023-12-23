package kr.toy.lyricsQuizServer.user.domain;

import lombok.Getter;

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
