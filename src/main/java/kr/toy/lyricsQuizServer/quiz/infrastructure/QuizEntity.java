package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizMakeType;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game_entity")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizSeq;

    String title;

    String singer;

    QuizMakeType quizMakeType;

    String information;

    LocalTime startTime; // 파일 내에 노래 구간 정보.

    LocalTime endTime;

    String beforeLyrics;

    String afterLyrics;

    String answer;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    UserEntity maker; // 제작자 fk 주석처리


    @Builder
    public QuizEntity(Long quizSeq, String title, String singer, QuizMakeType quizMakeType, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics, String afterLyrics,
                      String answer, LocalDateTime createdAt, LocalDateTime updatedAt, UserEntity maker){
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

    public Quiz toModel(){
        return Quiz.builder()
                .quizSeq(quizSeq)
                .title(title)
                .singer(singer)
                .quizMakeType(quizMakeType)
                .information(information)
                .startTime(startTime)
                .endTime(endTime)
                .beforeLyrics(beforeLyrics)
                .afterLyrics(afterLyrics)
                .answer(answer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .maker(maker.toModel())
                .build();
    }


    public static QuizEntity fromModel(Quiz quiz){
        return QuizEntity.builder()
                .quizSeq(quiz.getQuizSeq())
                .title(quiz.getTitle())
                .singer(quiz.getSinger())
                .quizMakeType(quiz.getQuizMakeType())
                .information(quiz.getInformation())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .beforeLyrics(quiz.getBeforeLyrics())
                .afterLyrics(quiz.getAfterLyrics())
                .answer(quiz.getAnswer())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt())
                .maker(UserEntity.fromModel(quiz.getMaker()))
                .build();
    }
}
