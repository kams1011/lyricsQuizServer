package kr.toy.lyricsQuizServer.user.controller.port;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User getByEmail(String email);

    User getById(Long id);

    User getByNickName(String nickName);

    User signUp(); // FIXME : 소셜 회원가입 시 리턴되는 값 확인

//    User login(); 도메인 레벨에서만 처리하고, 실제 로그인은 다른 패키지에서 처리하면 될 것 같아 일단 주석처리.

    User quit();

    User changeNickName();


}
