package kr.toy.lyricsQuizServer.quiz.controller;


import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Builder
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    //TODO 로비 리스트 API
    //방 제목 및 방장 닉네임 검색 API
    //로비 리스트 API
    //같이할 사람 찾는 API
    //방을 생성하는 API
}
