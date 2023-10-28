package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContentCreate {


    private QuizContentType quizContentType;

    private String detail;

    @Builder
    public QuizContentCreate(QuizContentType quizContentType, String detail){
        this.quizContentType = quizContentType;
        this.detail = detail;
    }

}
