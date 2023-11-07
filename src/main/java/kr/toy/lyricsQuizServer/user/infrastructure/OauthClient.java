package kr.toy.lyricsQuizServer.user.infrastructure;

import kr.toy.lyricsQuizServer.config.OauthProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;

@FeignClient(name = "dummy-name", url = "https://this-is-a-placeholder.com")
public interface OauthClient {


    @PostMapping(path = "", headers = "Content-Type: application/json") //FIXME 파라미터 받아지는지 확인 필요
    OauthProperties.AccessTokenResponse getAccessToken(URI uri, OauthProperties.AccessTokenRequest request);


    @GetMapping(path = "") // 확인
    Object getUserInfo(URI uri, @RequestHeader(value = "Authorization", required = true) String accessToken); //FIXME return type 변경


}
