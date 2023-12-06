package kr.toy.lyricsQuizServer.quiz.infrastructure;

import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.domain.QuizContentType;
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
@Table(name = "quiz_entity")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long quizSeq;

    Boolean isDeleted;

    String title;

    String singer;

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

    @OneToOne
    @JoinColumn(name = "quizContentSeq")
    QuizContentEntity quizContentEntity;


    @Builder
    public QuizEntity(Long quizSeq, Boolean isDeleted, String title, String singer, String information, LocalTime startTime, LocalTime endTime, String beforeLyrics, String afterLyrics,
                      String answer, LocalDateTime createdAt, LocalDateTime updatedAt, UserEntity maker, QuizContentEntity quizContentEntity){
        this.quizSeq = quizSeq;
        this.isDeleted = isDeleted;
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
        this.quizContentEntity = quizContentEntity;
    }

    public Quiz toModel(){
        return Quiz.builder()
                .quizSeq(quizSeq)
                .isDeleted(isDeleted)
                .title(title)
                .singer(singer)
                .information(information)
                .startTime(startTime)
                .endTime(endTime)
                .beforeLyrics(beforeLyrics)
                .afterLyrics(afterLyrics)
                .answer(answer)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .maker(maker.toModel())
                .quizContent(quizContentEntity.toModel())
                .build();
    }


    public static QuizEntity fromModel(Quiz quiz){
        return QuizEntity.builder()
                .quizSeq(quiz.getQuizSeq())
                .isDeleted(quiz.getIsDeleted())
                .title(quiz.getTitle())
                .singer(quiz.getSinger())
                .information(quiz.getInformation())
                .startTime(quiz.getStartTime())
                .endTime(quiz.getEndTime())
                .beforeLyrics(quiz.getBeforeLyrics())
                .afterLyrics(quiz.getAfterLyrics())
                .answer(quiz.getAnswer())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt())
                .maker(UserEntity.fromModel(quiz.getMaker()))
                .quizContentEntity(QuizContentEntity.fromModel(quiz.getQuizContent()))
                .build();
    }

    public void setQuizContentEntity(QuizContentEntity quizContentEntity){
        this.quizContentEntity = quizContentEntity;
    }

    public void delete(){
        this.isDeleted = true;
    }
}
