package kr.toy.lyricsQuizServer.user.controller.port;

import kr.toy.lyricsQuizServer.user.domain.User;

public interface UserService {

    User getByEmail(String email);

    User signUp(); // FIXME : 소셜 회원가입 시 리턴되는 값 확인

    User login();

    User quit();

    User changeNickName();

}
