package kr.toy.lyricsQuizServer.user.service.port;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties;
import kr.toy.lyricsQuizServer.user.domain.LoginType;

public interface AuthServerAPI {

    OauthProperties.AccessTokenResponse getAccessToken(LoginType loginType, String code);

    String getEmailBy(LoginType loginType, String accessToken);

    void getInformation();

}
