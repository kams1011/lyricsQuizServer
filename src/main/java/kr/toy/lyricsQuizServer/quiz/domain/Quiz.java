package kr.toy.lyricsQuizServer.quiz.domain;

import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Quiz {

    Long quizSeq;

    String title;

    String information;

    String beforeLyrics;

    String afterLyrics;

    String answer;

    LocalDateTime createdAt;

    LocalDateTime updatedAt; // JPAAuditing 사용하면 어떻게 변경할지 생각
    
    User maker; // 제작자 fk 주석처리

    @Builder
    public Quiz(Long quizSeq, String title, String information, String beforeLyrics,
                String afterLyrics, String answer, LocalDateTime createdAt, LocalDateTime updatedAt,
                User maker){
        this.quizSeq = quizSeq;
        this.title = title;
        this.information = information;
        this.beforeLyrics = beforeLyrics;
        this.afterLyrics = afterLyrics;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.maker = maker;
    }
}
