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

    private User host;

    private String roomName;

    private Boolean isSecretRoom;

    private String password;

    private Integer attendeeLimit;

    private Integer attendeeCount;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private GameStatus gameStatus;

    private Quiz quiz;


    @Builder
    public Game(Long gameRoomSeq, User host, String roomName, Boolean isSecretRoom, String password, Integer attendeeLimit, Integer attendeeCount, LocalDateTime createdAt, LocalDateTime startedAt,
                LocalDateTime endedAt, Quiz quiz, GameStatus gameStatus){
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
    }


    public static Game from(GameCreate gameCreate, User host, Quiz quiz){
        return Game.builder()
                .host(host)
                .roomName(gameCreate.getRoomName())
                .isSecretRoom(gameCreate.getIsSecretRoom())
                .password(gameCreate.getPassword())
                .attendeeLimit(gameCreate.getAttendeeLimit())
                .quiz(quiz)
                .gameStatus(GameStatus.READY)
                .build();
    }

    public Game create(LocalDateTime dateTime){
        this.createdAt = dateTime;
        this.attendeeCount = 0;
        return this;
    }

    public void start(LocalDateTime dateTime){
        this.startedAt = dateTime;
    }

    public void end(LocalDateTime dateTime){
        this.endedAt = dateTime;
    }

    public void join(){
        isAccessibleGameCheck();
        if (this.attendeeCount < this.attendeeLimit) {
            this.attendeeCount++;
        } else {
            throw new IllegalStateException("입장 인원을 초과했습니다.");
        }
    }

    public void isAccessibleGameCheck(){
        if (!this.gameStatus.isAccessible()) {
            throw new IllegalStateException("게임이 준비상태가 이닙니다.");
        }
    }

}
