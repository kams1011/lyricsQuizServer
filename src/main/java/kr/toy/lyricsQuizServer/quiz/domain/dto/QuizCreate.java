package kr.toy.lyricsQuizServer.quiz.domain.dto;

import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class QuizCreate {

    private String title;

    private String singer;

    private QuizContentType quizContentType;
    private String information;

    private LocalTime startTime; // 파일 내에 노래 구간 정보.

    private LocalTime endTime;

    private String beforeLyrics;

    private String afterLyrics;

    private String answer;

    private Long userSeq; // 제작자 고유키

    @Builder
    public QuizCreate(String title, String singer, QuizContentType quizContentType, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics, String afterLyrics, String answer, Long userSeq){
        this.title = title;
        this.singer = singer;
        this.quizContentType = quizContentType;
        this.information = information;
        this.startTime = startTime;
        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.userSeq = userSeq;
    }


}
