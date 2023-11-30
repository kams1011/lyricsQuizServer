package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContentCreate {


    private QuizContentType quizContentType;

    private String url;

    private Long fileSeq;

    @Builder
    public QuizContentCreate(QuizContentType quizContentType, String url, Long fileSeq){
        this.quizContentType = quizContentType;
        this.url = url;
        this.fileSeq = fileSeq;
    }

}
