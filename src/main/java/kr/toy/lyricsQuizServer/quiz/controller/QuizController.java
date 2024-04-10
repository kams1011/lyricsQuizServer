package kr.toy.lyricsQuizServer.quiz.controller;


import kr.toy.lyricsQuizServer.common.domain.Response;
import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizDetailToCreateRoom;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    @PostMapping("")
    public ResponseEntity<Response> create(@Valid @RequestBody QuizCreate quizCreate, User maker){
        return ResponseEntity.ok()
                .body(Response.success(quizService.create(maker, quizCreate)));
    }

    @DeleteMapping("/{quizSeq}")
    public ResponseEntity<Response> delete(@PathVariable Long quizSeq){

        return ResponseEntity.ok()
                .body(Response.success(quizService.delete(quizSeq)));
    }

    @GetMapping("")
    public ResponseEntity<Response> getList(@RequestParam(required = false) String keyword, Pageable pageable){
        PageImpl<Quiz> pages = quizService.getList(keyword, pageable);
        return ResponseEntity.ok()
                .body(Response.success(new PageImpl(pages.toList(), pageable, pages.getTotalElements())));
    }

    @GetMapping("/{quizSeq}")
    public ResponseEntity<Response> getDetail(@PathVariable Long quizSeq){
        Quiz quiz = quizService.find(quizSeq);
        return ResponseEntity.ok()
                .body(Response.success(QuizDetailToCreateRoom.fromModel(quiz)));
    }
}
