package kr.toy.lyricsQuizServer.user.service.port;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import kr.toy.lyricsQuizServer.user.domain.LoginType;

import java.util.Map;

public interface AuthServerAPI {

    OauthProperties.AccessTokenResponse getAccessToken(LoginType loginType, String code);

    Map<String, String> getUserInfoBy(LoginType loginType, String accessToken);

    void getInformation();

}
