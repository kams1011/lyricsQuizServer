package kr.toy.lyricsQuizServer.quiz.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class QuizCreate {

    @NotBlank
    private String title;

    private Boolean isDeleted;

    @NotBlank
    private String singer;

    @NotBlank
    private String information;

    @NotNull
    private LocalTime startTime; // 파일 내에 노래 구간 정보.

    @NotNull
    private LocalTime endTime;

    @NotBlank
    private String beforeLyrics;

    @NotBlank
    private String afterLyrics;

    @NotBlank
    private String answer;

    @NotNull
    private Long userSeq; // 제작자 고유키

    @NotNull
    private QuizContentCreate quizContentCreate;

    @Builder
    @JsonCreator
    public QuizCreate(@JsonProperty("title") String title,
//                      Boolean isDeleted,
                      @JsonProperty("singer") String singer,
                      @JsonProperty("information") String information,
//                      @JsonProperty("startTime") LocalTime startTime,
//                      @JsonProperty("endTime") LocalTime endTime,
                      @JsonProperty("beforeLyrics") String beforeLyrics,
                      @JsonProperty("afterLyrics") String afterLyrics,
                      @JsonProperty("answer") String answer,
//                      Long userSeq,
                      @JsonProperty("quizContentCreate") QuizContentCreate quizContentCreate){
        this.title = title;
        this.isDeleted = isDeleted;
        this.singer = singer;
        this.information = information;
//        this.startTime = startTime;
//        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.userSeq = userSeq;
        this.quizContentCreate = quizContentCreate;
    }


}
