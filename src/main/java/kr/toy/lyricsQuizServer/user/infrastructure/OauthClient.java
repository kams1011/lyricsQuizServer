package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@FeignClient(name = "dummy-name", url = "https://this-is-a-placeholder.com")
public interface OauthClient {

    @PostMapping(produces = "application/json", headers = "Content-Type=application/x-www-form-urlencoded")//FIXME 파라미터 받아지는지 확인 필요
    OauthProperties.AccessTokenResponse getAccessToken(URI uri, @RequestBody OauthProperties.AccessTokenRequest request);

    @GetMapping// 확인
    String getUserInfo(URI uri, @RequestHeader(value = "Authorization", required = true) String accessToken); //FIXME return type 변경


}
