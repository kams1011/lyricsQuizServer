package kr.toy.lyricsQuizServer.quiz.domain;

import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizContentCreate;
import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Quiz {

    private Long quizSeq;

    private String title;

    private String singer;

    private String information;

    private String beforeLyrics;

    private String afterLyrics;

    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt; // JPAAuditing 사용하면 어떻게 변경할지 생각

    private User maker;

    private LocalTime startTime; // 파일 내에 노래 구간 정보.

    private LocalTime endTime;

    private QuizContent quizContent;

    @Builder
    public Quiz(Long quizSeq, String title, String singer, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics,
                String afterLyrics, String answer, LocalDateTime createdAt, LocalDateTime updatedAt, User maker, QuizContent quizContent){
        this.quizSeq = quizSeq;
        this.title = title;
        this.singer = singer;
        this.information = information;
        this.startTime = startTime;
        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maker = maker;
        this.quizContent = quizContent;
    }


    public static Quiz from(QuizCreate quizCreate, User maker, LocalDateTime createdAt){
        return Quiz.builder()
                .title(quizCreate.getTitle())
                .singer(quizCreate.getSinger())
                .information(quizCreate.getInformation())
                .startTime(quizCreate.getStartTime())
                .endTime(quizCreate.getEndTime())
                .beforeLyrics(quizCreate.getBeforeLyrics())
                .afterLyrics(quizCreate.getAfterLyrics())
                .answer(quizCreate.getAnswer())
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .maker(maker)
                .quizContent(QuizContent.from(quizCreate.getQuizContentCreate()))
                .build();
    }
}
