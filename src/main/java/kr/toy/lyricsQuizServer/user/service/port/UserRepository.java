package kr.toy.lyricsQuizServer.user.service.port;

import kr.toy.lyricsQuizServer.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public interface UserRepository {

    User getById(Long id);

    User getByEmail(String email);

    User getByNickName(String nickName);

}
