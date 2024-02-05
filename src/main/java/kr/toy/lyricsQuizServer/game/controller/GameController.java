package kr.toy.lyricsQuizServer.game.controller;

import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizDetailToCreateRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

@RestController
@Builder
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    private final QuizService quizService;

    private final SecurityService securityService;
    //트랜잭션 보장을 해야하는 서비스라면 따로 추출하기.
    //그렇지 않다면 각각 서비스를 injection하는 쪽으로 구현.

    //현재 아키텍쳐 구조로는 entity가 컨트롤러에 드러나지 않는다.
    //그렇다면 화면에 나올 데이터를 뿌려주는 dto는 프레젠테이션 영역인 컨트롤러에서 하는게 계층별 역할분리에 부합한다.
    @GetMapping("")
    public ResponseEntity<Response> getGameList(@RequestParam(required = false) String keyword, Pageable pageable){
        return ResponseEntity.ok()
                .body(Response.success(
                        gameService.getGameListByWord(keyword, pageable)
                        .stream().map(data -> GameRoom.from(data))
                        .collect(Collectors.toList())));
    }

    @GetMapping("/quiz")
    public ResponseEntity<Response> getQuizSummaryList(@RequestParam(required = false) String keyword, Pageable pageable){
        return ResponseEntity.ok()
                .body(Response.success(quizService.getList(keyword, pageable)
                        .stream().map(data -> QuizDetailToCreateRoom.fromModel(data))
                        .collect(Collectors.toList())));
    }

    @PostMapping("")
    public ResponseEntity<Response> create(@RequestBody GameCreate gameCreate, User maker){

        return ResponseEntity.ok().body(Response.success(gameService.create(maker, gameCreate)));
    }


    @PostMapping("/password")
    public ResponseEntity<Response> checkPassword(@RequestBody GamePassword gamePassword){
        try {
            gameService.checkPassword(gamePassword);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Response.fail(e.getMessage(), null, null));
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(Response.success("유효한 비밀번호입니다."));
    }

    @GetMapping("/room")
    public ResponseEntity<Response> enter(@RequestParam Long roomId, @RequestParam(required = false) String password, User user) {
        try {
            gameService.enter(roomId, password, user);
            return ResponseEntity.ok().body(new Response(true, null, null, null));
        } catch (IllegalStateException | InvalidDataAccessApiUsageException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response(false, e.getMessage(), null, null));
        }
    }

    @PatchMapping("/invitation")
    public ResponseEntity<Response> allowInvitation(@RequestParam boolean isAllowed, User user){
        try{
            gameService.allowInvitation(user, isAllowed);
            return ResponseEntity.ok().body(new Response(true, null, null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Response(true, e.getMessage(), null, null));
        }
    }

    @GetMapping("/invitation")
    public ResponseEntity<Response> getInvitationInfo(User user) {
        try{
            return ResponseEntity.ok().body(new Response(true, null, gameService.getMyInvitationInfo(user), null));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Response(false, e.getMessage(), null, null));
        }
    }

}
