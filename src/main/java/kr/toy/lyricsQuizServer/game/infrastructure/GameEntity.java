package kr.toy.lyricsQuizServer.game.infrastructure;

import kr.toy.lyricsQuizServer.game.domain.Game;
import kr.toy.lyricsQuizServer.game.domain.GameStatus;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game_entity")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRoomSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq")
    private UserEntity host;

    private String roomName;

    private Boolean isSecretRoom;

    private String password;

    private Integer attendeeLimit;

    private Integer attendeeCount;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "quizSeq")
    private QuizEntity quiz;

    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;

    @Column(columnDefinition = "boolean DEFAULT false")
    private boolean isDeleted;


    @Builder
    public GameEntity(Long gameRoomSeq, UserEntity host, String roomName, Boolean isSecretRoom, String password,
                      Integer attendeeLimit, Integer attendeeCount, LocalDateTime createdAt, LocalDateTime startedAt,
                      LocalDateTime endedAt, QuizEntity quiz, GameStatus gameStatus, boolean isDeleted){
        this.gameRoomSeq = gameRoomSeq;
        this.host = host;
        this.roomName = roomName;
        this.isSecretRoom = isSecretRoom;
        this.password = password;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.quiz = quiz;
        this.gameStatus = gameStatus;
        this.isDeleted = isDeleted;
    }

    public Game toModel(){
        return Game.builder()
                .gameRoomSeq(gameRoomSeq)
                .host(host.toModel())
                .roomName(roomName)
                .isSecretRoom(isSecretRoom)
                .password(password)
                .attendeeLimit(attendeeLimit)
                .attendeeCount(attendeeCount)
                .createdAt(createdAt)
                .startedAt(startedAt)
                .endedAt(endedAt)
                .quiz(quiz.toModel())
                .gameStatus(gameStatus)
                .isDeleted(isDeleted)
                .build();
    }

    public static GameEntity fromModel(User host, Game game, Quiz quiz){
        return GameEntity.builder()
                .gameRoomSeq(game.getGameRoomSeq())
                .host(UserEntity.fromModel(host))
                .roomName(game.getRoomName())
                .isSecretRoom(game.getIsSecretRoom())
                .password(game.getPassword())
                .attendeeLimit(game.getAttendeeLimit())
                .attendeeCount(game.getAttendeeCount())
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .endedAt(game.getEndedAt())
                .quiz(QuizEntity.fromModel(quiz))
                .gameStatus(game.getGameStatus())
                .isDeleted(game.isDeleted())
                .build();
    }

}
