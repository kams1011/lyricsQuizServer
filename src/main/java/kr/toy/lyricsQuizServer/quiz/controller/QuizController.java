package kr.toy.lyricsQuizServer.quiz.controller;


import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Builder
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    @PostMapping("")
    public ResponseEntity<Quiz> create(@RequestBody QuizCreate quizCreate){

        return ResponseEntity.ok()
                .body(quizService.create(quizCreate));
    }

    @DeleteMapping("/{quizSeq}")
    public ResponseEntity<Quiz> delete(@PathVariable Long quizSeq){

        return ResponseEntity.ok()
                .body(quizService.delete(quizSeq));
    }

    @GetMapping("/{quizSeq}")
    public ResponseEntity<Boolean> solve(@PathVariable Long quizSeq,
                                      @RequestParam String answer){

        return ResponseEntity.ok()
                .body(quizService.solve(quizSeq, answer));
    } //FIXME Answer 가 너무 길어질 가능성 확인.

}
