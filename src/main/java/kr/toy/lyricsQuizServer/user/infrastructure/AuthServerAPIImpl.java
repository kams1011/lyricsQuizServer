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
    public void getUserInfoBy(LoginType loginType, String accessToken) {
        OauthProperties.Element clientServerElement = oauthProperties.getElementBy(loginType);
        oauthClient.getUserInfo(URI.create(clientServerElement.infoUrl()), accessToken); // FIXME returnType에 맞는 DTO 생성필요
    }

    @Override
    public void getInformation() {
        return;
    }
}
