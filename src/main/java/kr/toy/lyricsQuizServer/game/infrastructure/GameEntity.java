package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.quiz.infrastructure.QuizEntity;
import kr.toy.lyricsQuizServer.user.domain.User;
import kr.toy.lyricsQuizServer.user.infrastructure.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
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
    @JoinColumn(name = "quizSeq")
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

    public GameEntity fromModel(User manager, Game game, Quiz quiz){
        GameEntity gameEntity = GameEntity.builder()
                .manager(UserEntity.fromModel(manager))
                .roomName(game.getRoomName())
                .attendeeLimit(game.getAttendeeLimit())
                .attendeeCount(game.getAttendeeCount())
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .endedAt(game.getEndedAt())
                .quiz(QuizEntity.fromModel(quiz))
                .build();
        return gameEntity;
    }
}
