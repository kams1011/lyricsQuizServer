package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContentCreate {


    private QuizContentType quizContentType;

    private String url; // URL이라고 하는게 맞겠다. 파일을 S3에 올리자마자 URL을 리턴하는걸로.

    @Builder
    public QuizContentCreate(QuizContentType quizContentType, String url){
        this.quizContentType = quizContentType;
        this.url = url;
    }

}
