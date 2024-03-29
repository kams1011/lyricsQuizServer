package kr.toy.lyricsQuizServer.game.controller;

import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.config.SecurityService;
import kr.toy.lyricsQuizServer.game.controller.port.GameService;
import kr.toy.lyricsQuizServer.game.controller.response.GameRoom;
import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.domain.dto.GamePassword;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizDetailToCreateRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
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
                .body(Response.success(gameService.getGameListByWord(keyword, pageable)));
    }

    @GetMapping("/quiz")
    public ResponseEntity<Response> getQuizSummaryList(@RequestParam(required = false) String keyword, Pageable pageable){
        PageImpl<Quiz> pages = quizService.getList(keyword, pageable);
        return ResponseEntity.ok()
                .body(Response.success(
                        new PageImpl<>(
                                pages.stream().map(data -> QuizDetailToCreateRoom.fromModel(data)).collect(Collectors.toList()),
                                pageable,
                                pages.getTotalElements())));
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

    @GetMapping("/host")
    public ResponseEntity<Response> isHost(@RequestParam Long roomId, User user) {
        try {
            return ResponseEntity.ok().body(new Response(true, null, gameService.isHost(roomId, user), null));
        } catch (IllegalStateException | InvalidDataAccessApiUsageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(false, e.getMessage(), null, null));
        }
    }

    @PatchMapping("/room")
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
        return ResponseEntity.ok().body(new Response(true, null, gameService.allowInvitation(user, isAllowed), null));
    }

    @GetMapping("/invitation")
    public ResponseEntity<Response> getMyInvitationStatus(User user) {
        try{
            return ResponseEntity.ok().body(new Response(true, null, gameService.getMyInvitationInfo(user), null));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new Response(false, e.getMessage(), null, null));
        }
    }
    @PatchMapping("/start")
    public ResponseEntity<Response> start(@RequestParam Long roomId, User user){
        gameService.start(roomId, user);
        return ResponseEntity.ok().body(new Response(true, null, null, null));
    }

    @GetMapping("/invitation/users")
    public ResponseEntity<Response> getInvitableUsers(Pageable pageable){
        return ResponseEntity.ok().body(
                new Response(true, null, gameService.getInvitableUsers(pageable), null));
    }

    @PostMapping("/invitation")
    public ResponseEntity<Response> invite(User user, @RequestParam Long invitedUserSeq, @RequestParam Long roomId){
        gameService.invite(roomId, user, invitedUserSeq);
        return ResponseEntity.ok().body(
                new Response(true, null, invitedUserSeq, null));
    }

    @PatchMapping("/ready")
    public ResponseEntity<Response> ready(@RequestParam Long roomId, User user){
        System.out.println("레디들어옴");
        gameService.ready(roomId, user);
        return ResponseEntity.ok().body(new Response(true, null, null, null));
    }

    public ResponseEntity<Response> start(){
        //FIXME 시작하는 사람 유효성 검사 필요
        return null;
    }

    @GetMapping("/temp")
    public ResponseEntity<Response> streaming(@RequestHeader HttpHeaders headers) throws IOException {

        return null;
    }

}
