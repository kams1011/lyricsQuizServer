package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContentCreate {

    //FIXME Url로할지 fileSeq로 할지 FIX

    private QuizContentType quizContentType;

    private String url; // URL이라고 하는게 맞겠다. 파일을 S3에 올리자마자 URL을 리턴하는걸로.

    private Long fileSeq; // URL이라고 하는게 맞겠다. 파일을 S3에 올리자마자 URL을 리턴하는걸로.

    @Builder
    public QuizContentCreate(QuizContentType quizContentType, String url, Long fileSeq){
        this.quizContentType = quizContentType;
        this.url = url;
        this.fileSeq = fileSeq;
    }

}
