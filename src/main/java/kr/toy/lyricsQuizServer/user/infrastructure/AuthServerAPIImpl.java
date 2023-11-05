package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import kr.toy.lyricsQuizServer.user.service.port.AuthServerAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServerAPIImpl implements AuthServerAPI {

    private final OauthProperties oauthProperties;




    @Override
    public void getEmail() {
        return;
    }

    @Override
    public void getInformation() {
        return;
    }
}
