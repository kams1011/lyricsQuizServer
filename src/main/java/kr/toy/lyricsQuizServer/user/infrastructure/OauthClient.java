package kr.toy.lyricsQuizServer.user.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

@FeignClient(name = "dummy-name", url = "https://this-is-a-placeholder.com")
public interface OauthClient {

    @PostMapping(path = "/create") // 확인
    void getUserInfo(URI baseUrl, Object param); //FIXME return type 변경


}
