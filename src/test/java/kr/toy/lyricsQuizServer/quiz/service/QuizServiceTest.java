package kr.toy.lyricsQuizServer.quiz.service;

import kr.toy.lyricsQuizServer.quiz.controller.port.QuizService;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import org.springframework.beans.factory.annotation.Autowired;

public class QuizServiceTest {


    @Autowired
    QuizService quizService;

    public void create_메서드로_Quiz_를_생성할_수_있다(){
//        QuizCreate quizCreate = QuizCreate.builder()
//                .title("HypeBoy")
//                .singer("뉴진스")
//                .quizMakeType()
//                .information()
//                .beforeLyrics()
//                .afterLyrics()
//                .startTime()
//                .endTime()
//                .answer()
//                .userSeq(1L)
//                .build();
//        quizService.create()
    }
}
