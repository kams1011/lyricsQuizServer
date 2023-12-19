package kr.toy.lyricsQuizServer.game.domain;

import kr.toy.lyricsQuizServer.game.domain.dto.GameCreate;
import kr.toy.lyricsQuizServer.game.infrastructure.GameEntity;
import kr.toy.lyricsQuizServer.quiz.domain.Quiz;
import kr.toy.lyricsQuizServer.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Game {

    private Long gameRoomSeq;

    private User manager;

    private String roomName;

    private Boolean isSecretRoom;

    private String password;

    private Integer attendeeLimit;

    private Integer attendeeCount;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Quiz quiz;


    @Builder
    public Game(Long gameRoomSeq, User manager, String roomName, Boolean isSecretRoom, String password,
                Integer attendeeLimit, Integer attendeeCount, LocalDateTime createdAt, LocalDateTime startedAt,
                LocalDateTime endedAt, Quiz quiz){
        this.gameRoomSeq = gameRoomSeq;
        this.manager = manager;
        this.roomName = roomName;
        this.isSecretRoom = isSecretRoom;
        this.password = password;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.quiz = quiz;
    }


    public static Game from(GameCreate gameCreate, User manager, Quiz quiz){
        return Game.builder()
                .manager(manager)
                .roomName(gameCreate.getRoomName())
                .isSecretRoom(gameCreate.getIsSecretRoom())
                .password(gameCreate.getPassword())
                .attendeeLimit(gameCreate.getAttendeeLimit())
                .quiz(quiz)
                .build();
    }


    public void create(LocalDateTime dateTime){
        this.createdAt = dateTime;
        this.attendeeCount = 0;
    }

    public void start(LocalDateTime dateTime){
        this.startedAt = dateTime;
    }
    public void end(LocalDateTime dateTime){
        this.endedAt = dateTime;
    }
    public void join(Integer joinCount){
        this.attendeeCount = joinCount;
    }
}
