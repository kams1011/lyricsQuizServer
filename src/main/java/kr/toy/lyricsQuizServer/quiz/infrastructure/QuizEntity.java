package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "game_entity")
public class QuizEntity {

    Long quizSeq;

    String title;

    String information;

    String beforeLyrics;

    String afterLyrics;

    String answer;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    UserEntity maker; // 제작자 fk 주석처리


    @Builder
    public QuizEntity(Long quizSeq, String title, String information, String beforeLyrics, String afterLyrics,
                      String answer, LocalDateTime createdAt, LocalDateTime updatedAt, UserEntity maker){
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



    public Quiz toModel(){
        return Quiz.builder()
                .quizSeq(quizSeq)
                .title(title)
                .information(information)
                .beforeLyrics(beforeLyrics)
                .afterLyrics(afterLyrics)
                .answer(answer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .maker(maker.toModel())
                .build();
    }
}
