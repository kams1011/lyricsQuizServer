package kr.toy.lyricsQuizServer.user.domain.dto;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import lombok.Builder;
import lombok.Getter;


@Getter
public class UserCreate {


    private final String email;

    private final String nickName;

    private final LoginType loginType; // FIXME : LoginType의 유효성 검사를 어떻게 할지 생각


    @Builder
    public UserCreate(String email, String nickName, LoginType loginType){
        this.email = email;
        this.nickName = nickName;
        this.loginType = loginType;
    }
}
