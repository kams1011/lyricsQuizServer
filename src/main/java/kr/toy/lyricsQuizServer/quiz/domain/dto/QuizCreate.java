package kr.toy.lyricsQuizServer.quiz.domain.dto;

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
    public QuizCreate(String title, Boolean isDeleted, String singer, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics, String afterLyrics, String answer,
                      Long userSeq, QuizContentCreate quizContentCreate){
        this.title = title;
        this.isDeleted = isDeleted;
        this.singer = singer;
        this.information = information;
        this.startTime = startTime;
        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.userSeq = userSeq;
        this.quizContentCreate = quizContentCreate;
    }


}
