package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.ConfigurationProperties.OauthProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@FeignClient(name = "dummy-name", url = "https://this-is-a-placeholder.com")
public interface OauthClient {

    @PostMapping(produces = "application/json", headers = "Content-Type=application/x-www-form-urlencoded")
    OauthProperties.AccessTokenResponse getAccessToken(URI uri, @RequestBody OauthProperties.AccessTokenRequest request);

    @GetMapping
    OauthProperties.UserInfoDTO getUserInfo(URI uri, @RequestHeader(value = "Authorization", required = true) String accessToken);


}
