package kr.toy.lyricsQuizServer.user.service.port;

import kr.toy.lyricsQuizServer.user.domain.LoginType;

public interface AuthServerAPI {

    void getUserInfoBy(LoginType loginType, String accessToken);

    void getInformation();

}
