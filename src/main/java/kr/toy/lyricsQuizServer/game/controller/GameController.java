package kr.toy.lyricsQuizServer.game.controller;

import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Builder
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {


    private final GameService gameService;
    //트랜잭션 보장을 해야하는 서비스라면 따로 추출하기.
    //그렇지 않다면 각각 서비스를 injection하는 쪽으로 구현.


    //현재 아키텍쳐 구조로는 entity가 컨트롤러에 드러나지 않는다.
    //그렇다면 화면에 나올 데이터를 뿌려주는 dto는 프레젠테이션 영역인 컨트롤러에서 하는게 계층별 역할분리에 부합한다.
    @GetMapping()
    public ResponseEntity<List<GameRoom>> 로비_리스트_조회(Pageable pageable){
        return ResponseEntity.ok()
                .body(gameService.getGameList(pageable)
                        .stream().map(data -> GameRoom.from(data))
                        .collect(Collectors.toList())
                );
    }

}
