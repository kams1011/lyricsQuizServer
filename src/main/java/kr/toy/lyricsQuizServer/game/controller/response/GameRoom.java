package kr.toy.lyricsQuizServer.game.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameRoom {

    private final Long roomSeq;

    private final String picture;

    private final String roomName;

    private final LocalDateTime startTime;

    private final String managerName;

    private final String topic;

    private final Integer attendeeLimit;

    private final Integer attendeeCount;


    @Builder
    public GameRoom(Long roomSeq, String picture, String roomName, LocalDateTime startTime, String managerName, String topic, Integer attendeeLimit, Integer attendeeCount) {
        this.roomSeq = roomSeq;
        this.picture = picture;
        this.roomName = roomName;
        this.startTime = startTime;
        this.managerName = managerName;
        this.topic = topic;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = attendeeCount;
    }


}
