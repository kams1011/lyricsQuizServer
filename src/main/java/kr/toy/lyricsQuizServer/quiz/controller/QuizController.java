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



}
