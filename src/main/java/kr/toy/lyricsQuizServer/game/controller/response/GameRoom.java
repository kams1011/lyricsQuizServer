package kr.toy.lyricsQuizServer.game.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameRoom {

    private Long roomSeq;

    private String picture;

    private String roomName;

    private LocalDateTime startTime;

    private String managerName;

    private String topic;

    private Integer attendeeLimit;

    private Integer attendeeCount;


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
