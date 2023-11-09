package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import kr.toy.lyricsQuizServer.config.OauthProperties.AccessTokenRequest;
import kr.toy.lyricsQuizServer.config.OauthProperties.AccessTokenResponse;
import kr.toy.lyricsQuizServer.user.domain.LoginType;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthServerAPIImpl implements AuthServerAPI {

    private final OauthProperties oauthProperties;

    private final OauthClient oauthClient;

    @Override
    public OauthProperties.AccessTokenResponse getAccessToken(LoginType loginType, String code) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
        URI uri = URI.create(clientServerElement.tokenUrl());

        System.out.println("여기까지 ㄴ왔을듯?");
        System.out.println(code);
        AccessTokenRequest accessTokenRequest = OauthProperties.AccessTokenRequest.from(clientServerElement, code);
        AccessTokenResponse accessToken = oauthClient.getAccessToken(uri, accessTokenRequest);
        System.out.println(accessToken.getAccess_token());
        return accessToken;
    }

    @Override
    public Map<String, String > getUserInfoBy(LoginType loginType, String accessToken) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
//        Map<String, Object> object = oauthClient.getUserInfo(URI.create(clientServerElement.infoUrl()), "Bearer " + accessToken); // FIXME returnType에 맞는 DTO 생성필요
        String object = oauthClient.getUserInfo(URI.create(clientServerElement.infoUrl()), "Bearer " + accessToken); // FIXME returnType에 맞는 DTO 생성필요
        System.out.println("왜안들어와");
        System.out.println(object);
        return null;
    }

    @Override
    public void getInformation() {
        return;
    }
}
