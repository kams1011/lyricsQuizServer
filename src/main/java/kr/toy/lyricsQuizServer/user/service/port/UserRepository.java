package kr.toy.lyricsQuizServer.user.service.port;

import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public interface UserRepository {

    User getById(Long id);

    User getByEmail(String email);

    User getByEmailAndLoginType(String email, LoginType loginType);

    User getByNickName(String nickName);

    User save(User user);

}
