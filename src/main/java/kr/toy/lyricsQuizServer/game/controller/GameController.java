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
    
    public ResponseEntity<?> 로비_리스트_가져오기(){
        return ResponseEntity.ok()
                .body(1234);
    }

    public ResponseEntity<?> 방_제목_및_방장_닉네임_검색(){
        return ResponseEntity.ok()
                .body(1234);
    }

    public ResponseEntity<?> 같이_할_사람_찾기(){
        return ResponseEntity.ok()
                .body(1234);
    }

    public ResponseEntity<?> 방_생성하기(){
        return ResponseEntity.ok()
                .body(1234);
    }

}
