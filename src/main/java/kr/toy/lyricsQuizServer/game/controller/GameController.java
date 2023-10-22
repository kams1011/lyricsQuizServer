package kr.toy.lyricsQuizServer.game.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Builder
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    //트랜잭션 보장을 해야하는 서비스라면 따로 추출하기.
    //그렇지 않다면 각각 서비스를 injection하는 쪽으로 구현.


}
