package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.OauthProperties;
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
        OauthProperties.AccessTokenResponse result = oauthClient.getAccessToken(URI.create(clientServerElement.tokenUrl()),
        clientServerElement.client_id(), clientServerElement.client_secret(), code);

        System.out.println("success");
        System.out.println(result.getAccess_token());
        System.out.println(result.getToken_type());
        return result;
    }

    @Override
    public Object getUserInfoBy(LoginType loginType, String accessToken) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
        Object object = oauthClient.getUserInfo(URI.create(clientServerElement.infoUrl()), "Bearer " + accessToken); // FIXME returnType에 맞는 DTO 생성필요
        return object;
    }

    @Override
    public void getInformation() {
        return;
    }
}
