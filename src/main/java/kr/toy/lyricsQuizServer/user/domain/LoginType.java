package kr.toy.lyricsQuizServer.user.domain;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LoginType {

    GOOGLE(0),
    KAKAO(1),
    NAVER(2),
    GITHUB(3),
    INSTAGRAM(4);

    private String type;

    private int number;

    LoginType(int number){
        this.number = number;
    }

    public void isNotScope(){

    }


}
