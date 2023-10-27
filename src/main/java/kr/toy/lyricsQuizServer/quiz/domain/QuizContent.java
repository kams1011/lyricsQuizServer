package kr.toy.lyricsQuizServer.quiz.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContent {

    private Long quizContentSeq;

    private QuizContentType quizContentType;

    private String detail;


    @Builder
    public QuizContent(Long quizContentSeq, QuizContentType quizContentType, String detail){
        this.quizContentSeq = quizContentSeq;
        this.quizContentType = quizContentType;
        this.detail = detail;
    }

}
