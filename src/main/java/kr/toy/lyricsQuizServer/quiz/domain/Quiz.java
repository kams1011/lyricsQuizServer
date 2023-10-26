package kr.toy.lyricsQuizServer.quiz.domain;

import kr.toy.lyricsQuizServer.quiz.domain.dto.QuizCreate;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Quiz {

    Long quizSeq;

    QuizMakeType quizMakeType;

    String title;

    String singer;

    String information;

    String beforeLyrics;

    String afterLyrics;

    String answer;

    LocalDateTime createdAt;

    LocalDateTime updatedAt; // JPAAuditing 사용하면 어떻게 변경할지 생각
    
    User maker;

    LocalTime startTime; // 파일 내에 노래 구간 정보.

    LocalTime endTime;

    @Builder
    public Quiz(Long quizSeq, QuizMakeType quizMakeType, String title, String singer, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics,
                String afterLyrics, String answer, LocalDateTime createdAt, LocalDateTime updatedAt, User maker){
        this.quizSeq = quizSeq;
        this.title = title;
        this.singer = singer;
        this.quizMakeType = quizMakeType;
        this.information = information;
        this.startTime = startTime;
        this.endTime = endTime;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maker = maker;
    }


    public static Quiz from(QuizCreate quizCreate, User maker, LocalDateTime createdAt){
        return Quiz.builder()
                .title(quizCreate.getTitle())
                .singer(quizCreate.getSinger())
                .quizMakeType(quizCreate.getQuizMakeType())
                .information(quizCreate.getInformation())
                .startTime(quizCreate.getStartTime())
                .endTime(quizCreate.getEndTime())
                .beforeLyrics(quizCreate.getBeforeLyrics())
                .afterLyrics(quizCreate.getAfterLyrics())
                .answer(quizCreate.getAnswer())
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .maker(maker)
                .build();
    }
}
