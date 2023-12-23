package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties.AccessTokenRequest;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties.AccessTokenResponse;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AuthServerAPIImpl implements AuthServerAPI {

    private final OauthProperties oauthProperties;

    private final OauthClient oauthClient;

    @Override
    public OauthProperties.AccessTokenResponse getAccessToken(LoginType loginType, String code) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
        URI uri = URI.create(clientServerElement.tokenUrl());
        AccessTokenRequest accessTokenRequest = OauthProperties.AccessTokenRequest.from(clientServerElement, code);
        AccessTokenResponse accessToken = oauthClient.getAccessToken(uri, accessTokenRequest);
        return accessToken;
    }

    @Override
    public String getEmailBy(LoginType loginType, String accessToken) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
        OauthProperties.UserInfoDTO userInfoResponse = oauthClient.getUserInfo(URI.create(clientServerElement.infoUrl()), "Bearer " + accessToken); // FIXME 잘못된 인자가 들어왔거나 Return type이 달라질떄, 통신에 실패했을때 에러 핸들링 필요

        return userInfoResponse.getUserInfoInResponse(loginType);
    }

    @Override
    public void getInformation() {
        return;
    }
}
