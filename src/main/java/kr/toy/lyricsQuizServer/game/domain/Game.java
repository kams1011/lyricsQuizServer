package kr.toy.lyricsQuizServer.game.domain;

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

    private String topic;

    private Integer attendeeLimit;

    private Integer attendeeCount;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Quiz quiz;


    @Builder
    public Game(Long gameRoomSeq, LocalDateTime startTime, User manager, String topic, Integer attendeeLimit, Integer attendeeCount, Quiz quiz){
        this.gameRoomSeq = gameRoomSeq;
        this.startTime = startTime;
        this.manager = manager;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
        this.quiz = quiz;
    }

}
