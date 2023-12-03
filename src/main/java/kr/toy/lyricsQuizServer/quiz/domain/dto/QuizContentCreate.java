package kr.toy.lyricsQuizServer.quiz.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizContentCreate {


    private QuizContentType quizContentType;

    private String url;

    private Long fileSeq;

    @Builder
    @JsonCreator
    public QuizContentCreate(@JsonProperty("quizContentType") QuizContentType quizContentType,
                             @JsonProperty("url") String url,
                             @JsonProperty("fileSeq") Long fileSeq){
        this.quizContentType = quizContentType;
        this.url = url;
        this.fileSeq = fileSeq;
    }

}
