package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "game_entity")
public class GameEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private UserEntity manager;

    private String roomName;

    private Integer attendeeLimit;

    private Integer attendeeCount;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;


    @Builder
    public GameEntity(Long gameRoomSeq, UserEntity manager, String roomName,
                      Integer attendeeLimit, Integer attendeeCount,
                      LocalDateTime createdAt, LocalDateTime startedAt,
                      LocalDateTime endedAt, QuizEntity quiz){
        this.gameRoomSeq = gameRoomSeq;
        this.manager = manager;
        this.roomName = roomName;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.quiz = quiz;
    }


    public Game toModel(){
        return Game.builder()
                .gameRoomSeq(gameRoomSeq)
                .manager(manager.toModel())
                .roomName(roomName)
                .attendeeLimit(attendeeLimit)
                .attendeeCount(attendeeCount)
                .createdAt(createdAt)
                .startedAt(startedAt)
                .endedAt(endedAt)
                .quiz(quiz.toModel())
                .build();
    }
}
